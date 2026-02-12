package com.svetanis.concurrency.simplesync;

public final class Bar {

	public static synchronized void foo(String name) {
		try {
			System.out.println("Bar " + name + ".foo() starting");
			Thread.sleep(3000);
			System.out.println("Bar " + name + ".foo() ending");
		} catch (InterruptedException e) {
			System.out.println("Bar " + name + ": interrupted.");
		}
	}

	public static synchronized void bar(String name) {
		try {
			System.out.println("Bar " + name + ".bar() starting");
			Thread.sleep(3000);
			System.out.println("Bar " + name + ".bar() ending");
		} catch (InterruptedException e) {
			System.out.println("Bar " + name + ": interrupted.");
		}
	}

}