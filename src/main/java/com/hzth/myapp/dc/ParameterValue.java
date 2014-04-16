package com.hzth.myapp.dc;

import java.util.ArrayList;
import java.util.List;

public class ParameterValue {

	private List<String> values = new ArrayList<String>();

	public ParameterValue() {

	}

	public ParameterValue(String val) {
		addValue(val);
	}

	public void addValue(String value) {
		values.add(value);
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public String getFirstValue() {
		return values.size() > 0 ? values.get(0) : "";
	}
}
