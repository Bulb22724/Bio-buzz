package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutoForward extends LinearOpMode {
    public MechTrain mechTrain;
    public void runOpMode() {
        mechTrain = new MechTrain(this);

        waitForStart();

        mechTrain.auto_move_forward_p(100);
    }
}
