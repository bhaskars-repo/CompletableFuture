package com.polarsparc.cf.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample03 {
	public static void main(String[] args) {
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Cool");
			CompletableFuture<String> cf2 = cf1.thenApply(msg -> {
				System.out.printf("[1] [%s] %s\n", Thread.currentThread().getName(), msg);
				return String.format("%s and AWESOME !!!", msg);
			});
		
			try {
				String msg = cf2.get();
				System.out.printf("[1] %s\n", msg);
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			ExecutorService executor = Executors.newSingleThreadExecutor();
			
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am New");
			CompletableFuture<String> cf2 = cf1.thenApply(msg -> {
				System.out.printf("[2] [%s] %s\n", Thread.currentThread().getName(), msg);
				return String.format("%s and SMART !!!", msg);
			});
			
			executor.shutdown();
			
			try {
				String msg = cf2.get();
				
				System.out.printf("[2] %s\n", msg);
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Fast");
			CompletableFuture<String> cf2 = cf1.thenApplyAsync(msg -> {
				System.out.printf("[3] [%s] %s\n", Thread.currentThread().getName(), msg);
				return String.format("%s and ELEGANT !!!", msg);
			});
		
			try {
				String msg = cf2.get();
				
				System.out.printf("[3] %s\n", msg);
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			ExecutorService executor = Executors.newFixedThreadPool(2);
			
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Slick");
			CompletableFuture<String> cf2 = cf1.thenApplyAsync(msg -> {
				System.out.printf("[4] [%s] %s\n", Thread.currentThread().getName(), msg);
				return String.format("%s and NIMBLE !!!", msg);
			}, executor);
			
			executor.shutdown();
		
			try {
				String msg = cf2.get();
				
				System.out.printf("[4] %s\n", msg);
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	}
}
