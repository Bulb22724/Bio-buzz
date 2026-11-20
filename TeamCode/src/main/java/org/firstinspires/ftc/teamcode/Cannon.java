package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@Config
public class Cannon {
    private boolean cannon_on = false;
    //скорость пушки при 12 вольтах
    public static final double VELOCITY_AT_12V = 1800;
    // погрешность в скорости пушки
    public static final double DELTA_VELOCITY = 40;
    public final DcMotorEx cannon_motor;
    private final VoltageSensor volt_sensor;
    private final CannonGate linkedGate;

    public Cannon(LinearOpMode opMode, String motor_name, CannonGate gate) {
        cannon_motor = opMode.hardwareMap.get(DcMotorEx.class, motor_name);
        volt_sensor = opMode.hardwareMap.voltageSensor.iterator().next();
        linkedGate = gate;
    }

    //включение-выключение пушки с задержкой после запуска
    public void cannon_on_off() {
        cannon_on = !cannon_on;
        cannon_motor.setPower(cannon_on ? 1 : 0);
    }
    //возращает раскрутился ли мотор на свою максимальную скорость
    public boolean is_cannon_ready() {
        double current_speed = velocity_cannon();
        double max_velocity_now = VELOCITY_AT_12V * volt_sensor.getVoltage() / 12;
        return current_speed >= max_velocity_now - DELTA_VELOCITY;
    }

    // возвращает вращается ли колесо пушки
    public boolean is_cannon_on() {return cannon_on;}

    // возвращает скорость колеса пушки
    public double velocity_cannon() {return cannon_motor.getVelocity();}

    /**
     * Проверить, является ли скорость мотора стрельбы достаточной
     * для выстрела и установить состояние заслонки соответственно.
     */
    public void testVelocityAndSetGate() {
        /*
            Пушка готова    Пушка включена    Заслонка закрыта
            да              да                нет
            нет             да                да
            нет             нет               да
         */
        linkedGate.setExtended(!(cannon_on && is_cannon_ready()));
    }
}
