package org.example;

public class ThreadTypeN extends Thread{
    Contor c;

    public ThreadTypeN(Contor c){
        this.c = c;
    }

    public void run(){
        int i = 0;
        while(i < 100000){
            this.c.incr();
            i++;
        }
    }
}
