package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Zaxvat {
    DcMotor capture_motor;
    boolean capture_on = false;



    public Zaxvat(LinearOpMode opMode) {
        capture_motor = opMode.hardwareMap.get(DcMotor.class, "capture_motor");
    }

    //включение-выключение заксвата с задержкой после запуска
    public void capture() {
        if (capture_on) {
            capture_motor.setPower(0);
            capture_on = false;
        } else {
            capture_motor.setPower(1);
            capture_on = true;
        }

    }

}
