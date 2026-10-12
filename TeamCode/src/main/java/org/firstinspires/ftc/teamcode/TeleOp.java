package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "LinearOpMode")
public class TeleOp extends LinearOpMode {
    MechTrain mechTrain;
    Cannon cannon;
    Zaxvat zaxvat;
    // время для затупа операторов
    public double time_limit = 0.5;
    double rotation = 0;
    public void runOpMode() throws InterruptedException {
        mechTrain = new MechTrain(this);

        waitForStart();
        while (opModeIsActive()) {


            mechTrain.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger + gamepad1.right_trigger);

            if (gamepad1.a) {cannon.cannon_on_off();}
            if (gamepad1.x) {cannon.manual_block_servo();} //cannon.auto_block_servo();

            if (gamepad1.b) {zaxvat.capture();}

//            cannon.cripple_block_servo(gamepad1.right_stick_x);

            telemetry.addLine("'Лёше лень писать инструкцию.' -Михаил белый");
            telemetry.addLine("'Жаль что надо'. - Лёша");
            telemetry.addLine();
            telemetry.addLine("");
            telemetry.update();

        }
    }

}
