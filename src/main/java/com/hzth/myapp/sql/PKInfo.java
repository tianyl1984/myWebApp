package com.hzth.myapp.sql;

import java.util.HashSet;
import java.util.Set;

public class PKInfo {

	private String name;

	private Set<String> columnNames = new HashSet<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Set<String> columnNames) {
		this.columnNames = columnNames;
	}

	public void addColumnName(String columnName) {
		this.columnNames.add(columnName);
	}
}
