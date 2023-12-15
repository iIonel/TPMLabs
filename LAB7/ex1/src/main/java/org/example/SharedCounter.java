package org.example;
import java.util.concurrent.locks.Lock;
public class SharedCounter {
    private int count = 0;
    private SimpleReadWriteLock rwLock = new SimpleReadWriteLock();

    public void increment() {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            count++;
        } finally {
            writeLock.unlock();
        }
    }

    public int getCount() {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            return count;
        } finally {
            readLock.unlock();
        }
    }
}
