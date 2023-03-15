package com.tugrulkarakaya;

        import java.util.concurrent.CountDownLatch;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

public class Method8Latches {

    private CountDownLatch latchPing;
    private CountDownLatch latchPong;

    public Method8Latches() {
        this.latchPing = new CountDownLatch(1);
        this.latchPong = new CountDownLatch(1);
    }

    public static void main(String[] args) {
        Method8Latches latchExampler = new Method8Latches();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(latchExampler::printPing);
        service.submit(latchExampler::printPong);
    }

    public void printPing() {
        while (true) {
            try {
                latchPing.await();
                System.out.println("PING");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latchPing = new CountDownLatch(1);
                latchPong.countDown();
            }
        }
    }

    public void printPong() {
        while (true) {
            try {
                latchPing.countDown();
                latchPong.await();
                System.out.println("PONG");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally {
                latchPong = new CountDownLatch(1);
            }
        }
    }
}
