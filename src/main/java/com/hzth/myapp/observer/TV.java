package com.hzth.myapp.observer;

import java.util.Observable;

public class TV extends Observable {

	private String name;

	public TV(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void play(String tvName) {
		setChanged();
		notifyObservers(tvName);
	}
}
