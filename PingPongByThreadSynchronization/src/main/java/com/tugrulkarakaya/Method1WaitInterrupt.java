package com.tugrulkarakaya;

public class Method1WaitInterrupt {

    private boolean pongWait = true;

    public static void main(String[] args) throws InterruptedException {
        Method1WaitInterrupt waitInterrupt = new Method1WaitInterrupt();

        Thread pingThread = new Thread(waitInterrupt.pinger);
        Thread pongThread = new Thread(waitInterrupt.ponger);

        pingThread.start();
        pongThread.start();

        //stop threads after 5 seconds
        Thread.sleep(5*1000);
        pingThread.interrupt();
        pongThread.interrupt();
    }


    Runnable pinger = ()->{
        while(!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                while (!pongWait) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println("Ping");
                pongWait = false;
                notifyAll();
            }
        }
    };

    Runnable ponger = ()->{
        while(!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                while (pongWait) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Pong");
                pongWait = true;
                notifyAll();
            }
        }
    };
}
