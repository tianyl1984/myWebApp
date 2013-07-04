package com.hzth.myapp.sql;

import java.util.HashMap;
import java.util.Map;

public class TableInfo {

	private String name;
	private Map<String, ColumnInfo> columnInfoMap = new HashMap<String, ColumnInfo>();
	private Map<String, PKInfo> pkInfoMap = new HashMap<String, PKInfo>();
	private Map<String, FKInfo> fkInfoMap = new HashMap<String, FKInfo>();

	public TableInfo() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, ColumnInfo> getColumnInfoMap() {
		return columnInfoMap;
	}

	public void setColumnInfoMap(Map<String, ColumnInfo> columnInfoMap) {
		this.columnInfoMap = columnInfoMap;
	}

	public void addColumnInfo(ColumnInfo columnInfo) {
		this.columnInfoMap.put(columnInfo.getName().toLowerCase(), columnInfo);
		columnInfo.setTableInfo(this);
	}

	public Map<String, PKInfo> getPkInfoMap() {
		return pkInfoMap;
	}

	public void setPkInfoMap(Map<String, PKInfo> pkInfoMap) {
		this.pkInfoMap = pkInfoMap;
	}

	public void addPKInfo(String pkName, String columnName) {
		PKInfo pkInfo = null;
		if (pkInfoMap.containsKey(pkName)) {
			pkInfo = pkInfoMap.get(pkName);
		} else {
			pkInfo = new PKInfo();
			pkInfo.setName(pkName);
			pkInfoMap.put(pkName, pkInfo);
		}
		pkInfo.addColumnName(columnName);
	}

	public Map<String, FKInfo> getFkInfoMap() {
		return fkInfoMap;
	}

	public void setFkInfoMap(Map<String, FKInfo> fkInfoMap) {
		this.fkInfoMap = fkInfoMap;
	}

	public void addFKInfo(FKInfo fkInfo) {
		this.fkInfoMap.put(fkInfo.getName(), fkInfo);
	}
}
