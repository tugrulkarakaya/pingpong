package com.tugrulkarakaya;

public class WaitInterrupt {

    private boolean pongWait = true;

    public static void main(String[] args) {
        WaitInterrupt waitInterrupt = new WaitInterrupt();

        Thread pingThread = new Thread(waitInterrupt.pinger);
        Thread ponhThread = new Thread(waitInterrupt.ponger);

        pingThread.start();
        ponhThread.start();
    }


    Runnable pinger = ()->{
        for(;;) {
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
        for(;;) {
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
