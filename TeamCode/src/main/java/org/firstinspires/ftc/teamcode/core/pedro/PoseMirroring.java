package org.firstinspires.ftc.teamcode.core.pedro;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.core.units.Units.Alliance;

public final class PoseMirroring {
    private static final PoseFactory DEGREES_MIRRORED_ACROSS_FIELD_CENTER = PoseFactory.degrees().mirrorX(72.0);

    private PoseMirroring() {
    }

    public static Pose mirror_if_blue(Pose pose, Alliance alliance) {
        if (alliance == Alliance.BLUE) {
            return PoseMirroring.DEGREES_MIRRORED_ACROSS_FIELD_CENTER.of(pose.x(), pose.y(), Math.toDegrees(pose.heading()));
        }
        return pose;
    }

    public static Pose mirror_if_red(Pose pose, Alliance alliance) {
        if (alliance == Alliance.RED) {
            return DEGREES_MIRRORED_ACROSS_FIELD_CENTER.of(pose.x(), pose.y(), Math.toDegrees(pose.heading()));
        }
        return pose;
    }
}
