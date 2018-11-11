package com.polarsparc.cf.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample06 {
	public static void main(String[] args) {
		// Explicit style
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I'm Cool");
			CompletableFuture<String> cf2 = cf1.thenCompose(s ->
				CompletableFuture.supplyAsync(() -> s + " & am SLICK !!!"));
			CompletableFuture<Void> cf = cf2.thenAccept(msg ->
				System.out.printf("[1] [%s] %s\n", Thread.currentThread().getName(), msg));
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		// Fluent style
		{
			CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> "I'm Smart")
				.thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " & am NIMBLE !!!"))
				.thenAccept(msg -> 
					System.out.printf("[2] [%s] %s\n", Thread.currentThread().getName(), msg));
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		// Fluent style using Async
		{
			CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> "I'm Awesome")
					.thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> s + " & am FAST !!!"))
					.thenAcceptAsync(msg -> 
						System.out.printf("[3] [%s] %s\n", Thread.currentThread().getName(), msg));
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		// Fluent style using Async with an Executor
		{
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> "I'm Awesome", executor)
					.thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> s + " & am FAST !!!"),
						executor)
					.thenAcceptAsync(msg -> 
						System.out.printf("[4] [%s] %s\n", Thread.currentThread().getName(), msg),
						executor);
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
			
			executor.shutdown();
		}
	}
}
