package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@Config
public class Cannon {
    public static final int SPINUP_TIME_SECONDS = 3;
    boolean cannon_on = false;
    //скорость пушки при 12 вольтах
    public static final double velocity_at_12v = 120;
    double max_velocity_now = 0;
    double current_speed = 0;
    // погрешность в скорости пушки
    public static double delta_velocity = 40;
    DcMotorEx cannon_motor;
    VoltageSensor volt_sensor;
    private final LinearOpMode opMode;

    public Cannon(LinearOpMode opMode, String motor_name) {
        cannon_motor = opMode.hardwareMap.get(DcMotorEx.class, motor_name);
        volt_sensor = opMode.hardwareMap.voltageSensor.iterator().next();
        this.opMode = opMode;
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

    // TODO: сделать linkedGate полем в Cannon, не параметром


    public void toggleFire(CannonGate linkedGate) {
        cannon_on_off();
        // Если заслонка на данный момент блокирует ИЭ, нужно
        // разогнать колесо и только потом разблокировать.
        if (linkedGate.getCurrentlyExtended()) {
            ElapsedTime timer = new ElapsedTime(0);
            while (timer.time(TimeUnit.SECONDS) < SPINUP_TIME_SECONDS
                && opMode.opModeIsActive()) {

            }
        }
        linkedGate.toggle();
    }
}
