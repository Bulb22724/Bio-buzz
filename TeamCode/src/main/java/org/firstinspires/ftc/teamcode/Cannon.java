package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Cannon {
    boolean cannon_on = false;
    double position_open, position_close;
    //разница между открытым и закрытым положением
    double op = 0.5;
    double time_limit = 0;
    ElapsedTime timer = new ElapsedTime();
    ElapsedTime timer_servo = new ElapsedTime();
    DcMotor cannon_motor;
    Servo block_servo;

    public Cannon(LinearOpMode opMode, double tl) {
        block_servo = opMode.hardwareMap.get(Servo.class, "block_servo");
        cannon_motor = opMode.hardwareMap.get(DcMotor.class, "cannon_motor");
        position_close = block_servo.getPosition();
        position_open = block_servo.getPosition() + op;
        //block_servo.setPosition(position_close);
        time_limit = tl;
    }
    //включение-выключение пушки с задержкой после запуска
    public void cannon_on_off(){
        if (cannon_on && timer.seconds() >= time_limit) {cannon_motor.setPower(0); cannon_on = false; timer.startTime();}
        else if (timer.seconds() >= time_limit){cannon_motor.setPower(1); cannon_on = true; timer.startTime();}
        
    }

    //перевод сервы, блокирующей ИЭ, в противоположное положение
    public void manual_block_servo() {
        if (block_servo.getPosition() == position_close && timer_servo.seconds() >= time_limit) {block_servo.setPosition(position_open); timer_servo.startTime();}
        else if (timer_servo.seconds() >= time_limit) {block_servo.setPosition(position_close); timer_servo.startTime();}
    }
    //управление сервой, блокирующей ИЭ, напрямую
    public void cripple_block_servo(double gx) {
        block_servo.setPosition(block_servo.getPosition() + gx);
    }

    //автооткрытие сервы, блокирующей ИЭ, при работе пушки в течение 2 секунд
    public void auto_block_servo() {
        if (cannon_on && timer.seconds() >= 2){
            block_servo.setPosition(position_open);
        }
        else {block_servo.setPosition(position_close);}

    }
}
