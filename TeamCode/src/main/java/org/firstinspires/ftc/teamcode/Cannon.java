package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Cannon {
    TeleOp teleOp;

    boolean cannon_on = false;
    double time_limit = teleOp.time_limit;
    double position_open, position_close;
    //разница между открытым и закрытым положением
    double op = 90;
    ElapsedTime timer, timer_servo;
    DcMotor cannon_motor;
    Servo block_servo;

    public Cannon(LinearOpMode opMode) {
        cannon_motor = opMode.hardwareMap.get(DcMotor.class, "cannon_motor");
        position_close = block_servo.getPosition();
        position_open = block_servo.getPosition() + op;
        //block_servo.setPosition(position_close);
    }
    public void cannon_on_off(){
        if (cannon_on && timer.seconds() >= time_limit) {cannon_motor.setPower(0); cannon_on = false;}
        else if (timer.seconds() >= time_limit){cannon_motor.setPower(1); cannon_on = true;}
        timer.startTime();
    }

    public void manual_block_servo() {
        if (block_servo.getPosition() == position_close && timer_servo.seconds() >= time_limit) {block_servo.setPosition(position_open);}
        else if (timer_servo.seconds() >= time_limit) {block_servo.setPosition(position_close);}
    }
    public void cripple_block_servo(double gx) {
        block_servo.setPosition(block_servo.getPosition() + gx);
    }

    public void auto_block_servo() {
        if (cannon_on && timer.seconds() >= 2){
            block_servo.setPosition(position_open);
        }
        else {block_servo.setPosition(position_close);}

    }
}
