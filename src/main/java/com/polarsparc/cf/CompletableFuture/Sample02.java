package com.polarsparc.cf.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample02 {
	public static void main(String[] args) {
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Cool");
			CompletableFuture<Void> cf2 = cf1.thenAccept(msg -> 
				System.out.printf("[1] [%s] %s and am also Awesome\n", Thread.currentThread().getName(), msg));
			
			try {
				cf2.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			ExecutorService executor = Executors.newSingleThreadExecutor();
			
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am New", executor);
			CompletableFuture<Void> cf2 = cf1.thenAccept(msg -> 
				System.out.printf("[2] [%s] %s and am also Smart\n", Thread.currentThread().getName(), msg));
			
			executor.shutdown();
			
			try {
				cf2.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Fast");
			CompletableFuture<Void> cf2 = cf1.thenAcceptAsync(msg -> 
				System.out.printf("[3] [%s] %s and am also Elegant\n", Thread.currentThread().getName(), msg));
			
			try {
				cf2.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		{
			ExecutorService executor = Executors.newFixedThreadPool(2);
			
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Slick", executor);
			CompletableFuture<Void> cf2 = cf1.thenAcceptAsync(msg -> 
				System.out.printf("[4] [%s] %s and am also Nimble\n", Thread.currentThread().getName(), msg),
				executor);
			
			executor.shutdown();
			
			try {
				cf2.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	}
}
