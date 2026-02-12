package com.svetanis.concurrency.fizzbuzz.semaphore;

import java.util.function.IntConsumer;

// 1195. Fizz Buzz Multithreaded
// given 4 functions:
// printFizz, printBuzz, printFizzBuzz, printNumber
// given an instance of the class FizzBuzz 
// that has 4 function: fizz, buzz, fizzbuzz, number
// the same instance of FizzBuzz will be passed 
// to 4 different threads:
// Thread A: calls fizz() that should output "fizz"
// Thread B: calls buzz() that should output "buzz"
// Thread C: calls fizzbuzz() that should output "fizzbuzz"
// Thread D: calls number() that should only output integers

public final class FizzBuzzMultithreaded {

	public static void main(String[] args) {
		int n = 100;
		FizzBuzz fb = new FizzBuzz(n);
		Runnable fizz = () -> System.out.println("fizz");
		Runnable buzz = () -> System.out.println("buzz");
		Runnable fizzbuzz = () -> System.out.println("fizzbuzz");
		IntConsumer num = i -> System.out.println(i);

		Thread ft = new Thread(() -> ft(fb, fizz));
		Thread bt = new Thread(() -> bt(fb, buzz));
		Thread fbt = new Thread(() -> fbt(fb, fizzbuzz));
		Thread nt = new Thread(() -> nt(fb, num));

		ft.start();
		bt.start();
		fbt.start();
		nt.start();
	}

	private static void nt(FizzBuzz fb, IntConsumer num) {
		try {
			fb.number(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void fbt(FizzBuzz fb, Runnable task) {
		try {
			fb.fizzbuzz(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void bt(FizzBuzz fb, Runnable task) {
		try {
			fb.buzz(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void ft(FizzBuzz fb, Runnable task) {
		try {
			fb.fizz(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
