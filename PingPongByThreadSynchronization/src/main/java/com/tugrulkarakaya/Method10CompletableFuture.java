package com.tugrulkarakaya;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Method10CompletableFuture {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private CompletableFuture<Void> pingFuture;
    private CompletableFuture<Void> pongFuture;

    public void start() {
        pingFuture = new CompletableFuture<>();
        pongFuture = new CompletableFuture<>();

        // Start ping-pong cycle
        CompletableFuture.runAsync(this::ping, executor);
        CompletableFuture.runAsync(this::pong, executor);
    }

    private void ping() {
        while (true) {
            try {
                pongFuture.get(); // Wait for pong
                System.out.println("Ping");
                
                // Reset futures
                pongFuture = new CompletableFuture<>();
                CompletableFuture<Void> oldPingFuture = pingFuture;
                pingFuture = new CompletableFuture<>();
                oldPingFuture.complete(null);
                
                Thread.sleep(100); // Optional: add small delay for better visualization
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void pong() {
        while (true) {
            try {
                pingFuture.get(); // Wait for ping
                System.out.println("Pong");
                
                // Reset futures
                pingFuture = new CompletableFuture<>();
                CompletableFuture<Void> oldPongFuture = pongFuture;
                pongFuture = new CompletableFuture<>();
                oldPongFuture.complete(null);
                
                Thread.sleep(100); // Optional: add small delay for better visualization
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
    }

    public static void main(String[] args) {
        Method10CompletableFuture app = new Method10CompletableFuture();
        app.start();
        
        // Let it run for a while
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        app.shutdown();
    }
} 