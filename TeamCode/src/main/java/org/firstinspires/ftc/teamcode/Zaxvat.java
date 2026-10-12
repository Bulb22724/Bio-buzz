package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Zaxvat {
    TeleOp teleOp;
    ElapsedTime timer;
    DcMotor capture_motor;
    double time_limit = teleOp.time_limit;
    boolean capture_on = false;


    public Zaxvat(LinearOpMode opMode){
        capture_motor = opMode.hardwareMap.get(DcMotor.class, "capture_motor");
    }
    public void capture(){
        if (capture_on && timer.seconds() >= time_limit) {capture_motor.setPower(0); capture_on = false;}
        else if (timer.seconds() >= time_limit){capture_motor.setPower(1); capture_on = true;}
        timer.startTime();
    }

}
