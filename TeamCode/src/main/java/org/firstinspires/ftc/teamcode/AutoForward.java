package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
@Config
public class AutoForward extends LinearOpMode {
    int target = 100;
    //длина/ширина 1 плитки в мм
    int plate = 610;
    private MechTrain mechTrain;
    private Cannon cannon;
    private CannonGate pollenGate;
    ElapsedTime timer =

    public void runOpMode()  throws InterruptedException{
        mechTrain = new MechTrain(this);
        pollenGate = new CannonGate(this, "pollenGate");
        cannon = new Cannon(this, "cannon_motor", pollenGate);
        waitForStart();
        telemetry.addData("мощность fl", mechTrain.front_left::getPower);
        telemetry.addData("мощность fr", mechTrain.front_right::getPower);
        telemetry.addData("мощность bl", mechTrain.back_left::getPower);
        telemetry.addData("мощность br", mechTrain.back_right::getPower);

        telemetry.addData("положение fl", mechTrain.front_left::getCurrentPosition);
        telemetry.addData("положение fr", mechTrain.front_right::getCurrentPosition);
        telemetry.addData("положение bl", mechTrain.back_left::getCurrentPosition);
        telemetry.addData("положение br", mechTrain.back_right::getCurrentPosition);

        telemetry.addData("погрешность передвижения", MechTrain.delta);

        mechTrain.auto_move_p(plate/2, plate*2, 0);
        cannon.cannon_on_off();
        sleep(500);
        cannon.cannon_on_off();
    }
}
