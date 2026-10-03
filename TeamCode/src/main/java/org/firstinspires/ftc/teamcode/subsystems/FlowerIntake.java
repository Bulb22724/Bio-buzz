package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FlowerIntake {
    private static Servo flowerServoLeft;
    private static Servo flowerServoRight;
    private static final  double flowerServoStartPose = 0; // should be 0 in optimal case
    private static final double flowerServoUpPose = 1;
    private enum FlowerIntakePose{UP,DOWN, }

    public void init(HardwareMap hardwareMap){
        flowerServoLeft = hardwareMap.get(Servo.class, "flowerServoLeft");
        flowerServoRight = hardwareMap.get(Servo.class, "flowerServoRight");

    }
}
