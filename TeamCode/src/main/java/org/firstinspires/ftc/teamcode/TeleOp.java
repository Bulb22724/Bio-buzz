package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "LinearOpMode")
public class TeleOp extends LinearOpMode {
    double rotation = 0;
    boolean last_g1a = false;
    boolean last_g1x = false;
    boolean last_g1b = false;
    private CannonGate pollenGate;

    //запуск режима телеопа
    public void runOpMode() throws InterruptedException {
        MechTrain mechTrain = new MechTrain(this);
        Cannon cannon = new Cannon(this, "cannon_motor");
        Zaxvat zaxvat = new Zaxvat(this);
        pollenGate = new CannonGate(this, "pollenGate");
        waitForStart();

        telemetry.addLine("'Лёше лень писать инструкцию.' -Михаил белый");
        telemetry.addLine("'Жаль что надо'. - Лёша");
        telemetry.addLine();
        telemetry.addLine("");
        telemetry.addData("заслонка пыльцы закрыта", pollenGate::getCurrentlyExtended);
        telemetry.addData("состояние заслонки пыльцы", pollenGate.servo::getPosition);

        while (opModeIsActive()) {
            mechTrain.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger + gamepad1.right_trigger);

            if (gamepad1.a && !last_g1a) {cannon.cannon_on_off();}
            
            //if (gamepad1.x && !last_g1x) {cannon.manual_block_servo();} //cannon.auto_block_servo();

            if (gamepad1.b && !last_g1b) {zaxvat.capture();}

            if (gamepad1.y && !last_g1x) pollenGate.toggle();
            last_g1a = gamepad1.a;
            last_g1b = gamepad1.b;
            last_g1x = gamepad1.x;

//            cannon.cripple_block_servo(gamepad1.right_stick_x);
            telemetry.update();

        }
    }

}
