package com.polarsparc.cf.CompletableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Sample11 {
	private static final Random random = new Random();
	
	// ----- Main -----
	
	public static void main(String[] args) {
		{
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
				int n = random.nextInt(1000) + 1;
				System.out.printf("[1] [%s] Random number is %d\n", Thread.currentThread().getName(), n);
				return n;
			}, executor);
			
			CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> {
				int n = random.nextInt(100) + 1;
				System.out.printf("[2] [%s] Random number is %d\n", Thread.currentThread().getName(), n);
				return n;
			}, executor);
			
			CompletableFuture<Integer> cf3 = cf1.thenCombineAsync(cf2, (n1, n2) -> {
				int n = n1.intValue() % n2.intValue();
				if (n <= 0) {
					throw new RuntimeException(String.format("n1 = %d, n2 = %d => Invalid combination", 
						n1.intValue(), n2.intValue()));
				}
				return n;
			}, executor);
			cf3.thenAcceptAsync(n -> System.out.printf("[3] [%s] Transformed number is %d\n",
				Thread.currentThread().getName(), n), executor);
			
			CompletableFuture<Integer> cf4 = cf3.thenApplyAsync(n -> {
				int r = random.nextInt(5) + 1;
				System.out.printf("[4] [%s] Random seed is %d\n", Thread.currentThread().getName(), r);
				return n * r;
			}, executor);
			cf4.thenAcceptAsync(n -> System.out.printf("[5] [%s] Final number is %d\n", 
				Thread.currentThread().getName(), n), executor);
			cf4.thenRun(() -> System.out.printf("[6] [%s] Done !!!\n", Thread.currentThread().getName()));
			
			try {
				cf4.get(1000, TimeUnit.MILLISECONDS);
			} catch (Exception ex) {
				System.out.printf("EXCEPTION:: %s\n", ex.getMessage());
			}
			
			// Sleep 1 second before shutting down executor
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
				// Ignore
			}

			executor.shutdown();
		}
	}
}
