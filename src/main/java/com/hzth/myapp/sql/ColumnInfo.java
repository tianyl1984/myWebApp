package com.hzth.myapp.sql;

public class ColumnInfo {

	private TableInfo tableInfo;

	private String name;

	private Integer length;

	private String type;

	public ColumnInfo() {

	}

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
