package com.polarsparc.cf.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample07 {
	public static void main(String[] args) {
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Cool");
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "am Slick !!!");
			CompletableFuture<Void> cf3 = cf1.thenAcceptBoth(cf2, (s1, s2) -> 
				System.out.printf("[1] [%s] %s and %s\n", Thread.currentThread().getName(), s1, s2));
			
			try {
				cf3.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Fast");
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "am Nimble !!!");
			CompletableFuture<Void> cf3 = cf1.thenAcceptBothAsync(cf2, (s1, s2) -> 
				System.out.printf("[2] [%s] %s and %s\n", Thread.currentThread().getName(), s1, s2));
			
			try {
				cf3.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			ExecutorService executor = Executors.newFixedThreadPool(3);
			
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Stunning", executor);
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "am Quick !!!", executor);
			CompletableFuture<Void> cf3 = cf1.thenAcceptBothAsync(cf2, (s1, s2) -> 
				System.out.printf("[3] [%s] %s and %s\n", Thread.currentThread().getName(), s1, s2), executor);
			
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
