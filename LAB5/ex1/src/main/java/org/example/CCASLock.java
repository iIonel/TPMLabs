package org.example;
import java.util.concurrent.atomic.AtomicInteger;

class CCASLock {
    private AtomicInteger state = new AtomicInteger(0);

    void lock() {
        while (true) {
            while (state.get() == 1) {}
            if (state.compareAndSet(0, 1)) {
                return;
            }
        }
    }

    void unlock() {
        state.set(0);
    }
}