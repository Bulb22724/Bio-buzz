package org.firstinspires.ftc.teamcode.shooter;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.util.SubsystemIF;

public class Shooter extends SubsystemIF {

    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private double power;
    DcMotorSimple motor;
    Servo servo;

    public Shooter(Telemetry telemetry, HardwareMap hardwareMap) {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;

        motor = hardwareMap.get(DcMotorSimple.class, "shooterMotor");
        servo = hardwareMap.get(Servo.class, "shooterServo");
    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void teleopInit() {
        runServo();
        resetShotPower();
    }

    @Override
    public void periodic() {
        telemetry.addData("Shot Power: ", getPower());
    }


    public void runServo() {
        servo.setPosition(0.25);
    }

    public void shoot() {
        motor.setPower(power);
    }

    public void increasePower() {
        power = Math.min(1.0, power + 0.05);
        motor.setPower(power + 0.05);
    }

    public void decreasePower() {
        power = Math.max(0.0, power - 0.05);
        motor.setPower(power - 0.05);
    }

    public void resetShotPower() {
        power = 0.5;
        // 0.5 is just a magic number
        motor.setPower(power);
    }

    public void stopMotor() {
        motor.setPower(0);
    }

    public double getPower() {
        return motor.getPower();
    }
}
