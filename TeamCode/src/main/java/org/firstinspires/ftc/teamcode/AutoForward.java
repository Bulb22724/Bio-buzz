package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Config
public class AutoForward extends LinearOpMode {
    int target = 100;
    public MechTrain mechTrain;
    public void runOpMode() {
        mechTrain = new MechTrain(this);

        waitForStart();

        mechTrain.auto_move_forward_p(target);
    }
}
