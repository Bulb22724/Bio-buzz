package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.mechanisms.HardwareConfig;

@TeleOp(name = "Feeder Test")
public class Feeder extends OpMode {
    HardwareConfig config = new HardwareConfig();


    @Override
    public void init() {
        config.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up){
            config.feederServo.setPower(0.3);
        }

        if (gamepad1.dpad_down){
            config.feederServo.setPower(-0.3);
        }
    }
}
