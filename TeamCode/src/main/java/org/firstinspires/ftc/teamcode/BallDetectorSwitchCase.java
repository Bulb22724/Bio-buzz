package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Ball Detector Switch Case")
public class BallDetectorSwitchCase extends LinearOpMode {
    private ColorSensor colorSensor;
    private DcMotor intakeMotor;
    private enum BallColor {
        RED,
        BLUE,
        YELLOW,
        UNKNOWN
    }

    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");

        telemetry.addData("Status", "Initialized. Ready to start.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            BallColor detectBallColor = getBallColor();


            switch (detectBallColor) {
                case YELLOW:
                    intakeMotor.setPower(1.0);
                    telemetry.addData("Ball Status", "Yellow ball detected = , need to turn on the intake motor.");
                    break;

                case RED:
                    intakeMotor.setPower(0.0);
                    telemetry.addData("Ball Status", "Red ball detected, do not take the ball, go away and search for yellow balls.");
                    break;

                case BLUE:
                    intakeMotor.setPower(0.0);
                    telemetry.addData("Ball Status", "Blue ball detected, do not take the ball, go away and search for yellow balls.");
                    break;

                case UNKNOWN:
                default:
                    intakeMotor.setPower(0.0);
                    telemetry.addData("Ball Status", "Searching for balls...");
                    break;
            }

            telemetry.addData("Red Value", colorSensor.red());
            telemetry.addData("Green Value", colorSensor.green());
            telemetry.addData("Blue Value", colorSensor.blue());
            telemetry.update();
        }
    }

    private BallColor getBallColor() {
        double r = colorSensor.red();
        double g = colorSensor.green();
        double b = colorSensor.blue();

        if (r < 30.0 && g < 30.0 && b < 30.0) {
            return BallColor.UNKNOWN;
        }

        if (r > 50.0 && g > 50.0 && b < 40.0) {
            return BallColor.YELLOW;
        }

        else if (r > g && r > b) {
            return BallColor.RED;
        }

        else if (b > r && b > g) {
            return BallColor.BLUE;
        }

        return BallColor.UNKNOWN;
    }
}
