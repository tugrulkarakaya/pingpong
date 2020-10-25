package com.tugrulkarakaya;

public class Method1WaitInterrupt {

    private boolean pongWait = true;

    public static void main(String[] args) {
        Method1WaitInterrupt waitInterrupt = new Method1WaitInterrupt();

        Thread pingThread = new Thread(waitInterrupt.pinger);
        Thread ponhThread = new Thread(waitInterrupt.ponger);

        pingThread.start();
        ponhThread.start();
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
