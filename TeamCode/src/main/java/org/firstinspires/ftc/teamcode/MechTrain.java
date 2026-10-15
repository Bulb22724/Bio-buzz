package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
public class MechTrain {
    DcMotor front_left, front_right, back_left, back_right;

    public MechTrain(LinearOpMode opMode) {
        front_left = opMode.hardwareMap.get(DcMotor.class, "front_left");
        front_right = opMode.hardwareMap.get(DcMotor.class, "front_right");
        back_left = opMode.hardwareMap.get(DcMotor.class, "back_left");
        back_right = opMode.hardwareMap.get(DcMotor.class, "back_right");

        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //управление движением
    public void move(double g1x, double g1y, double rt) {
        front_left.setPower(g1y - g1x + rt);
        front_right.setPower(g1y + g1x - rt);
        back_left.setPower(g1y + g1x + rt);
        back_right.setPower(g1y - g1x - rt);
    }

}
