package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

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

    public int mmToTicks(double mm) {
        final double tpr = 537.7;
        final int diameter = 96;
        return (int) (mm * (tpr * diameter * PI));
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
        front_right.setPower(-pow_f_r);
        back_left.setPower(pow_b_l);
        back_right.setPower(-pow_b_r);


    }

    public void auto_move_forward_p(int ty) {
        double target_y = ticksToMm(ty);

        allSetMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        allSetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opMode.opModeIsActive() &&
                ((abs(e_f_l) > delta) ||
                (abs(e_f_r) > delta) ||
                (abs(e_b_l) > delta) ||
                (abs(e_b_r) > delta))) {

            e_f_l = target_y - front_left.getCurrentPosition();
            e_f_r = target_y + front_right.getCurrentPosition();
            e_b_l = target_y - back_left.getCurrentPosition();
            e_b_r = target_y + back_right.getCurrentPosition();

            front_left.setPower(e_f_l * kp);
            front_right.setPower(-e_f_r * kp);
            back_left.setPower(e_b_l * kp);
            back_right.setPower(-e_b_r * kp);
        }
        move(0, 0, 0);
    }

    public void auto_move_right_p(int tx) {
        double target_x = ticksToMm(tx);

        allSetMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        allSetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opMode.opModeIsActive() &&
                ((abs(e_f_l) > delta) ||
                        (abs(e_f_r) > delta) ||
                        (abs(e_b_l) > delta) ||
                        (abs(e_b_r) > delta))) {

            e_f_l = target_x + front_left.getCurrentPosition();
            e_f_r = target_x - front_right.getCurrentPosition();
            e_b_l = target_x - back_left.getCurrentPosition();
            e_b_r = target_x + back_right.getCurrentPosition();

            front_left.setPower(-e_f_l * kp);
            front_right.setPower(e_f_r * kp);
            back_left.setPower(e_b_l * kp);
            back_right.setPower(-e_b_r * kp);
        }
        move(0, 0, 0);
    }
    //
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
