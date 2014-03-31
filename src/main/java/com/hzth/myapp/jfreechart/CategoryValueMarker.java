package com.hzth.myapp.jfreechart;

import org.jfree.chart.plot.Marker;

public class CategoryValueMarker extends Marker {

	private static final long serialVersionUID = 4996771130414102224L;

	private String category;

	private Double value;

	public CategoryValueMarker() {

	}

	public CategoryValueMarker(String category, Double value) {
		super();
		this.category = category;
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
