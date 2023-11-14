package org.example;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

class TASlock{

    AtomicBoolean state = new AtomicBoolean(false);

    void lock() {
        while (state.getAndSet(true)) {}
    }

    void unlock() {
        state.set(false);
    }
}