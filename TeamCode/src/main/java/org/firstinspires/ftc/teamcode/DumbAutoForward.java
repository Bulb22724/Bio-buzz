package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class DumbAutoForward extends LinearOpMode {
    public MechTrain mechTrain;
    public void runOpMode() {
        mechTrain = new MechTrain(this);

        waitForStart();

        mechTrain.move(0, 1, 0);
        sleep(300);
        mechTrain.move(0, 0, 0);
    }
}
