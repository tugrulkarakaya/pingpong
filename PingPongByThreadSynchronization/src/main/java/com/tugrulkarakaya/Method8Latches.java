package com.tugrulkarakaya;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates ping-pong synchronization using CountDownLatches.
 * Each thread waits for its latch to be counted down before proceeding,
 * then counts down the other thread's latch.
 */
public class Method8Latches {
    private volatile boolean running = true;
    private CountDownLatch latchPing;
    private CountDownLatch latchPong;

    public Method8Latches() {
        this.latchPing = new CountDownLatch(1);
        this.latchPong = new CountDownLatch(1);
    }

    public static void main(String[] args) {
        Method8Latches latchExample = new Method8Latches();
        ExecutorService service = Executors.newFixedThreadPool(2);
        
        try {
            service.submit(latchExample::printPing);
            service.submit(latchExample::printPong);
            
            // Let it run for a while
            Thread.sleep(1000);
            
            // Graceful shutdown
            latchExample.stop();
            service.shutdown();
            if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running = false;
        // Count down both latches to prevent deadlock during shutdown
        latchPing.countDown();
        latchPong.countDown();
    }

    public void printPing() {
        while (running) {
            try {
                latchPing.await();
                if (!running) break;
                System.out.println("PING");
                Thread.sleep(100); // Add small delay for better readability

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                latchPing = new CountDownLatch(1);
                latchPong.countDown();
            }
        }
    }

    public void printPong() {
        while (running) {
            try {
                latchPing.countDown();
                latchPong.await();
                if (!running) break;
                System.out.println("PONG");
                Thread.sleep(100); // Add small delay for better readability

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                latchPong = new CountDownLatch(1);
            }
        }
    }
}
