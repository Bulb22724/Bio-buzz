package org.firstinspires.ftc.teamcode.shooter;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;

import java.util.function.BooleanSupplier;

public class ShooterCommands {
    public Command shooting(Shooter shooter) {
        return new InstantCommand(shooter::shoot, shooter);
    }

    public Command increaseShotPower(Shooter shooter) {
        return new InstantCommand(shooter::increasePower, shooter);
    }

    public Command decreaseShotPower(Shooter shooter) {
        return new InstantCommand(shooter::decreasePower, shooter);
    }

    public Command stop(Shooter shooter) {
        return new InstantCommand(shooter::stopMotor, shooter);
    }

    public Command resetShot(Shooter shooter) {
        return new InstantCommand(shooter::resetShotPower, shooter);
    }

    public Command runServo(Shooter shooter) {
        return new InstantCommand(shooter::runServo);
    }
}
