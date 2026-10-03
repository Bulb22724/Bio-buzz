package org.firstinspires.ftc.teamcode.opModes.teleOp.comp;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.pedro.PoseMirroring;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.opModes.teleOp.AbstractTeleOp;
import org.firstinspires.ftc.teamcode.subsystems.intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter;

@TeleOp(name = "Main opMode", group = "main")
public class main extends AbstractTeleOp {
    private static final PoseFactory POSE_FACTORY = PoseFactory.degrees();

    private Robot robot;
    private shooter shooterSubsystem;
    private intake intakeSubsystem;

    public main() {
        compMode = true;
    }

    @Override
    protected void onInit() {
        robot = new Robot();
        robot.initRobot(hardwareMap, follower, telemetry, panelsEnabled());
        shooterSubsystem = robot.getShooter();
        intakeSubsystem = robot.getIntake();
        setTelemetryManager(robot.getTelemetryManager());

        inputManager.gamepad2.dpad_right.addButtonPressListener(() -> resetPose(9.0, 9.0));
        inputManager.gamepad2.dpad_left.addButtonPressListener(() -> resetPose(9.0, 133.0));
        inputManager.gamepad2.b.addButtonPressListener(shooterSubsystem::toggleBypass);
        inputManager.gamepad2.left_bumper.addButtonPressListener(() -> adjustShooterTargetRpm(-100));
        inputManager.gamepad2.right_bumper.addButtonPressListener(() -> adjustShooterTargetRpm(100));
    }

    @Override
    protected void onStart() {
        robot.startRobot();
    }

    @Override
    protected void opModeLoop() {
        robot.updateRobot(alliance);
    }

    private void adjustShooterTargetRpm(int delta) {
        if (shooterSubsystem.isBypassEnabled()) {
            shooterSubsystem.adjustTargetRpm(delta);
        } else {
            gamepad2.rumble(0.0, 1.0, 200);
        }
    }

    private void resetPose(double x, double y) {
        Pose startPose = POSE_FACTORY.of(x, y, 90.0);
        follower.setPose(PoseMirroring.mirror_if_blue(startPose, alliance));
        follower.update();
    }

    @Override
    protected void opModeStop() {
        if (robot != null) {
            robot.stopRobot(alliance);
        }
    }
}
