package org.example;

class WriterThreadR extends Thread {
    private SharedCounterR sharedCounter;

    public WriterThreadR(SharedCounterR sharedCounter) {
        this.sharedCounter = sharedCounter;
    }

    public void run() {
        for (int i = 0; i < 100000; i++) {
            sharedCounter.increment();
        }
    }
}
