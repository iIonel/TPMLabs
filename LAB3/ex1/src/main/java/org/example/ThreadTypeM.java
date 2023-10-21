package org.example;

public class ThreadTypeM extends Thread{
    Contor c;

    public ThreadTypeM(Contor c){
        this.c = c;
    }

    public void run(){
        int i = 0;
        while(i < 100000){
            try {
                this.c.getSem().acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                long temp = this.c.getValue();
                this.c.setValue(temp-1);
                i++;
                this.c.getSem().release();
            }
        }
    }
}
