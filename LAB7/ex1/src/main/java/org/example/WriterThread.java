package org.example;

class WriterThread extends Thread {
    private SharedCounter sharedCounter;

    public WriterThread(SharedCounter sharedCounter) {
        this.sharedCounter = sharedCounter;
    }

    public void run() {
        for (int i = 0; i < 100000; i++) {
            sharedCounter.increment();
            //System.out.println("W: " +  sharedCounter.getCount());
        }
    }
}
