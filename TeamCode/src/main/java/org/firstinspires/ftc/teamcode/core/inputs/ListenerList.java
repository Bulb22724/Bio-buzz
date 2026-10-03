package org.firstinspires.ftc.teamcode.core.inputs;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// CopyOnWriteArrayList: sicher, wenn ein Listener sich während execute() selbst entfernt.
class ListenerList<T> {

    private final List<T> listeners = new CopyOnWriteArrayList<>();

    void add(T listener) {
        listeners.add(listener);
    }

    void remove(T listener) {
        listeners.remove(listener);
    }

    void clear() {
        listeners.clear();
    }

    List<T> get() {
        return listeners;
    }

}
