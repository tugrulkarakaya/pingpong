package com.tugrulkarakaya;

import java.util.Random;
import java.util.concurrent.*;

public class Method5FeatureTask {
    private final ExecutorService executor =  Executors.newFixedThreadPool(5);

    public Callable<Void> callablePong;
    public Callable<Void> callablePing;

    //Used in callable methods to make random Thread sleep.
    public static final Random rnd = new Random();

    public Method5FeatureTask() {
        callablePong = () -> {
            Thread.sleep(rnd.nextInt(4)*1000);
            System.out.println("Pong");
            //creating a new thread is actually costly, threadpools uses existing parked thread so we can gain some performance by using threadpool executor.
            executor.submit(callablePing);
            return null;
        };

        callablePing = () -> {
            Thread.sleep(rnd.nextInt(4)*1000);
            System.out.println("Ping");
            executor.submit(callablePong);
            return null;
        };

    }


    public static void main(String[] args) {
        Method5FeatureTask app = new Method5FeatureTask();
        app.executor.submit(app.callablePing); // ignite the threads :)
    }
}
