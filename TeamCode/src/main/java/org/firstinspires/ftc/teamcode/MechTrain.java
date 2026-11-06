package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.List;

public class MechTrain {
    LinearOpMode opMode;
    DcMotor front_left, front_right, back_left, back_right;
    // ошибки к каждому мотору по их сокращенным названиям
    double e_f_l, e_f_r, e_b_l, e_b_r;
    // коэфициент i к каждому мотору по их сокращенным названиям
    double i_f_l, i_f_r, i_b_l, i_b_r;
    // прошлые ошибки к каждому мотору по их сокращенным названиям
    double el_f_l, el_f_r, el_b_l, el_b_r;
    // мощность подаваемая к каждому мотору по их сокращенным названиям
    double pow_f_l, pow_f_r, pow_b_l, pow_b_r;
    double delta = 10;
    double kp = 1;
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
        double tpr = 537.7; //тиков в полном обороте
        int diametr = 96; //Диаметр колеса в миллиметрах
        return ticks / tpr * diametr * PI;
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
    public double[] pid(double e, double i, double el) {
        double p = e * kp;
        i += e * kp * td;
        double d = kd * (e - el) / td;

        return new double[]{p + i + d, i, e};

    }

    /**
     * автономно доезжает до заданной позиции
     *
     * @param t позиция до которой надо доехать
     */
    public void auto_move(double t) {
        e_f_l = t - front_left.getCurrentPosition();
        e_f_r = t - front_right.getCurrentPosition();
        e_b_l = t - back_left.getCurrentPosition();
        e_b_r = t - back_right.getCurrentPosition();

        double[] f_l_pid = pid(e_f_l, i_f_l, el_f_l);
        pow_f_l = f_l_pid[0];
        i_f_l = f_l_pid[1];
        el_f_l = f_l_pid[2];

        double[] f_r_pid = pid(e_f_r, i_f_r, el_f_r);
        pow_f_r = f_r_pid[0];
        i_f_r = f_r_pid[1];
        el_f_r = f_r_pid[2];

        double[] b_l_pid = pid(e_b_l, i_b_l, el_b_l);
        pow_b_l = b_l_pid[0];
        i_b_l = b_l_pid[1];
        el_b_l = b_l_pid[2];

        double[] b_r_pid = pid(e_b_r, i_b_r, el_b_r);
        pow_f_l = b_r_pid[0];
        i_b_r = b_r_pid[1];
        el_b_r = b_r_pid[2];

        front_left.setPower(pow_f_l);
        front_right.setPower(pow_f_r);
        back_left.setPower(pow_b_l);
        back_right.setPower(pow_b_r);


    }

    public void auto_move_p(double t) {
        while (opMode.opModeIsActive() &&
                ((abs(e_f_l) > delta) ||
                (abs(e_f_r) > delta) ||
                (abs(e_b_l) > delta) ||
                (abs(e_b_r) > delta))) {

            e_f_l = t - front_left.getCurrentPosition();
            e_f_r = t - front_right.getCurrentPosition();
            e_b_l = t - back_left.getCurrentPosition();
            e_b_r = t - back_right.getCurrentPosition();

            front_left.setPower(e_f_l * kp);
            front_right.setPower(-e_f_r * kp);
            back_left.setPower(e_b_l * kp);
            back_right.setPower(-e_b_r * kp);
        }
    }
}
