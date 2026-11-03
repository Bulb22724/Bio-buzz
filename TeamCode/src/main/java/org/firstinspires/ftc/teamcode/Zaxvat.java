package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Zaxvat {
    ElapsedTime timer = new ElapsedTime();
    DcMotor capture_motor;
    boolean capture_on = false;
    double time_limit = 0;


    public Zaxvat(LinearOpMode opMode, double tl){
        capture_motor = opMode.hardwareMap.get(DcMotor.class, "capture_motor");
        time_limit = tl;
    }
    //включение-выключение заксвата с задержкой после запуска
    public void capture(){
        if (capture_on && timer.seconds() >= time_limit) {capture_motor.setPower(0); capture_on = false; timer.startTime();}
        else if (timer.seconds() >= time_limit){capture_motor.setPower(1); capture_on = true; timer.startTime();}
        
    }

}
