package org.example;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Peterson {
    private AtomicIntegerArray level;
    private AtomicIntegerArray victim;
    private int sharedCounter = 0;
    private AtomicIntegerArray accessCounters;
    private static final int LIMIT = 300000;

    public Peterson(int n) {
        level = new AtomicIntegerArray(n);
        victim = new AtomicIntegerArray(n);
        accessCounters = new AtomicIntegerArray(n);
    }

    public void lock(int i) {
        for (int L = 1; L < level.length(); L++) {
            level.set(i, L);
            victim.set(L, i);
            while (exists(i, L) && victim.get(L) == i) {
            }
        }
    }

    public void unlock(int i) {
        level.set(i, 0);
    }

    private boolean exists(int i, int L) {
        for (int k = 0; k < level.length(); k++) {
            if (k != i && level.get(k) >= L) {
                return true;
            }
        }
        return false;
    }

    public void incrementSharedCounter(int threadId) {
        while (sharedCounter < LIMIT) {
            lock(threadId);
            if (sharedCounter < LIMIT) {
                sharedCounter++;
                accessCounters.incrementAndGet(threadId);
            }
            unlock(threadId);
        }
    }

    public int getSharedCounter() {
        return sharedCounter;
    }

    public int[] getAccessCounters() {
        int[] counters = new int[accessCounters.length()];
        for (int i = 0; i < accessCounters.length(); i++) {
            counters[i] = accessCounters.get(i);
        }
        return counters;
    }
}
