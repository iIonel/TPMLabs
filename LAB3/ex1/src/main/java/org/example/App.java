package org.example;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class App
{
    public static void main( String[] args ) throws InterruptedException {

        int n,m;
        Scanner input = new Scanner(System.in);

        System.out.print("N: ");
        n = input.nextInt();

        System.out.println();

        System.out.print("M: ");
        m = input.nextInt();

        ArrayList<Thread> threadListN = new ArrayList<>();
        ArrayList<Thread> threadListM = new ArrayList<>();
        Contor c = new Contor(0);

        long start = System.currentTimeMillis();

        for(int i = 0; i < n; ++i){
            Thread thN = new ThreadTypeN(c);
            threadListN.add(thN);
            thN.start();
        }

        for(int i = 0; i < m; ++i) {
            Thread thM = new ThreadTypeM(c);
            threadListM.add(thM);
            thM.start();
        }

        for(int i = 0; i < n; ++i){
            threadListN.get(i).join();
        }

        for(int i = 0; i < m; ++i){
            threadListM.get(i).join();
        }

        long end = System.currentTimeMillis();
        long time = end - start;

        System.out.println(c.getValue());
        System.out.println(time + "ms");
    }
}
