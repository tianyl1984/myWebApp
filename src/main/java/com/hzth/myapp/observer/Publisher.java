package com.hzth.myapp.observer;

import java.util.Observable;

public class Publisher extends Observable {

	private String name;

	public Publisher(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void publish(String bookName) {
		setChanged();
		notifyObservers(bookName);
	}
}
