package org.example;

public class ThreadTypeM extends Thread{
    Contor c;

    public ThreadTypeM(Contor c){
        this.c = c;
    }

    public void run(){
        int i = 0;
        while(i < 100000){
            this.c.decr();
            i++;
        }
    }
}
