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
                exchanger.exchange(false); //it gets true from PONG and sends false to not allow it to print PONG but it will wait pong be written after first iteration
                System.out.println("PING");
                exchanger.exchange(true);
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
                if(exchanger.exchange(true)) { //this allows PING to print PING, but it will get false so it won't write PONG up until it gets trues from exchanger
                    System.out.println("PONG");
                    exchanger.exchange(true);
                }

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}