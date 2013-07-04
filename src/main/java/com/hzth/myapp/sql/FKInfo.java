package com.hzth.myapp.sql;

public class FKInfo {

	private String name;

	private String pkTabName;

	private String pkColumnName;

	private String fkTabName;

	private String fkColumnName;

	private String pkName;

	public FKInfo() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPkTabName() {
		return pkTabName;
	}

	public void setPkTabName(String pkTabName) {
		this.pkTabName = pkTabName;
	}

	public String getPkColumnName() {
		return pkColumnName;
	}

	public void setPkColumnName(String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}

	public String getFkTabName() {
		return fkTabName;
	}

	public void setFkTabName(String fkTabName) {
		this.fkTabName = fkTabName;
	}

	public String getFkColumnName() {
		return fkColumnName;
	}

	public void setFkColumnName(String fkColumnName) {
		this.fkColumnName = fkColumnName;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

}
