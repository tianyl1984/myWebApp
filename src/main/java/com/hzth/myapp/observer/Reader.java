package com.hzth.myapp.observer;

import java.util.Observable;
import java.util.Observer;

public class Reader implements Observer {

	private String name;

	public Reader(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Publisher) {
			Publisher pub = (Publisher) o;
			System.out.println(this.getName() + " read book " + arg.toString() + " from " + pub.getName());
		}
		if (o instanceof TV) {
			TV tv = (TV) o;
			System.out.println(this.getName() + " watch " + tv.getName() + "," + arg.toString());
		}
	}

}
