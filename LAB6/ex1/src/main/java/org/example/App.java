package org.example;

import org.example.TASlock;

import java.util.concurrent.atomic.AtomicInteger;

public class App {
    public static void main(String[] args) {
        final int numThreads = 4;
        final int totalIncrementLimit = 300000;
        final AtomicInteger sharedCounter = new AtomicInteger(0);
        final AtomicInteger[] localCounters = new AtomicInteger[numThreads];

        TASlock lock = new TASlock();
        CCASLock lock1 = new CCASLock();

        for (int i = 0; i < numThreads; i++) {
            localCounters[i] = new AtomicInteger(0);
        }

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                int localCounter = 0;
                while (sharedCounter.get() < totalIncrementLimit) {
                    lock1.lock();
                    try {
                        if (sharedCounter.get() < totalIncrementLimit) {
                            sharedCounter.getAndIncrement();
                            localCounter++;
                        }
                    } finally {
                        lock1.unlock();
                    }
                }
                localCounters[threadId].set(localCounter);
            });
        }

        long startTime = System.currentTimeMillis();

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        int totalIncrements = 0;
        for (int i = 0; i < numThreads; i++) {
            totalIncrements += localCounters[i].get();
            System.out.println("Thread " + i + ":" + localCounters[i]);
        }
        System.out.println("Counter:  " + sharedCounter.get());
        System.out.println("All counters: " + totalIncrements);
        System.out.println("Time: " + (endTime - startTime) + " ms");
    }
}


