package com.polarsparc.cf.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sample05 {
	public static void main(String[] args) {
		// Explicit style
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I'm Cool");
			CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "am Slick !!!");
			CompletableFuture<String> cf3 = cf1.thenCombine(cf2,
				(s1, s2) -> String.format("%s AND %s", s1, s2));
			CompletableFuture<Void> cf = cf3.thenAccept(msg ->
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
				.thenCombine(CompletableFuture.supplyAsync(() -> "am Nimble !!!"),
					(s1, s2) -> String.format("%s AND %s", s1, s2))
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
				.thenCombineAsync(CompletableFuture.supplyAsync(() -> "am Fast !!!"),
					(s1, s2) -> String.format("%s AND %s", s1, s2))
				.thenAcceptAsync(msg -> 
					System.out.printf("[3] [%s] %s\n", Thread.currentThread().getName(), msg));
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		// Fluent style using Async with an Executor [1]
		{
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> "I'm Stunning", executor)
				.thenCombineAsync(CompletableFuture.supplyAsync(() -> "am New !!!"),
					(s1, s2) -> String.format("%s AND %s", s1, s2), executor)
				.thenAcceptAsync(msg -> 
					System.out.printf("[4] [%s] %s\n", Thread.currentThread().getName(), msg), executor);
			
			executor.shutdown();
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		// Fluent style using Async with an Executor [2]
		{
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> "I'm Agile", executor)
				.thenCombineAsync(CompletableFuture.supplyAsync(() -> "am Quick !!!"),
					(s1, s2) -> String.format("%s AND %s", s1, s2), executor)
				.thenAcceptAsync(msg -> 
					System.out.printf("[5] [%s] %s\n", Thread.currentThread().getName(), msg), executor);
			
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
