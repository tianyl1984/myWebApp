package com.hzth.myapp.sql.model;

import java.util.Map;

import com.hzth.myapp.core.util.UUID;

public class FKInfo {

	private String name;

	private String pkTabName;

	private String pkColumnName;

	private String fkTabName;

	private String fkColumnName;

	private String pkName;

	private TableInfo tableInfo;

	public FKInfo() {

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

	public String getAddSql(Map<String, TableInfo> fromTableInfos) {
		boolean hasSameName = false;
		for (TableInfo tab : fromTableInfos.values()) {
			Map<String, FKInfo> fkFromMap = tab.getFkInfoMap();
			for (FKInfo fk : fkFromMap.values()) {
				if (tab.getName().equals(tableInfo.getName())) {// 同一张表
					if (!hasSameFK(fkFromMap)) {// 没有相同外键
						hasSameName = true;
					}
				} else {
					if (fk.getName().equals(name)) {
						hasSameName = true;
					}
				}
			}
		}
		String pkName = hasSameName ? ("FK_" + UUID.getUUID().substring(6)) : name;
		String result = "alter table " + fkTabName + " add constraint " + pkName + " foreign key (" + fkColumnName + ") references " + pkTabName + " (" + pkColumnName + ")";
		return result;
	}

	public boolean hasSameFK(Map<String, FKInfo> fkToMap) {
		for (FKInfo fk : fkToMap.values()) {
			if (fk.equalsIgnoreName(this)) {
				return true;
			}
		}
		return false;
	}

	private boolean equalsIgnoreName(FKInfo fkInfo) {
		return fkTabName.equalsIgnoreCase(fkInfo.getFkTabName()) && fkColumnName.equalsIgnoreCase(fkInfo.getFkColumnName()) && pkTabName.equalsIgnoreCase(fkInfo.getPkTabName()) && pkColumnName.equalsIgnoreCase(fkInfo.getPkColumnName());
	}

}
