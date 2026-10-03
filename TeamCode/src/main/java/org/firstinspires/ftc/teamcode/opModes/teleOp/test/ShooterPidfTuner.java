package org.firstinspires.ftc.teamcode.opModes.teleOp.test;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.core.control.lastPositionStorage;
import org.firstinspires.ftc.teamcode.core.pedro.Constants;
import org.firstinspires.ftc.teamcode.core.units.Units.Alliance;
import org.firstinspires.ftc.teamcode.subsystems.shooter;

/**
 * Test opMode for tuning the shooter velocity controller and flywheel distance formula.
 *
 * <p>Controls (gamepad 1):
 * <ul>
 *     <li>Start: toggle the shooter on/off.</li>
 *     <li>Y/B: increase/decrease target RPM by 100.</li>
 *     <li>D-pad up/right/down/left: select P/I/D/F respectively.</li>
 *     <li>Right bumper/left bumper: increase/decrease the selected PIDF value.</li>
 *     <li>Back: restore 4000 RPM and the default PIDF values.</li>
 * </ul>
 * Controls are edge-triggered; tap a button to make one change.</p>
 */
@TeleOp(name = "Shooter PIDF Tuner", group = "Test")
public class ShooterPidfTuner extends LinearOpMode {
    private static final double DEFAULT_TARGET_RPM = 4000.0;
    private static final double RPM_STEP = 100.0;
    private static final int LOOP_PERIOD_MS = 40;

    private static final double DEFAULT_P = 0.0002;
    private static final double DEFAULT_I = 0.0000001;
    private static final double DEFAULT_D = 0.00001;
    private static final double DEFAULT_F = 0.00017;

    private DcMotorEx shooterMotor;
    private Follower follower;
    private Alliance alliance;
    private double targetRpm = DEFAULT_TARGET_RPM;
    private double targetVelocityTicksPerSecond = rpmToTicksPerSecond(DEFAULT_TARGET_RPM);
    private PIDFCoefficients pidf = new PIDFCoefficients(DEFAULT_P, DEFAULT_I, DEFAULT_D, DEFAULT_F);
    private PidfParameter selectedParameter = PidfParameter.P;
    private boolean shooterEnabled = false;

    private boolean previousStart;
    private boolean previousBack;
    private boolean previousY;
    private boolean previousB;
    private boolean previousDpadUp;
    private boolean previousDpadRight;
    private boolean previousDpadDown;
    private boolean previousDpadLeft;
    private boolean previousRightBumper;
    private boolean previousLeftBumper;

    @Override
    public void runOpMode() throws InterruptedException {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        applyPidf();

        follower = Constants.createFollower(hardwareMap);
        alliance = lastPositionStorage.getCurrentAlliance();
        follower.update();
        telemetry.setMsTransmissionInterval(LOOP_PERIOD_MS);

        telemetry.addLine("Shooter PIDF tuner ready");
        telemetry.addLine("Start: toggle | Y/B: RPM | D-pad: P/I/D/F");
        telemetry.addLine("Bumpers: decrease/increase selected PIDF");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) {
            return;
        }

        while (opModeIsActive()) {
            follower.update();
            updateControls();

            if (shooterEnabled) {
                shooterMotor.setVelocity(targetVelocityTicksPerSecond);
            } else {
                shooterMotor.setVelocity(0.0);
            }

            addTelemetry();
            sleep(LOOP_PERIOD_MS);
        }

        shooterMotor.setVelocity(0.0);
        shooterMotor.setPower(0.0);
    }

    private void updateControls() {
        if (pressed(gamepad1.start, previousStart)) {
            shooterEnabled = !shooterEnabled;
        }
        if (pressed(gamepad1.back, previousBack)) {
            targetRpm = DEFAULT_TARGET_RPM;
            targetVelocityTicksPerSecond = rpmToTicksPerSecond(targetRpm);
            pidf = new PIDFCoefficients(DEFAULT_P, DEFAULT_I, DEFAULT_D, DEFAULT_F);
            selectedParameter = PidfParameter.P;
            applyPidf();
        }

        if (pressed(gamepad1.y, previousY)) {
            setTargetRpm(targetRpm + RPM_STEP);
        }
        if (pressed(gamepad1.b, previousB)) {
            setTargetRpm(targetRpm - RPM_STEP);
        }

        if (pressed(gamepad1.dpad_up, previousDpadUp)) {
            selectedParameter = PidfParameter.P;
        }
        if (pressed(gamepad1.dpad_right, previousDpadRight)) {
            selectedParameter = PidfParameter.I;
        }
        if (pressed(gamepad1.dpad_down, previousDpadDown)) {
            selectedParameter = PidfParameter.D;
        }
        if (pressed(gamepad1.dpad_left, previousDpadLeft)) {
            selectedParameter = PidfParameter.F;
        }
        if (pressed(gamepad1.right_bumper, previousRightBumper)) {
            changeSelectedPidf(1.0);
        }
        if (pressed(gamepad1.left_bumper, previousLeftBumper)) {
            changeSelectedPidf(-1.0);
        }

        previousStart = gamepad1.start;
        previousBack = gamepad1.back;
        previousY = gamepad1.y;
        previousB = gamepad1.b;
        previousDpadUp = gamepad1.dpad_up;
        previousDpadRight = gamepad1.dpad_right;
        previousDpadDown = gamepad1.dpad_down;
        previousDpadLeft = gamepad1.dpad_left;
        previousRightBumper = gamepad1.right_bumper;
        previousLeftBumper = gamepad1.left_bumper;
    }

    private void setTargetRpm(double rpm) {
        targetRpm = Math.max(0.0, rpm);
        targetVelocityTicksPerSecond = rpmToTicksPerSecond(targetRpm);
    }

    private void changeSelectedPidf(double direction) {
        switch (selectedParameter) {
            case P:
                pidf.p = Math.max(0.0, pidf.p + direction * 0.0001);
                break;
            case I:
                pidf.i = Math.max(0.0, pidf.i + direction * 0.00000001);
                break;
            case D:
                pidf.d = Math.max(0.0, pidf.d + direction * 0.000001);
                break;
            case F:
                pidf.f = Math.max(0.0, pidf.f + direction * 0.0001);
                break;
        }
        applyPidf();
    }

    private void applyPidf() {
        if (shooterMotor != null) {
            shooterMotor.setVelocityPIDFCoefficients(pidf.p, pidf.i, pidf.d, pidf.f);
        }
    }

    private void addTelemetry() {
        double measuredVelocity = Math.abs(shooterMotor.getVelocity());
        double measuredRpm = measuredVelocity * 60.0 / shooter.TICKS_PER_REV;
        double robotX = follower.pose().x();
        double robotY = follower.pose().y();
        shooter.HiveDistance nearestHive = shooter.nearestHive(robotX, robotY, alliance);

        telemetry.addData("Shooter", shooterEnabled ? "RUNNING" : "OFF");
        telemetry.addData("Target RPM", "%.1f", targetRpm);
        telemetry.addData("Measured RPM", "%.1f", measuredRpm);
        telemetry.addData("Selected PIDF", selectedParameter.name());
        telemetry.addData("PIDF step", "P/F: 0.0001 | I: 0.00000001 | D: 0.000001");
        telemetry.addData("P", "%.8f", pidf.p);
        telemetry.addData("I", "%.8f", pidf.i);
        telemetry.addData("D", "%.8f", pidf.d);
        telemetry.addData("F", "%.8f", pidf.f);
        telemetry.addData("Alliance", alliance);
        telemetry.addData("Robot position", "x %.2f, y %.2f", robotX, robotY);
        telemetry.addData("Target hive", nearestHive.position.name);
        telemetry.addData("Distance to nearest hive [in]", "%.2f", nearestHive.distance);
        telemetry.addData("Loop", LOOP_PERIOD_MS + " ms");
        telemetry.update();
    }

    private static double rpmToTicksPerSecond(double rpm) {
        return rpm * shooter.TICKS_PER_REV / 60.0;
    }

    private static boolean pressed(boolean current, boolean previous) {
        return current && !previous;
    }

    private enum PidfParameter {
        P, I, D, F
    }

    private static class HivePosition {
        private final String name;
        private final double x;
        private final double y;
        private final double z;

        private HivePosition(String name, double x, double y, double z) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static class HiveDistance {
        private final HivePosition position;
        private final double distance;

        private HiveDistance(HivePosition position, double distance) {
            this.position = position;
            this.distance = distance;
        }
    }
}
