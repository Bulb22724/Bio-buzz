package org.firstinspires.ftc.teamcode.core.inputs;

import org.firstinspires.ftc.teamcode.core.inputs.GamepadAnalogStick;

public class GamepadAnalogSticks {

    private final ListenerList<UpdateListener> listeners = new ListenerList<>();

    public interface UpdateListener {
        void execute(double left_x, double left_y, double right_x, double right_y);
    }

    public void addUpdateListener(UpdateListener listener) {
        listeners.add(listener);
    }

    public void removeUpdateListener(UpdateListener listener) {
        listeners.remove(listener);
    }

    public void clearUpdateListenerList() {
        listeners.clear();
    }

    void update(double left_x, double left_y, double right_x, double right_y) {
        for (UpdateListener listener : listeners.get()) {
            listener.execute(left_x, GamepadAnalogStick.invertY(left_y), right_x, GamepadAnalogStick.invertY(right_y));
        }
    }

}
