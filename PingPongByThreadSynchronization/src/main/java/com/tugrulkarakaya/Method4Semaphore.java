package com.tugrulkarakaya;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Method4Semaphore {

    private int pingPermit = 1;
    private int pongPermit = 0; //Pong should wait ping do it job first

    private Semaphore semaphorePing;
    private Semaphore semaphorePong;

    public Method4Semaphore() {
        this.semaphorePing = new Semaphore(pingPermit);
        this.semaphorePong = new Semaphore(pongPermit);
    }

    public static void main(String[] args) {
        Method4Semaphore semaphoreExampler = new Method4Semaphore();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(()-> semaphoreExampler.printPing());
        service.submit(()-> semaphoreExampler.printPong());
    }

    public void printPing()  {
        while(true) {
            try {
                semaphorePing.acquire();
                System.out.println("PING");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            finally {
                semaphorePong.release();
            }

        }
    }

    public void printPong() {
        while(true) {
            try {
                semaphorePong.acquire();
                System.out.println("PONG");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            finally {
                semaphorePing.release();
            }
        }
    }
}
