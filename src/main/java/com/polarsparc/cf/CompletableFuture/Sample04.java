package com.polarsparc.cf.CompletableFuture;

import java.util.concurrent.CompletableFuture;

public class Sample04 {
	public static void main(String[] args) {
		{
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I'm Cool");
			CompletableFuture<String> cf2 = cf1.thenApply(msg -> 
				String.format("%s and am Super AWESOME !!!", msg));
			CompletableFuture<Void> cf3 = cf2.thenAccept(msg -> 
				System.out.printf("[1] %s\n", msg));
			
			try {
				cf3.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		
		// Fluent style
		{
			CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> "I'm Awesome")
				.thenApply(msg -> String.format("%s and am Super COOL !!!", msg))
				.thenAccept(msg -> 	System.out.printf("[2] %s\n", msg));
			
			try {
				cf.get();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	}
}
