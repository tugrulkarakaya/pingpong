package com.tugrulkarakaya;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Method11ReentrantLock {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pingCondition = lock.newCondition();
    private final Condition pongCondition = lock.newCondition();
    private boolean isPing = true;

    public void playPingPong(int n) {
        Thread pingThread = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (!isPing) {
                        pingCondition.await();
                    }
                    System.out.println("Ping");
                    isPing = false;
                    pongCondition.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread pongThread = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (isPing) {
                        pongCondition.await();
                    }
                    System.out.println("Pong");
                    isPing = true;
                    pingCondition.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        });

        pingThread.start();
        pongThread.start();

        try {
            pingThread.join();
            pongThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Method11ReentrantLock pingPong = new Method11ReentrantLock();
        pingPong.playPingPong(15);
    }
} 