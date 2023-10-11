package org.example;

public class Contor {
    private long value;

    public Contor(long value){
        this.value = value;
    }

    public synchronized long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public synchronized long incr(){
        long temp = this.value;
        this.value = temp + 1;
        return temp;
    }

    public synchronized long decr(){
        long temp = this.value;
        this.value = temp - 1;
        return temp;
    }
}
