package com.polarsparc.cf.CompletableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample10 {
	private static final Random random = new Random();
	
	// ----- Main -----
	
	public static void main(String[] args) {
		// No explicit exception handling
		{
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> random.nextInt(1000)+1, executor);
			CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> random.nextInt(100)+1, executor);
			CompletableFuture<Void> cf3 = cf1.thenCombineAsync(cf2, (n1, n2) -> {
				int ret = n1.intValue() % n2.intValue();
				if (ret <= 0) {
					throw new RuntimeException(String.format("n1 = %d, n2 = %d => Invalid combination", 
						n1.intValue(), n2.intValue()));
				}
				return ret;
			}, executor)
			.thenAcceptAsync(n -> System.out.printf("[1] [%s] Magic number is %d\n", Thread.currentThread().getName(), 
				n), executor);
			
			try {
				cf3.get();
			} catch (Exception ex) {
				System.out.printf("[1] EXCEPTION:: %s\n", ex.getMessage());
			}
			
			executor.shutdown();
		}
		
		// Using exceptionally
		{
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> random.nextInt(1000)+1, executor);
			CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> random.nextInt(100)+1, executor);
			CompletableFuture<Void> cf3 = cf1.thenCombineAsync(cf2, (n1, n2) -> {
				int ret = n1.intValue() % n2.intValue();
				if (ret <= 0) {
					throw new RuntimeException(String.format("n1 = %d, n2 = %d => Invalid combination", 
						n1.intValue(), n2.intValue()));
				}
				return ret;
			}, executor)
			.exceptionally(ex -> {
				System.out.printf("[2] ERROR:: %s\n", ex.getMessage());
				return -1;
			})
			.thenAcceptAsync(n -> System.out.printf("[2] [%s] Magic number is %d\n", Thread.currentThread().getName(), 
				n), executor);
			
			try {
				cf3.get();
			} catch (Exception ex) {
				System.out.printf("[2] EXCEPTION:: %s\n", ex.getMessage());
			}
			
			executor.shutdown();
		}
		
		// Using handle (or handleAsync)
		{
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> random.nextInt(1000)+1, executor);
			CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> random.nextInt(100)+1, executor);
			CompletableFuture<Void> cf3 = cf1.thenCombineAsync(cf2, (n1, n2) -> {
				int ret = n1.intValue() % n2.intValue();
				if (ret <= 0) {
					throw new RuntimeException(String.format("n1 = %d, n2 = %d => Invalid combination", 
						n1.intValue(), n2.intValue()));
				}
				return ret;
			}, executor)
			.handle((n, ex) -> {
				if (n != null) {
					return n;
				} else {
					System.out.printf("[3] ERROR:: %s\n", ex.getMessage());
					return -1;
				}
			})
			.thenAcceptAsync(n -> System.out.printf("[3] [%s] Magic number is %d\n", Thread.currentThread().getName(), 
				n), executor);
			
			try {
				cf3.get();
			} catch (Exception ex) {
				System.out.printf("[3] EXCEPTION:: %s\n", ex.getMessage());
			}
			
			executor.shutdown();
		}
		
		// Using whenComplete
		{
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> random.nextInt(1000)+1, executor);
			CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> random.nextInt(100)+1, executor);
			CompletableFuture<Integer> cf3 = cf1.thenCombineAsync(cf2, (n1, n2) -> {
				int ret = n1.intValue() % n2.intValue();
				if (ret <= 0) {
					throw new RuntimeException(String.format("n1 = %d, n2 = %d => Invalid combination", 
						n1.intValue(), n2.intValue()));
				}
				return ret;
			}, executor)
			.whenComplete((n, ex) -> {
				if (n != null) {
					System.out.printf("[4] [%s] Magic number is %d\n", Thread.currentThread().getName(), n);
				} else {
					System.out.printf("[4] ERROR:: %s\n", ex.getMessage());
				}
			});
			
			try {
				cf3.get();
			} catch (Exception ex) {
				System.out.printf("[4] EXCEPTION:: %s\n", ex.getMessage());
			}
			
			executor.shutdown();
		}
	}
}
