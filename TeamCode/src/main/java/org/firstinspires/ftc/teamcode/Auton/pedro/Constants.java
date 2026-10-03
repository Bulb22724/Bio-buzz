package org.firstinspires.ftc.teamcode.Auton.pedro;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("fl");
        c.frontRightName.set("fr");
        c.backLeftName.set("bl");
        c.backRightName.set("br");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(3.620598560243141);
        c.yPodOffset.set(5.864743810939038);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.19895884213394324);
                Controller secondaryTranslationalForward = Controller.proportional(0.07350997589031892);
                Controller primaryTranslationalLateral = Controller.proportional(0.240391037660847);
                Controller secondaryTranslationalLateral = Controller.proportional(0.08881806504885581);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01579117562970756));
                c.brake.set(Controller.proportionalFeedforward(0.013422499285251428));

                c.headingFeedback.set(Controller.proportional(2.462186337288268));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.02646535057646767, 0.011606302459953683));

                c.linearBrakeCoefficients.set(Matrix.diag(0.5528281933310141, 0.0578250391012415));
                c.quadraticBrakeCoefficients.set(Matrix.diag(-0.008710803729596048, 0.0025158223528998816));

                c.maxAchievableForwardVelocity.set(60.546185430465485);
                c.maxAchievableStrafeVelocity.set(50.48327014976264);
                c.naturalForwardDeceleration.set(29.19382416350979);
                c.naturalStrafeDeceleration.set(45.84797293885813);
            }
    );
}