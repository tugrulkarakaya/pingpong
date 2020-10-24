package com.tugrulkarakaya;

import java.util.concurrent.*;

public class Method6CompletionService {
    private final ExecutorService executor =  Executors.newCachedThreadPool();// pools are great for re-using same thread so gain more performance. But be careful (especially for ThreadLocal variables) to clean variable before termination

    public CompletionService<Void> pingCompletionService = new ExecutorCompletionService<>(executor);
    public CompletionService<Void> pongCompletionService = new ExecutorCompletionService<>(executor);

    Callable<Void> callablePong = () -> {
        System.out.println("Pong");
        return null;
    };

    Callable  callablePing = () -> {
        System.out.println("Ping");
        return null;
    };

    public ExecutorService getExecutor() {
        return executor;
    }

    public static void main(String[] args) {
        Method6CompletionService app = new Method6CompletionService();
        app.getExecutor().submit(()-> app.ping());
        app.getExecutor().submit(()-> app.pong());
    }

    public void pong(){
        while (true) {
            try {
                pongCompletionService.submit(callablePong);
                pingCompletionService.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void ping(){
        while (true) {
            try {
                //on the contrary of pong method. first wait completion of pong print and then submit a new ping thread.
                pongCompletionService.take(); //if pong callable is not executed yet wait for it
                pingCompletionService.submit(callablePing);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
