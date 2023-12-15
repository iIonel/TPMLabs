package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    public static void main(String[] args) throws InterruptedException {
        //clasic

        SharedCounter sharedCounter = new SharedCounter();
        int totalIncrement = 400000;
        int thNumbers = 4;

        ReaderThread[] readers = new ReaderThread[thNumbers];
        WriterThread[] writers = new WriterThread[thNumbers];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < thNumbers; i++) {
            writers[i] = new WriterThread(sharedCounter);
            readers[i] = new ReaderThread(sharedCounter, totalIncrement);
            writers[i].start();
            readers[i].start();
        }


        long endTime = System.currentTimeMillis();
        System.out.println("Total SimpleReadWriteLock: " + (endTime - startTime) + "ms");

        System.out.println();
        //another Lock

        SharedCounterR sharedCounterR = new SharedCounterR();

        ReaderThreadR[] readersR = new ReaderThreadR[thNumbers];
        WriterThreadR[] writersR = new WriterThreadR[thNumbers];

        startTime = System.currentTimeMillis();

        for (int i = 0; i < thNumbers; i++) {
            writersR[i] = new WriterThreadR(sharedCounterR);
            readersR[i] = new ReaderThreadR(sharedCounterR, totalIncrement);
            writersR[i].start();
            readersR[i].start();
        }

        endTime = System.currentTimeMillis();
        System.out.println("Total ReentrantLock: " + (endTime - startTime) + "ms");
    }
}
