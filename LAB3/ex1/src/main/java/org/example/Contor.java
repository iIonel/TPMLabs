package org.example;

import java.util.concurrent.Semaphore;

public class Contor {
    private long value;
    Semaphore sem;

    public Semaphore getSem() {
        return sem;
    }

    public void setSem(Semaphore sem) {
        this.sem = sem;
    }

    public Contor(long value){
        this.value = value;
        sem = new Semaphore(1);
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long incr(){
        long temp = this.value;
        this.value = temp + 1;
        return temp;
    }

    public long decr(){
        long temp = this.value;
        this.value = temp - 1;
        return temp;
    }
}
