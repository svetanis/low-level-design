package com.svetanis.concurrency.simplesync;

public final class ThreadExample extends Thread {

	private String name;
	private Foo foo;

	public ThreadExample(String name, Foo foo) {
		this.name = name;
		this.foo = foo;
	}

	@Override
	public void run() {
		foo.foo(name);
	}

}