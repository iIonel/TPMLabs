package org.example;

class ReaderThread extends Thread {
    private SharedCounter sharedCounter;
    private int readOperations = 0;
    private final int expectedCount;

    public ReaderThread(SharedCounter sharedCounter, int expectedCount) {
        this.sharedCounter = sharedCounter;
        this.expectedCount = expectedCount;
    }

    public void run() {
        while (sharedCounter.getCount() < expectedCount) {
            readOperations++;
        }
        //System.out.println("T: " + readOperations);
    }
}
