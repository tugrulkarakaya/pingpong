package com.tugrulkarakaya;

import java.util.concurrent.*;

public class Main {

    ExecutorService service;
    CyclicBarrier c1 = new CyclicBarrier(2);
    CyclicBarrier c2 = new CyclicBarrier(2);

    public static void main(String[] args) {
        Main m = new Main();
        m.runPingPong();
    }

    public void runPingPong(){
        service = Executors.newFixedThreadPool(2);

        while(true) {
            service.submit(() -> this.printPing(c1, c2));
            service.submit(() -> this.printPong(c1, c2));
        }
    }



    public void printPing(CyclicBarrier c1, CyclicBarrier c2) {
        try {
            c1.await();
            System.out.println("PING");
            c2.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    public void printPong(CyclicBarrier c1, CyclicBarrier c2){
        try {
            c1.await();
            c2.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            //handle here
        }
        System.out.println("PONG");
    }
}