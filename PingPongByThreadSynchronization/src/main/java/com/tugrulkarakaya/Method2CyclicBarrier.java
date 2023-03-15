package com.tugrulkarakaya;

import java.util.concurrent.*;

public class Method2CyclicBarrier {

    ExecutorService service;
    CyclicBarrier c1 = new CyclicBarrier(2);
    CyclicBarrier c2 = new CyclicBarrier(2);

    public static void main(String[] args) {
        Method2CyclicBarrier m = new Method2CyclicBarrier();
        m.runPingPong();
    }

    public void runPingPong(){
        service = Executors.newFixedThreadPool(2);
            service.submit(this::printPing);
            service.submit(this::printPong);
    }



    public void printPing() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                c1.await();
                System.out.println("PING");
                c2.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            catch(BrokenBarrierException ex){

            }
        }
    }

    public void printPong(){
        while(!Thread.currentThread().isInterrupted()) {
            try {
                c1.await();
                c2.await();
                System.out.println("PONG");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch(BrokenBarrierException ex){

            }
        }
    }
}