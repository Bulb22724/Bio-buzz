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
        Zaxvat zaxvat = new Zaxvat(this, "zaxvat_motor");
        pollenGate = new CannonGate(this, "pollenGate");
        waitForStart();

        telemetry.addLine("Управление ездой на левый стик");
        telemetry.addLine("a включает и вторым нажатием выключает пушку");
        telemetry.addLine("b включает и вторым нажатием выключает захват");
        telemetry.addLine("x переключает заслонку пыльцы");
        telemetry.addLine("");
        telemetry.addData("заслонка пыльцы закрыта", pollenGate::getCurrentlyExtended);
        telemetry.addData("состояние заслонки пыльцы", pollenGate.servo::getPosition);

        while (opModeIsActive()) {
            mechTrain.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);

            if (gamepad1.a && !last_g1a) {cannon.fireOnce(pollenGate);}
            
            if (gamepad1.b && !last_g1b) {zaxvat.capture();}

            if (gamepad1.x && !last_g1x) pollenGate.toggle();

            last_g1a = gamepad1.a;
            last_g1b = gamepad1.b;
            last_g1x = gamepad1.x;

            telemetry.update();
        }
    }
}
