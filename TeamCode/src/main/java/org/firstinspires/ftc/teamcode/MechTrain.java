package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.List;

@Config
public class MechTrain {
    public static final double KB_GEAR_RATIO = 32.0 / 18.0;
    public static final double ENCODER_TICKS_PER_REVOLUTION = 751.8347107438016 / KB_GEAR_RATIO;
    public static final int WHEEL_DIAMETER_MM = 96;
    LinearOpMode opMode;
    DcMotor front_left, front_right, back_left, back_right;
    // ошибки к каждому мотору по их сокращенным названиям
    // максимальная погрешность, при которой продолжается автоматическое движение
    public static double delta = 10;
    public static double kp = 500;
    double ki = 0;
    double kd = 0;
    double td = 0.025;
    public MechTrain(LinearOpMode opMode) {
        front_left = opMode.hardwareMap.get(DcMotor.class, "front_left");
        front_right = opMode.hardwareMap.get(DcMotor.class, "front_right");
        back_left = opMode.hardwareMap.get(DcMotor.class, "back_left");
        back_right = opMode.hardwareMap.get(DcMotor.class, "back_right");

        front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.opMode = opMode;
    }

    //управление движением
    public void move(double g1x, double g1y, double rt) {
        front_left.setPower(g1y - g1x + rt);
        front_right.setPower(-(g1y + g1x - rt));
        back_left.setPower(g1y + g1x + rt);
        back_right.setPower(-(g1y - g1x - rt));
    }

    /**
     * Метод преобразования тиков мотора в мм пройденные
     */
    public double ticksToMm(int ticks){
        return ticks * (WHEEL_DIAMETER_MM * PI / ENCODER_TICKS_PER_REVOLUTION);
    }

    public int mmToTicks(double mm) {
        return (int) (mm * (ENCODER_TICKS_PER_REVOLUTION / WHEEL_DIAMETER_MM * PI));
    }

    /**
     * Метод, который устанавливает режим для всех моторов КБ
     *
     * @param mode - режим вращения
     */
    public void allSetMode(DcMotor.RunMode mode) {
        front_left.setMode(mode);
        front_right.setMode(mode);
        back_left.setMode(mode);
        back_right.setMode(mode);
    }
    //Возвращает [мощность мотора, i, предыдущая ошибка]
    public List<Double> calc_e(int tx, int ty, int tr){
        double ex_f_l = tx - front_left.getCurrentPosition();
        double ex_f_r = tx - front_right.getCurrentPosition();
        double ex_b_l = tx - back_left.getCurrentPosition();
        double ex_b_r = tx - back_right.getCurrentPosition();

        double ey_f_l = ty - front_left.getCurrentPosition();
        double ey_f_r = ty - front_right.getCurrentPosition();
        double ey_b_l = ty - back_left.getCurrentPosition();
        double ey_b_r = ty - back_right.getCurrentPosition();

        double er_f_l = tr - front_left.getCurrentPosition();
        double er_f_r = tr - front_right.getCurrentPosition();
        double er_b_l = tr - back_left.getCurrentPosition();
        double er_b_r = tr - back_right.getCurrentPosition();

        double efull_f_l = ey_f_l - ex_f_l + er_f_l;
        double efull_f_r = -ey_f_r - ex_f_r + er_f_r;
        double efull_b_l = ey_b_l + ex_b_l + er_b_l;
        double efull_b_r = -ey_b_r + ex_b_r + er_b_r;

        return List.of(efull_f_l, efull_f_r, efull_b_l, efull_b_r);
    }
    /**
     * Проехать вперёд
     * @param ty (double) целевое положение в мм
     */
    public void auto_move_p(double tx, double ty, double tr) {
        int target_y = mmToTicks(ty);
        int target_x = mmToTicks(tx);
        int target_r = mmToTicks(tr);

        allSetMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        allSetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        List<Double> list_e = calc_e(target_x, target_y, target_r);

        double e_f_l = list_e.get(0);
        double e_f_r = list_e.get(1);
        double e_b_l = list_e.get(2);
        double e_b_r = list_e.get(3);

        while (opMode.opModeIsActive() &&
                ((e_f_l > delta) ||
                (-e_f_r > delta) ||
                (e_b_l > delta) ||
                (-e_b_r > delta))) {

            list_e = calc_e(target_x, target_y, target_r);

            e_f_l = list_e.get(0);
            e_f_r = list_e.get(1);
            e_b_l = list_e.get(2);
            e_b_r = list_e.get(3);

            front_left.setPower(e_f_l / kp);
            front_right.setPower(e_f_r / kp);
            back_left.setPower(e_b_l / kp);
            back_right.setPower(e_b_r / kp);

            opMode.telemetry.addData("ошибка переднего левого", e_f_l);
            opMode.telemetry.addData("ошибка переднего правого", e_f_r);
            opMode.telemetry.addData("ошибка заднего левого", e_b_l);
            opMode.telemetry.addData("ошибка заднего правого", e_b_r);

            opMode.telemetry.update();
        }
        move(0, 0, 0);

    }

    public void auto_move_forward(double distance_mm) {
        double power = 1;
        int distance_ticks = mmToTicks(distance_mm);

        allSetMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        allSetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        opMode.telemetry.addData("положение frontleft", front_left::getCurrentPosition);
        opMode.telemetry.addData("положение frontright", front_right::getCurrentPosition);
        opMode.telemetry.addData("положение backleft", back_left::getCurrentPosition);
        opMode.telemetry.addData("положение backright", back_right::getCurrentPosition);

        while (opMode.opModeIsActive()
                && (front_left.getCurrentPosition() < distance_ticks
                    || front_right.getCurrentPosition() < distance_ticks
                    || back_right.getCurrentPosition() < distance_ticks
                    || back_left.getCurrentPosition() < distance_ticks)
        ) {
            front_left.setPower(power);
            front_right.setPower(-power);
            back_left.setPower(power);
            back_right.setPower(-power);

            power = (double) (distance_ticks - front_right.getCurrentPosition()) / distance_ticks;

            opMode.telemetry.update();
        }

        front_left.setPower(0);
        front_right.setPower(0);
        back_left.setPower(0);
        back_right.setPower(0);
    }

}
