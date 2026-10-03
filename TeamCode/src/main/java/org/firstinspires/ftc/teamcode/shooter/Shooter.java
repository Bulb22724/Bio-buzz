package org.firstinspires.ftc.teamcode.shooter;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.util.SubsystemIF;

public class Shooter extends SubsystemIF {

    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    public Shooter(Telemetry telemetry, HardwareMap hardwareMap) {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void periodic() {

    }


}
