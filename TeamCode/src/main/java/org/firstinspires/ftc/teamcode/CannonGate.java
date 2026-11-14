package org.firstinspires.ftc.teamcode;

// напиши управление на прямую сервой, для калибровки на всякий.
// желательно ещё и автоматическое открытие и закрытия заслонки по времени, как пушка раскрученна
// может я не увидел, но почему заслонка при старте не приезжает в начальное положение?
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 *  Заслонка стрельбы
 */
public class CannonGate {
    public final Servo servo;

    private boolean currentlyExtended;

    /**
     * Создать новую заслонку, останавливающую игровые элементы по пути к
     * механизму стрельбы.
     * @param opMode (LinearOpMode) - opMode, к которому привязана заслонка
     * @param servoName (String) - название сервопривода в hardwareMap, при
     *                  помощи которого приводится в движение заслонка.
     */
    public CannonGate(LinearOpMode opMode, String servoName) {
        this(opMode.hardwareMap.get(Servo.class, servoName));
    }

    /**
     * Создать новую заслонку, останавлювающую игровые элементы по пути к
     * механизму стрельбы
     * @param servo (Servo) - сервопривод, при помощи которого приводится в
     *              движение заслонка.
     */
    public CannonGate(Servo servo) {
        this.servo = servo;
    }

    /**
     * Установить состояние закрытости заслонки.
     * @param extended (boolean) - должна ли заслонка закрыться.
     */
    public void setExtended(boolean extended) {
        servo.setPosition(extended ? 1.0 : 0.5);
        currentlyExtended = extended;
    }

    /**
     * @return (boolean) - состояние закрытости заслонки.
     */
    public boolean getCurrentlyExtended() {
        return currentlyExtended;
    }

    /**
     * Установить состояние закрытости, обратное установленному на данный момент.
     */
    public void toggle() {
        setExtended(!getCurrentlyExtended());
    }
}
