package com.tugrulkarakaya;

public class Main {

    private boolean pongWait = true;

    public static void main(String[] args) {
        Main main = new Main();

        Thread ping = new Thread(main.pinger);
        Thread pong = new Thread(main.ponger);

        ping.start();
        pong.start();
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

                notifyAll();
                System.out.println("Pong");
            }
        }
    };
}
