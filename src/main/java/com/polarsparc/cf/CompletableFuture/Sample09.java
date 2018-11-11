package com.polarsparc.cf.CompletableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample09 {
	private static final Random random = new Random();
	
	public static final void randomDelay() {
		try {
			Thread.sleep(random.nextInt(500));
		}
		catch (Exception ex) {
			// Ignore
		}
	}
	
	// ----- Main -----
	
	public static void main(String[] args) {
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
				randomDelay();
				return "I am Awesome";
			});
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
				randomDelay();
				return "I am Bold";
			});
			CompletableFuture<Void> cf3 = cf1.applyToEither(cf2, msg -> String.format("%s and am Cool !!!", msg))
				.thenAccept(msg -> System.out.printf("[1] [%s] %s\n", Thread.currentThread().getName(), msg));
			
			try {
				cf3.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
				randomDelay();
				return "I am Elegant";
			});
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
				randomDelay();
				return "I am Fast";
			});
			CompletableFuture<Void> cf3 = cf1.applyToEitherAsync(cf2, msg -> String.format("%s and am New !!!",
					msg))
				.thenAcceptAsync(msg -> System.out.printf("[2] [%s] %s\n", Thread.currentThread().getName(), msg));
			
			try {
				cf3.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			ExecutorService executor = Executors.newFixedThreadPool(3);
			
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
				randomDelay();
				return "I am Practical";
			});
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
				randomDelay();
				return "I am Quick";
			});
			CompletableFuture<Void> cf3 = cf1.applyToEitherAsync(cf2, msg -> String.format("%s and am Radical !!!",
					msg), executor)
				.thenAcceptAsync(msg -> System.out.printf("[3] [%s] %s\n", Thread.currentThread().getName(), msg),
					executor);
			
			try {
				cf3.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
			
			executor.shutdown();
		}
	}
}
