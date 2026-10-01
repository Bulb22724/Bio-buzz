package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.HardwareConfig;

public class MechanumDrive extends OpMode {

    HardwareConfig config = new HardwareConfig();

    double forward;
    double strafe;
    double rotate;

    @Override
    public void init() {
        config.init(hardwareMap);
    }

    @Override
    public void loop() {
        forward = gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;
        rotate = gamepad1.left_stick_x;

        config.driveFieldRelative(forward, strafe, rotate);
    }
}
