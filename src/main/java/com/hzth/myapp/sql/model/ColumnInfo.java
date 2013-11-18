package com.hzth.myapp.sql.model;

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

	public String getTypeString() {
		String result = "";
		if (this.getType().equals("char")) {
			result += " char(" + this.getLength() + ")";
		} else if (this.getType().equals("varchar")) {
			result += " varchar(" + this.getLength() + ")";
		} else if (this.getType().equals("text")) {
			result += " text ";
		} else if (this.getType().equals("int")) {
			result += " int ";
		} else if (this.getType().equals("bigint")) {
			result += " bigint ";
		} else {
			new RuntimeException("不支持的字段类型!!");
		}
		return result;
	}

	public String getAddSql() {
		return "alter table " + tableInfo.getName() + " add " + this.getName() + getTypeString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ColumnInfo) {
			ColumnInfo info = (ColumnInfo) obj;
			return getTypeString().equals(info.getTypeString());
		}
		return false;
	}

	public String getAlterSql() {
		return "alter table " + tableInfo.getName() + " alter column " + this.getName() + getTypeString();
	}
}
