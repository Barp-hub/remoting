package com.machine;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.UUID;

import org.junit.Test;

public class ThreadException {

	@Test
	public void exception() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				throw new RuntimeException("runtime error!");

			}
		});
		thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(t.getName());
				System.out.println(e.getMessage());
			}
		});
		thread.start();

		Thread boy = new Thread(new Runnable() {

			@Override
			public void run() {
				throw new RuntimeException("bad boy!");
			}
		});
		boy.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(t.getName());
				System.out.println(e.getMessage());
			}
		});
		boy.start();

	}

	@Test
	public void random() {
		System.out.println(UUID.randomUUID().toString());
	}
}
