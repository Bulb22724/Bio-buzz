package org.firstinspires.ftc.teamcode.core.inputs;

public class GamepadAnalogStick {

    private final org.firstinspires.ftc.teamcode.core.inputs.ListenerList<UpdateListener> listeners
            = new org.firstinspires.ftc.teamcode.core.inputs.ListenerList<>();

    public interface UpdateListener {
        void execute(double x, double y);
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

    // Gamepad-y ist invertiert; "nach oben" soll positiv sein.
    static double invertY(double y) {
        return -y;
    }

    void update(double x, double y) {
        for (UpdateListener listener : listeners.get()) {
            listener.execute(x, invertY(y));
        }
    }

}
