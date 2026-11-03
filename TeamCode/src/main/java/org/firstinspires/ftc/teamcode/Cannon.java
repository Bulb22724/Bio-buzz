package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


// TODO: поставить точные значения для положения сервы, чтобы она доезжала до начальной позиции при старте
//TODO: Остслеживание состояния кнопки по предыдущему состоянию, удалить time_limit, удалить таймеры
public class Cannon {
    boolean cannon_on = false;
    double position_open = 0;
    double position_close = 0.5;
    public static double time_to_shoot = 2;
    DcMotor cannon_motor;
    //Servo block_servo;
    ElapsedTime timer = new ElapsedTime();

    public Cannon(LinearOpMode opMode, String motor_name) {
        //block_servo = opMode.hardwareMap.get(Servo.class, "block_servo");
        cannon_motor = opMode.hardwareMap.get(DcMotor.class, motor_name);

        //block_servo.setPosition(position_close);
    }

    //включение-выключение пушки с задержкой после запуска
    public void cannon_on_off() {
        if (cannon_on) {
            cannon_motor.setPower(0);
            cannon_on = false;
        }
        else {
            cannon_motor.setPower(1);
            cannon_on = true;
            timer.startTime();
        }

    }

    //перевод сервы, блокирующей ИЭ, в противоположное положение
    /*public void manual_block_servo() {
        if (block_servo.getPosition() == position_close) {
            block_servo.setPosition(position_open);
        } else {
            block_servo.setPosition(position_close);
        }
    }

    //управление сервой, блокирующей ИЭ, напрямую
    public void cripple_block_servo(double gx) {
        block_servo.setPosition(block_servo.getPosition() + gx);
    }

    //автооткрытие сервы, блокирующей ИЭ, при работе пушки в течение 2 секунд
    public void auto_block_servo() {
        if (cannon_on && timer.seconds() >= time_to_shoot) {
            block_servo.setPosition(position_open);
        } else {
            block_servo.setPosition(position_close);
        }

    }*/
}
