package com.tugrulkarakaya;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class Method9Phaser {
    private final Phaser phaserPing;
    private final Phaser phaserPong;

    public Method9Phaser() {
        this.phaserPing = new Phaser(1);
        this.phaserPong = new Phaser(1);
    }

    public static void main(String[] args) {
        Method9Phaser phaserExample = new Method9Phaser();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(phaserExample::printPing);
        service.submit(phaserExample::printPong);
    }

    public void printPing() {
        while (true) {
            phaserPing.awaitAdvance(phaserPing.getPhase());
            System.out.println("PING");
            phaserPong.arrive();
        }
    }

    public void printPong() {
        while (true) {
            phaserPing.arrive();
            phaserPong.awaitAdvance(phaserPong.getPhase());
            System.out.println("PONG");
        }
    }
}
