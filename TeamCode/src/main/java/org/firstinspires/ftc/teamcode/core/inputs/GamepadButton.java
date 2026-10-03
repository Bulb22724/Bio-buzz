package org.firstinspires.ftc.teamcode.core.inputs;

public class GamepadButton {

    private final ListenerList<ButtonListener> pressListeners = new ListenerList<>();
    private final ListenerList<ButtonListener> releaseListeners = new ListenerList<>();

    boolean state = false;

    public interface ButtonListener {
        void execute();
    }

    public void addButtonPressListener(ButtonListener listener) {
        pressListeners.add(listener);
    }

    public void removeButtonPressListener(ButtonListener listener) {
        pressListeners.remove(listener);
    }

    public void clearButtonPressListeners() {
        pressListeners.clear();
    }

    public void addButtonReleaseListener(ButtonListener listener) {
        releaseListeners.add(listener);
    }

    public void removeButtonReleaseListener(ButtonListener listener) {
        releaseListeners.remove(listener);
    }

    public void clearButtonReleaseListeners() {
        releaseListeners.clear();
    }

    void update(boolean newState) {
        if (newState != state) {
            if (newState) {
                onPress();
            } else {
                onRelease();
            }
            state = newState;
        }
    }

    void onPress() {
        for (ButtonListener listener : pressListeners.get()) {
            listener.execute();
        }
    }

    void onRelease() {
        for (ButtonListener listener : releaseListeners.get()) {
            listener.execute();
        }
    }

}
