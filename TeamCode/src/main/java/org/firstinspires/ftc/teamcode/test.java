package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Test", group = "LinearOpMode")
public class test extends LinearOpMode {
    boolean last_g1a = false;
    boolean last_g1x = false;
    boolean last_g1b = false;
    private CannonGate pollenGate;

    //запуск режима телеопа
    public void runOpMode() throws InterruptedException {
        MechTrain mechTrain = new MechTrain(this);
        pollenGate = new CannonGate(this, "pollenGate");
        Cannon cannon = new Cannon(this, "cannon_motor", pollenGate);
        Zaxvat zaxvat = new Zaxvat(this, "zaxvat_motor");
        waitForStart();

        telemetry.addData("Положение заслонки", pollenGate.servo::getPosition);
        telemetry.addData("Скорость пушки", cannon::velocity_cannon);
        telemetry.addData("Достаточно для стрельбы", cannon::is_cannon_ready);

        while (opModeIsActive()) {
            mechTrain.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger + gamepad1.right_trigger);

            if (gamepad1.a && !last_g1a) {cannon.cannon_on_off();}

            if (gamepad1.b && !last_g1b) {zaxvat.capture();}

            if (gamepad1.x && !last_g1x) pollenGate.toggle();

            pollenGate.servo.setPosition(1 - ((gamepad1.right_stick_y + 1) / 2));

            last_g1a = gamepad1.a;
            last_g1b = gamepad1.b;
            last_g1x = gamepad1.x;

            //cannon.testVelocityAndSetGate(pollenGate);
            telemetry.update();
        }
    }

}
