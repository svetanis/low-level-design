package com.svetanis.concurrency.simplesync;

public final class Foo {

	public synchronized void foo(String name) {
		try {
			System.out.println("Foo " + name + ".foo() starting");
			Thread.sleep(3000);
			System.out.println("Foo " + name + ".foo() ending");
		} catch (InterruptedException e) {
			System.out.println("Foo " + name + ": interrupted.");
		}
	}
}