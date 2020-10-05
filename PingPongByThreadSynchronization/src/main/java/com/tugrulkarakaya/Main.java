package com.tugrulkarakaya;

public class Main {

    private boolean pongWait = true;

    public static void main(String[] args) {
        Main main = new Main();

        Thread pingThread = new Thread(main.pinger);
        Thread ponhThread = new Thread(main.ponger);

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
                pongWait = false;

                System.out.println("Ping");
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
                pongWait = true;

                System.out.println("Pong");
                notifyAll();
            }
        }
    };
}
