package com.tugrulkarakaya;

import java.util.concurrent.Exchanger;

public class Method7Exchanger {
    public static void main(String[] args)  {
        Exchanger<Boolean> exchanger = new Exchanger<>();
        Thread pingThread = new Thread(new Ping(exchanger));
        Thread pongThread = new Thread(new Pong(exchanger));
        pingThread.start();
        pongThread.start();
    }
}

class Ping implements Runnable {
    private final Exchanger<Boolean> exchanger;
    public Ping(Exchanger<Boolean> exchanger) {
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("PING");
                // Exchange after printing PING, sending true to allow PONG to print
                exchanger.exchange(true);
                Thread.sleep(100); // Optional: Add small delay for readability
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Pong implements Runnable {
    private final Exchanger<Boolean> exchanger;
    public Pong(Exchanger<Boolean> exchanger) {
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        try {
            while (true) {
                // Wait for signal from PING
                exchanger.exchange(false);
                System.out.println("PONG");
                Thread.sleep(100); // Optional: Add small delay for readability
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}