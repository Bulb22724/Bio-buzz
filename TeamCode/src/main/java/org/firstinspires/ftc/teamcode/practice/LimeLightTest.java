package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mechanisms.HardwareConfig;

@Autonomous
public class LimeLightTest extends OpMode {

    HardwareConfig config = new HardwareConfig();
    private Limelight3A limelight3A;

    private double turnUntilTx; //Edit this however you like.

    @Override
    public void init() {
        config.init(hardwareMap);
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(0); // 0 is Pollen, 1 is Blue Nectar, and 2 is AprilTags
        turnUntilTx = 2;
    }

    @Override
    public void start() {
        limelight3A.start();
    }

    @Override
    public void loop() {
        LLResult llresult = limelight3A.getLatestResult();
        if ((llresult != null) && llresult.isValid()){
            telemetry.addData("Target X Offset", llresult.getTx());
            telemetry.addData("Target Y Offset", llresult.getTy());
            telemetry.addData("Target Area Offset", llresult.getTa());
        }
        if ((llresult != null) && llresult.isValid()){
            if (isPositive(llresult.getTx())){
                while (llresult.getTx() < 2) {
                    rotateClockwise(0.3);
                }
                stopMotor();
            }
            if (isNegative(llresult.getTx())) {
                while (llresult.getTx() > -2) {
                    rotateCounterClockwise(0.3);
                }
                stopMotor();
            }
        }
    }

    public void rotateClockwise(double power) {
        if (power > 1.0) power = 1.0;

        if (power < 0.0) power = 0.1;

        config.backRight.setPower(-power);
        config.backLeft.setPower(power);
        config.frontRight.setPower(-power);
        config.frontLeft.setPower(power);
    }
    public void rotateCounterClockwise(double power) {
        if (power > 1.0) power = 1.0;

        if (power < 0.0) power = 0.1;

        config.backRight.setPower(power);
        config.backLeft.setPower(-power);
        config.frontRight.setPower(power);
        config.frontLeft.setPower(-power);
    }

    public void stopMotor() {
        config.backRight.setPower(0);
        config.backLeft.setPower(0);
        config.frontRight.setPower(0);
        config.frontLeft.setPower(0);
    }

    public boolean isPositive(double number){
        boolean positive = false;
        if (number > 0) {
            positive  = true;
        }

        return positive;
    }

    public boolean isNegative(double number){
        boolean negative = false;
        if (number < 0) {
            negative  = true;
        }

        return negative;
    }
}
