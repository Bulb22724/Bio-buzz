package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.qualcomm.robotcore.hardware.Gamepad;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.drive.DriveCommands;

public class Drivetrain implements Mechanism {
    public final NextMotor frontLeft;
    public final NextMotor frontRight;
    public final NextMotor backLeft;
    public final NextMotor backRight;

    public Drivetrain(NextMotor frontLeft, NextMotor frontRight, NextMotor backLeft, NextMotor backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        frontLeft.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.BRAKE);
    }

    public void startMecanumDrive(Gamepad gamepad) {
        DriveCommands.mecanumDrive(frontLeft, frontRight, backLeft, backRight, gamepad).schedule();
    }
    public void startFieldCentricDrive(Gamepad gamepad){
        DriveCommands.mecanumDriveFieldCentric(frontLeft,frontRight,backLeft,backRight, gamepad , () -> follower.getPose().getHeading()).schedule();
    }
}