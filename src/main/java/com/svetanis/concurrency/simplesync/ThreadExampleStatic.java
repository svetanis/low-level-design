package com.svetanis.concurrency.simplesync;

public final class ThreadExampleStatic extends Thread {

	private String name;

	public ThreadExampleStatic(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		if (name.equals("1")) {
			Bar.foo(name);
		} else if (name.equals("2")) {
			Bar.bar(name);
		}
	}

}