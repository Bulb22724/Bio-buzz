package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.core.units.Units.Length.mm;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.core.control.periodicRegistry;
import org.firstinspires.ftc.teamcode.core.control.taskManager;
import org.firstinspires.ftc.teamcode.core.units.Units;

public class intake {
public static DcMotorEx intakeMotor;
public static DcMotorEx transferMotor;
private static ColorRangeSensor colorSensorLow;
private static ColorRangeSensor colorSensorHigh;
private enum IntakeMode {OFF,FORWARD,REVERSE}
private enum TransferMode{OFF, FORWARD, REVERSE}
private static IntakeMode intakeMode = IntakeMode.OFF;
private static TransferMode transferMode = TransferMode.OFF;
int intakePow = 1;
int transferPow= 1;
double transferStallCurrent;
public void init (HardwareMap hardwareMap){//tune directions of motors
    intakeMotor=hardwareMap.get(DcMotorEx.class, "intakeMotor");
    intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    transferMotor = hardwareMap.get(DcMotorEx.class, "transferMotor");
    transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    transferMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    colorSensorLow = hardwareMap.get(ColorRangeSensor.class,"colorSensorLow");
    colorSensorHigh = hardwareMap.get(ColorRangeSensor.class,"colorSensorHigh");
}
public void forward(){
    intakeMotor.setPower(intakePow);
    intakeMode = IntakeMode.FORWARD;
}
public void reverse(){
intakeMotor.setPower(-intakePow);
intakeMode = IntakeMode.REVERSE;
}
public void off(){
    intakeMotor.setPower(0);
    intakeMode = IntakeMode.OFF;
}
public void stop() {
    off();
    transferMotor.setPower(0);
    transferMode = TransferMode.OFF;
}
public void toggleForward() {
if (intakeMode!= IntakeMode.FORWARD){
    forward();
} else{
    off();
}
}
public void toggleReverse() {
    if (intakeMode!= IntakeMode.REVERSE){
            reverse();
    } else{
            off();
    }
}
public double getIntakePower() {return intakeMotor.getPower();}
    public static Units.Length getUpperDistance() {
        return mm(colorSensorHigh.getDistance(DistanceUnit.MM));
    }
    public static Units.Length getLowerDistance() {
        return mm(colorSensorLow.getDistance(DistanceUnit.MM));
    }
    private static final Units.Length emptyUpperDistance = mm(0);// tune pls, placeholder value
    private static final Units.Length emptyLowerDistance = mm(0);// tune pls, placeholder value
    public static boolean loaded() {
        boolean loaded = false;
        if (getLowerDistance().mm()< emptyLowerDistance.mm()&&getUpperDistance().mm()< emptyUpperDistance.mm()){
            loaded = true;
        }
        return loaded;
    }
    public static String getSensorTelemetry() {
        return getLowerDistance() + "\t" + getUpperDistance() + "\t" + loaded();
    }

    public void edgePollen(){
        if (transferMode==TransferMode.FORWARD||transferMode== TransferMode.REVERSE){
            return;
        }
        transferMotor.setPower(transferPow);
        transferMode = TransferMode.FORWARD;
        taskManager.runOnceDelayed("transferPulseOff", () -> {
            transferMotor.setPower(0);
            transferMode = TransferMode.OFF;
        }, Units.Time.s(0.2));
    }
    public static double getTransferPower() {
        return transferMotor.getPower();
    }

    public IntakeMode getIntakeMode() {
        return intakeMode;
    }
    public TransferMode getTransferMode(){
        return transferMode;
    }
}