package org.firstinspires.ftc.teamcode.TurretCode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

// note to self there are still errors in here
public class TurretAutoAlignOpMode extends OpMode {

    private Limelight3A Limelight;
    private TurretTurningMechanism turret = new TurretTurningMechanism();

    double[] stepSizes = {0.1, 0.01, 0.001, 0.0001, 0.00001};

    int stepIndex= 2;

    @Override
    public void init(){
       Limelight.start();
        turret.init(hardwareMap);

        telemetry.addLine("Initialized all Mechanisms");
    }
    @Override
    public void start(){
        turret.resetTimer();

    }

    @Override
    public void loop(){
        //vision Logic

        // update code on the fly, Left/Right adjusts kP, Up/Down adjusts kD
        if (gamepad1.bWasPressed()){
            stepIndex= (stepIndex+1) %stepSizes.length;
        }
        // Left= decrease kP by step
        if (gamepad1.dpadLeftWasPressed())
        {
            turret.setkP(turret.getkP() - stepSizes[stepIndex]);
        }
        // Right= increase kP by step
        if (gamepad1.dpadRightWasPressed())
        {
            turret.setkP(turret.getkP() + stepSizes[stepIndex]);
        }
        // Up= increase kD by step
        if (gamepad1.dpadUpWasPressed())
        {
            turret.setkD(turret.getkD() + stepSizes[stepIndex]);
        }
        // Down= decrease kD by step
        if (gamepad1.dpadDownWasPressed())
        {
            turret.setkD(turret.getkD() - stepSizes[stepIndex]);
        }
        //Limelight.update(); (everything commented is full of errors)
        //AprilTagDetection id20 = Limelight.getTagBySpecificID(20);

        // turret.update(20);
        // change ID and "AprilTagDetection"
       // if(id20 != null ){
        //    telemetry.addData("Cur ID",Limelight);
        //} else
        //{
        //    telemetry.addLine("No aprilTag Detected, stopping turret Motor");
        //}
            telemetry.addData("Current step Index",stepIndex);
            telemetry.addData("Current step Size", stepSizes);




    }



}
