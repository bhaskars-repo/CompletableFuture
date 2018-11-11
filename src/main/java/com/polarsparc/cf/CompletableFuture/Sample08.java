package com.polarsparc.cf.CompletableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample08 {
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
				return "I am Cool";
			});
			CompletableFuture<Void> cf3 = cf1.acceptEither(cf2, msg -> 
				System.out.printf("[1] [%s] %s and am NIMBLE !!!\n", Thread.currentThread().getName(), msg));
			
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
				return "I am Stunning";
			});
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
				randomDelay();
				return "I am Fast";
			});
			CompletableFuture<Void> cf3 = cf1.acceptEitherAsync(cf2, msg -> 
				System.out.printf("[2] [%s] %s and am SLICK !!!\n", Thread.currentThread().getName(), msg));
			
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
				return "I am Quick";
			});
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
				randomDelay();
				return "I am Elegant";
			});
			CompletableFuture<Void> cf3 = cf1.acceptEitherAsync(cf2, msg -> 
				System.out.printf("[3] [%s] %s and am NEW !!!\n", Thread.currentThread().getName(), msg),
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
