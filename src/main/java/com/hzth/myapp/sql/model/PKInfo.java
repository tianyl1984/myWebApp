package com.hzth.myapp.sql.model;

import java.util.HashSet;
import java.util.Set;

public class PKInfo {

	private String name;

	private Set<String> columnNames = new HashSet<String>();

	private TableInfo tableInfo;

	public PKInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

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

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	public String getAddSql() {
		String sql = "alter table " + tableInfo.getName() + " add constraint " + this.getName() + " primary key nonclustered (";
		int index = 0;
		for (String colu : columnNames) {
			sql += colu + (columnNames.size() - 1 == index ? "" : ",");
			index++;
		}
		sql += ")";
		return sql;
	}
}
