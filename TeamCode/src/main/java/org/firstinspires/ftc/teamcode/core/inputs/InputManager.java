package org.firstinspires.ftc.teamcode.core.inputs;

public final class InputManager {
    public final Gamepad gamepad1 = new Gamepad();
    public final Gamepad gamepad2 = new Gamepad();

    public void update(
            com.qualcomm.robotcore.hardware.Gamepad gamepad1State,
            com.qualcomm.robotcore.hardware.Gamepad gamepad2State
    ) {
        gamepad1.update(gamepad1State);
        gamepad2.update(gamepad2State);
    }
}
