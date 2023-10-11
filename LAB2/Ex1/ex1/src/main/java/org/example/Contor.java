package org.example;

public class Contor {
    private long value;

    public Contor(long value){
        this.value = value;
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
