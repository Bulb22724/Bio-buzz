package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Cannon {
    boolean cannon_on = false;
    //скорость пушки при 12 вольтах
    public static double velocity_at_12v = 120;
    double max_velocity_now = 0;
    double current_speed = 0;
    // погрешность в скорости пушки
    public static double delta_velocity = 40;
    DcMotorEx cannon_motor;
    VoltageSensor volt_sensor;

    public Cannon(LinearOpMode opMode, String motor_name) {
        cannon_motor = opMode.hardwareMap.get(DcMotorEx.class, motor_name);
        volt_sensor = opMode.hardwareMap.voltageSensor.iterator().next();
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
        }
    }
    //возращает раскрутился ли мотор на свою максимальную скорость
    public boolean is_cannon_ready() {
        current_speed = cannon_motor.getVelocity();
        max_velocity_now = velocity_at_12v * volt_sensor.getVoltage()/12;
        return current_speed >= max_velocity_now - delta_velocity;
    }

    // возвращает вращается ли колесо пушки
    public boolean is_cannon_on() {return cannon_on;}

    // возвращает скорость колеса пушки
    public double velocity_cannon() {return cannon_motor.getVelocity();}
}
