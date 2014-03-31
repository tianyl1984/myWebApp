package com.hzth.myapp.jfreechart;

public class ControlPoint {
	/** The x-coordinate. */
	public float x;

	/** The y-coordinate. */
	public float y;

	/**
	 * Creates a new control point.
	 * 
	 * @param x
	 *            the x-coordinate.
	 * @param y
	 *            the y-coordinate.
	 */
	public ControlPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public ControlPoint(double x, double y) {
		this.x = Double.valueOf(x).floatValue();
		this.y = Double.valueOf(y).floatValue();
	}

	/**
	 * Tests this point for equality with an arbitrary object.
	 * 
	 * @param obj
	 *            the object (<code>null</code> permitted.
	 * 
	 * @return A boolean.
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ControlPoint)) {
			return false;
		}
		ControlPoint that = (ControlPoint) obj;
		if (this.x != that.x || this.y != that.y) {
			return false;
		}
		return true;
	}
}
