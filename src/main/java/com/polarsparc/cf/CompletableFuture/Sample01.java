package com.polarsparc.cf.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample01 {
	public static void main(String[] args) {
		{
			CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
				System.out.printf("[%s] I am Cool\n", Thread.currentThread().getName());
			});
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}

		{
			CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
				System.out.printf("[%s] Am Awesome\n", Thread.currentThread().getName());
				return null;
			});
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	
		{
			ExecutorService executor = Executors.newSingleThreadExecutor();
			
			CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
				System.out.printf("[%s] And am Smart\n", Thread.currentThread().getName());
				return null;
			}, executor);
			
			executor.shutdown();
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	}
}
