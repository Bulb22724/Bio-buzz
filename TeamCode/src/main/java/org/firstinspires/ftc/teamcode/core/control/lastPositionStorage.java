package org.firstinspires.ftc.teamcode.core.control;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.core.units.Units.Alliance;
import org.firstinspires.ftc.teamcode.core.units.Units.Time;


public abstract class lastPositionStorage {

    static boolean dataStored = false;
    static Time storageTime = Time.s(0);
    final static Time dataValidDuration = Time.s(200);

    static Pose lastPosition = new Pose(0.0, 0.0, 0.0);
    static Alliance currentAlliance = Alliance.BLUE;

    public static void storeData(Pose position, Alliance alliance) {
        dataStored = true;
        lastPosition = position;
        currentAlliance = alliance;
        storageTime = Time.ms(System.currentTimeMillis());
    }

    public static boolean validDataAvailable() {
        return storageTime.ms() + dataValidDuration.ms() > System.currentTimeMillis();
    }

    public static Pose getLastPosition() {
        return lastPosition;
    }

    public static Alliance getCurrentAlliance() {
        return currentAlliance;
    }
}