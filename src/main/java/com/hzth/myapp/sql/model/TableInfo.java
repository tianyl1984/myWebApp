package com.hzth.myapp.sql.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableInfo {

	private String name;
	private Map<String, ColumnInfo> columnInfoMap = new HashMap<String, ColumnInfo>();
	private List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
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

	public List<ColumnInfo> getColumnInfoList() {
		return columnInfoList;
	}

	public void addColumnInfo(ColumnInfo columnInfo) {
		this.columnInfoMap.put(columnInfo.getName().toLowerCase(), columnInfo);
		columnInfo.setTableInfo(this);
		columnInfoList.add(columnInfo);
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
			pkInfo = new PKInfo(this);
			pkInfo.setName(pkName);
			pkInfoMap.put(pkName, pkInfo);
		}
		pkInfo.addColumnName(columnName);
	}

	public boolean hasPK() {
		return !pkInfoMap.isEmpty();
	}

	public List<String> getPKSql() {
		if (!hasPK()) {
			return new ArrayList<String>();
		}
		List<String> result = new ArrayList<String>();
		for (PKInfo pkInfo : pkInfoMap.values()) {
			result.add(pkInfo.getAddSql());
		}
		return result;
	}

	public Map<String, FKInfo> getFkInfoMap() {
		return fkInfoMap;
	}

	public void setFkInfoMap(Map<String, FKInfo> fkInfoMap) {
		this.fkInfoMap = fkInfoMap;
	}

	public void addFKInfo(FKInfo fkInfo) {
		this.fkInfoMap.put(fkInfo.getName().toLowerCase(), fkInfo);
		fkInfo.setTableInfo(this);
	}

	public String getCreateSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + this.getName() + " (");
		for (ColumnInfo info : columnInfoList) {
			sb.append(info.getName() + " ");
			sb.append(info.getTypeString() + " ");
			sb.append(("id".equals(info.getName()) ? "not " : "") + "null,");
		}
		if (columnInfoMap.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(")");
		return sb.toString();
	}
}
