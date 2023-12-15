package org.example;

public class ReaderThreadR extends Thread {
    private SharedCounterR sharedCounter;
    private int readOperations = 0;
    private final int expectedCount;

    public ReaderThreadR(SharedCounterR sharedCounter, int expectedCount) {
        this.sharedCounter = sharedCounter;
        this.expectedCount = expectedCount;
    }

    public void run() {
        while (sharedCounter.getCount() < expectedCount) {
            readOperations++;
        }
        System.out.println("T: " + readOperations);
    }
}
