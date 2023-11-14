package org.example;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

class TTASlock{

    AtomicBoolean state = new AtomicBoolean(false);

    void lock() {
        while (true) {
            while (state.get()) {}
            if (!state.getAndSet(true))
                return;
        }
    }

    void unlock() {
        state.set(false);
    }
}