package com.hzth.myapp.jfreechart;

import java.awt.Color;

public class MyColor extends Color {

	public MyColor(int r, int g, int b) {
		super(r, g, b);
	}

	private static final long serialVersionUID = 920480497422209773L;

	@Override
	public Color brighter() {
		// Color color = new Color(0.1f, 0.1f, 0.1f, 0f);
		return this;
	}
}
