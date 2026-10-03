package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp (name = "test")
public class test1 extends LinearOpMode {
    DcMotor testMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        testMotor = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();
        while (opModeIsActive()) {
            testMotor.setPower(1);
        }
    }
}