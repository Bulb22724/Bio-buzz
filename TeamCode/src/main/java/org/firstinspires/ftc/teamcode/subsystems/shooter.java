package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.units.Units;

public class shooter {

    private static final double GEAR_RATIO = 1.0;
    public static final double TICKS_PER_REV = 2048.0;
    public static final int STANDARD_RPM = 3000; // This is the rpm if the bot touches the side panel of the field and shoots.

    private static final double kP = 0.0002;
    private static final double kI = 0.0000001;
    private static final double kD = 0.00001;
    private static final double kF = 0.00017;

    private static final double PIDF_RANGE_RPM = 150.0;

    public static DcMotorEx shooterMotor;
    private static Servo servoHood;

    private static double integralSum = 0.0;
    private static double lastError = 0.0;
    private static double lastTime = 0.0;

    private boolean bypassEnabled;
    private double targetRpm;

    private static final HivePosition BOTTOM_RED_HIVE = new HivePosition("Bottom red", 59.25, 62.58, 53.5);
    private static final HivePosition TOP_RED_HIVE = new HivePosition("Top red", 59.25, 81.42, 53.5);
    private static final HivePosition TOP_BLUE_HIVE = new HivePosition("Top blue", 84.25, 81.42, 53.5);
    private static final HivePosition BOTTOM_BLUE_HIVE = new HivePosition("Bottom blue", 84.25, 62.58, 53.5);

    public double calculateFlywheelVelocity(double distanceToNearestHive){
        double speed = distanceToNearestHive;
        return speed;
    }

    public static HiveDistance nearestHive(double robotX, double robotY, Units.Alliance alliance) {
        HivePosition targetHive;
        if (robotY > 72.0) {
            if (alliance == Units.Alliance.RED) {
                targetHive = TOP_RED_HIVE;
            } else {
                targetHive = TOP_BLUE_HIVE;
            }
        } else {
            if (alliance == Units.Alliance.RED) {
                targetHive = BOTTOM_RED_HIVE;
            } else {
                targetHive = BOTTOM_BLUE_HIVE;
            }
        }
        return new HiveDistance(targetHive, distance(robotX, robotY, targetHive));
    }

    private static double distance(double robotX, double robotY, HivePosition hive) {
        double xDelta = hive.x - robotX;
        double yDelta = hive.y - robotY;
        return Math.sqrt(xDelta * xDelta + yDelta * yDelta);
    }

    public void init(HardwareMap hardwareMap) {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servoHood = hardwareMap.get(Servo.class, "servoHood");
        integralSum = 0.0;
        lastError = 0.0;
        lastTime = System.nanoTime() / 1e9;
        bypassEnabled = false;
    }

    public boolean toggleBypass() {
        setBypassEnabled(!bypassEnabled);
        return bypassEnabled;
    }

    public void setBypassEnabled(boolean enabled) {
        bypassEnabled = enabled;
        if (enabled) {
            targetRpm = STANDARD_RPM;
        }
    }

    public void adjustTargetRpm(double delta) {
        targetRpm = Math.max(0.0, targetRpm + delta);
    }

    public boolean isBypassEnabled() {
        return bypassEnabled;
    }

    public double getTargetRpm() {
        return targetRpm;
    }

    public void update(double robotX, double robotY, Units.Alliance alliance) {
        HiveDistance nearestHive = nearestHive(robotX, robotY, alliance);
        if (!bypassEnabled) {
            targetRpm = calculateFlywheelVelocity(nearestHive.distance);
        }
        setFlywheelVelocity(targetRpm, shooterMotor.getVelocity());
    }

    public void stop() {
        if (shooterMotor != null) {
            shooterMotor.setPower(0.0);
        }
    }

    public void setFlywheelVelocity(double targetVelocity, double currentVelocityTicksPerSec) {
        double currentTime = System.nanoTime() / 1e9;

        double targetTicksPerSec = (targetVelocity / 60.0) * TICKS_PER_REV * GEAR_RATIO;
        double error = targetTicksPerSec - currentVelocityTicksPerSec;
        double currentRpm = currentVelocityTicksPerSec * 60.0 / (TICKS_PER_REV * GEAR_RATIO);

        double dt = currentTime - lastTime;
        if (dt <= 0) dt = 1e-3;

        double output;
        if (currentRpm < targetVelocity - PIDF_RANGE_RPM) {
            integralSum = 0.0;
            output = 1.0;
        } else if (currentRpm > targetVelocity) {
            integralSum = 0.0;
            output = 0.0;
        } else {
            integralSum += error * dt;
            double derivative = (error - lastError) / dt;
            output = (kP * error) + (kI * integralSum) + (kD * derivative) + (kF * targetTicksPerSec);
        }

        lastError = error;
        lastTime = currentTime;

        output = Math.max(-1.0, Math.min(1.0, output));
        shooterMotor.setPower(output);
    }

    public static class HivePosition {
        public final String name;
        public final double x;
        public final double y;
        public final double z;

        public HivePosition(String name, double x, double y, double z) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static class HiveDistance {
        public final HivePosition position;
        public final double distance;

        public HiveDistance(HivePosition position, double distance) {
            this.position = position;
            this.distance = distance;
        }
    }
}