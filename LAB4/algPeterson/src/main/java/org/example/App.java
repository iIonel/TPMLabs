package org.example;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class App
{
    private static final int THREAD_COUNT = 2;

    public static void main(String[] args) {
        Peterson petersonLock = new Peterson(THREAD_COUNT);
        Thread[] threads = new Thread[THREAD_COUNT];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                petersonLock.incrementSharedCounter(threadId);
            });
            threads[i].start();
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Timp:  " + totalTime + " ms");
        System.out.println("Valoare totala: " + petersonLock.getSharedCounter());

        int[] accessCounters = petersonLock.getAccessCounters();
        for (int i = 0; i < THREAD_COUNT; i++) {
            System.out.println("Thread " + i + " acces de  " + accessCounters[i] + " ori.");
        }

        int sum = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            sum += accessCounters[i];
        }
    }
}