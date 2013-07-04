package com.hzth.myapp.sql;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class InsertSql {

	private String table;

	private List<Object> values = new ArrayList<Object>();

	private List<String> labels = new ArrayList<String>();

	private List<Integer> types = new ArrayList<Integer>();

	public void add(Integer type, Object val, String label) {
		types.add(type);
		values.add(val);
		labels.add(label);
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public List<Integer> getTypes() {
		return types;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public boolean hasForeignKey() {
		for (String label : labels) {
			if (label.startsWith("id_")) {
				return true;
			}
		}
		return false;
	}

	public List<String> getForeignValues() {
		List<String> val = new ArrayList<String>();
		int index = 0;
		for (String label : labels) {
			if (label.startsWith("id_")) {
				if (values.get(index) != null) {
					val.add(values.get(index).toString());
				}
			}
			index++;
		}
		return val;
	}

	public String getSql() {
		String sql = "insert into " + table + "(";
		for (int index = 0; index < values.size(); index++) {
			String label = labels.get(index);
			sql += label;
			if (index != values.size() - 1) {
				sql += ",";
			}
		}
		sql += ") values(";
		for (int index = 0; index < values.size(); index++) {
			Object val = values.get(index);
			Integer type = types.get(index);
			if (Types.INTEGER == type) {
				if (val == null) {
					sql += "null";
				} else {
					sql += val.toString();
				}
			} else if (Types.CHAR == type || Types.VARCHAR == type) {
				if (val == null) {
					sql += "null";
				} else {
					sql += "'" + SqlHelper.getSqlValue(val.toString()) + "'";
				}
			} else {
				System.out.println("不支持类型");
			}
			if (index != values.size() - 1) {
				sql += ",";
			}
		}
		sql += ");";
		return sql;
	}

	public String getId() {
		if (!labels.contains("id")) {
			return "";
		}
		int index = 0;
		for (String label : labels) {
			if (label.equals("id")) {
				return values.get(index).toString();
			}
			index++;
		}
		return "";
	}

	public boolean containsId() {
		return labels.contains("id");
	}
}
