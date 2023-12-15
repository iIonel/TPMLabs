package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleReadWriteLock {
    private int readers = 0;
    private boolean flag = false;
    private Lock helperLock = new ReentrantLock();
    private Condition condition = helperLock.newCondition();
    private Lock readLock = new ReadLock();
    private Lock writeLock = new WriteLock();

    public Lock readLock() {
        return readLock;
    }

    public Lock writeLock() {
        return writeLock;
    }

    private class ReadLock implements Lock {
        public void lock() {
            helperLock.lock();
            try {
                while (flag) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {}
                }
                readers++;
            } finally {
                helperLock.unlock();
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        public void unlock() {
            helperLock.lock();
            try {
                readers--;
                if (readers == 0) {
                    condition.signalAll();
                }
            } finally {
                helperLock.unlock();
            }
        }

        @Override
        public Condition newCondition() {
            return null;
        }

        // Implement other Lock methods as needed
    }

    private class WriteLock implements Lock {
        public void lock() {
            helperLock.lock();
            try {
                while (readers > 0 || flag) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {}
                }
                flag = true;
            } finally {
                helperLock.unlock();
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        public void unlock() {
            helperLock.lock();
            try {
                flag = false;
                condition.signalAll();
            } finally {
                helperLock.unlock();
            }
        }

        @Override
        public Condition newCondition() {
            return null;
        }

    }
}

