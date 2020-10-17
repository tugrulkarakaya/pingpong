package com.tugrulkarakaya;

import java.util.concurrent.*;

public class Method3BlockingQueue {
    BlockingQueue<Object> pingQueue = new LinkedBlockingQueue(1);
    BlockingQueue pongQueue = new LinkedBlockingQueue<>(1);

    public static void main(String[] args) {
        Method3BlockingQueue producerConsumer = new Method3BlockingQueue();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(()-> producerConsumer.printPing());
        service.submit(()-> producerConsumer.printPong());
    }

    public void printPing()  {
        while(true) {
            System.out.println("PING");
            try {
                pongQueue.put(new Object());
                pingQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public void printPong() {
        while(true) {
            try {
                pongQueue.take();
                System.out.println("PONG");
                pingQueue.put(new Object());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
