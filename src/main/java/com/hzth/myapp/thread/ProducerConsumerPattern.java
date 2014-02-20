package com.hzth.myapp.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerPattern {

	public static void main(String args[]) {
		// Creating shared object
		BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<Integer>();

		// Creating Producer and Consumer Thread
		Thread prodThread = new Thread(new Producer(sharedQueue));
		Thread consThread = new Thread(new Consumer(sharedQueue));

		// Starting producer and Consumer thread
		prodThread.start();
		consThread.start();
	}
}

// Producer Class in java
class Producer implements Runnable {

	private final BlockingQueue<Integer> sharedQueue;

	public Producer(BlockingQueue<Integer> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println("Produced: " + i);
				sharedQueue.put(i);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

}

// Consumer Class in Java
class Consumer implements Runnable {

	private final BlockingQueue<Integer> sharedQueue;

	public Consumer(BlockingQueue<Integer> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Consumed: " + sharedQueue.take());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

}