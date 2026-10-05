package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.HardwareConfig;

@TeleOp
public class RunCode extends OpMode {

    HardwareConfig config = new HardwareConfig();

    double forward;
    double strafe;
    double rotate;

    double feederPower;

    @Override
    public void init() {
        config.init(hardwareMap);
        feederPower = 0.3;
    }

    @Override
    public void loop() {
        forward = gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;
        rotate = gamepad1.left_stick_x;

        config.driveFieldRelative(forward, strafe, rotate);

        if (gamepad2.left_trigger_pressed && !gamepad2.left_bumper) {
            config.intakeMotor.setPower(gamepad2.left_trigger);
        }
        else {
            config.intakeMotor.setPower(0);
        }

        if (gamepad2.left_trigger_pressed && gamepad2.left_bumper) {
            config.intakeMotor.setPower(-gamepad2.left_trigger);
        }
        else {
            config.intakeMotor.setPower(0);
        }

        /* This is for the flywheel motor
        if (gamepad2.right_trigger_pressed) {
            config.intakeMotor.setPower(gamepad2.right_trigger);
        }
        else {
            config.intakeMotor.setPower(0);
        }
        */
        if (gamepad1.dpad_up){
            config.feederServo.setPower(feederPower);
        }
        else {
            config.feederServo.setPower(0);
        }

        if (gamepad1.dpad_down){
            config.feederServo.setPower(-feederPower);
        }
        else{
            config.feederServo.setPower(0);
        }
    }
}
