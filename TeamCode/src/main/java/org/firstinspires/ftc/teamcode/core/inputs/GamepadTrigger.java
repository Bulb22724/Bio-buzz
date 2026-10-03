package org.firstinspires.ftc.teamcode.core.inputs;

public class GamepadTrigger {

    private static final double FULL_PRESS_THRESHOLD = 0.95;

    private final ListenerList<TriggerListener> fullPressListeners = new ListenerList<>();
    private final ListenerList<TriggerListener> releaseListeners = new ListenerList<>();
    private final ListenerList<TriggerListener> pressListeners = new ListenerList<>();

    double value;

    public interface TriggerListener {
        void execute(double value);
    }

    public void addFullPressListener(TriggerListener listener) {
        fullPressListeners.add(listener);
    }

    public void removeFullPressListener(TriggerListener listener) {
        fullPressListeners.remove(listener);
    }

    public void clearFullPressListeners() {
        fullPressListeners.clear();
    }

    public void addPressListener(TriggerListener listener) {
        pressListeners.add(listener);
    }

    public void removePressListener(TriggerListener listener) {
        pressListeners.remove(listener);
    }

    public void clearPressListeners() {
        pressListeners.clear();
    }

    public void addTriggerReleaseListener(TriggerListener listener) {
        releaseListeners.add(listener);
    }

    public void removeTriggerReleaseListener(TriggerListener listener) {
        releaseListeners.remove(listener);
    }

    public void clearTriggerReleaseListeners() {
        releaseListeners.clear();
    }

    void update(double newValue) {
        if (newValue != value) {
            if (newValue >= FULL_PRESS_THRESHOLD) {
                onFullPress();
            } else if (value >= FULL_PRESS_THRESHOLD) {
                onRelease(newValue);
            } else {
                onPress(newValue);
            }
            value = newValue;
        }
    }

    void onFullPress() {
        for (TriggerListener listener : fullPressListeners.get()) {
            listener.execute(1);
        }
    }

    void onPress(double value) {
        for (TriggerListener listener : pressListeners.get()) {
            listener.execute(value);
        }
    }

    // Übergibt jetzt den echten Wert statt hartcodierter 0.
    void onRelease(double value) {
        for (TriggerListener listener : releaseListeners.get()) {
            listener.execute(value);
        }
    }

}
