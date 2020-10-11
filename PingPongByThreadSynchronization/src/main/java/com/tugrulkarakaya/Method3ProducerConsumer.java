package com.tugrulkarakaya;

import java.util.concurrent.*;

public class Method3ProducerConsumer {
    BlockingQueue<Object> pingQueue = new LinkedBlockingQueue(1);
    BlockingQueue pongQueue = new LinkedBlockingQueue<>(1);

    public static void main(String[] args) {
        Method3ProducerConsumer producerConsumer = new Method3ProducerConsumer();
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
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }
}
