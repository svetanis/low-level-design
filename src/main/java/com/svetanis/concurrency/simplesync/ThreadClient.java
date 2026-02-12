package com.svetanis.concurrency.simplesync;

public final class ThreadClient {

	// Different references - both threads can call Foo.foo()
	// Foo 1.foo() starting
	// Foo 2.foo() starting
	// Foo 1.foo() ending
	// Foo 2.foo() ending
	public static void differentReferences() {
		Foo foo1 = new Foo();
		Foo foo2 = new Foo();
		ThreadExample thread1 = new ThreadExample("1", foo1);
		ThreadExample thread2 = new ThreadExample("2", foo2);
		thread1.start();
		thread2.start();
	}

	// Same reference to Foo. Only one will be allowed to call foo,
	// and the other will be forced to wait
	// Foo 1.foo() starting
	// Foo 1.foo() ending
	// Foo 2.foo() starting
	// Foo 2.foo() ending
	public static void sameReference() {
		Foo foo = new Foo();
		ThreadExample thread1 = new ThreadExample("1", foo);
		ThreadExample thread2 = new ThreadExample("2", foo);
		thread1.start();
		thread2.start();
	}

	// static methods synchronize on the class lock.
	// two threads can't simultaneously execute synchronized
	// static methods on the same class, even if one is calling
	// foo and the other is calling bar
	// Bar 1.foo() starting
	// Bar 1.foo() ending
	// Bar 2.bar() starting
	// Bar 2.bar() ending
	public static void staticReference() {
		ThreadExampleStatic thread1 = new ThreadExampleStatic("1");
		ThreadExampleStatic thread2 = new ThreadExampleStatic("2");
		thread1.start();
		thread2.start();
	}

	public static void main(String[] args) {
		staticReference();
		sameReference();
		differentReferences();
	}
}