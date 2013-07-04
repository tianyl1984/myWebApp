package com.hzth.myapp.sql;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CommonSql {

	private String sql;

	private String table;

	private List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();

	public CommonSql(String sql) {
		this.sql = sql.trim();
		parseTableName();
		parseColumn();
	}

	private void parseColumn() {
		if (!isCreate()) {
			return;
		}
		String temp = sql.substring(sql.indexOf("(") + 1, sql.lastIndexOf(")"));
		temp = temp.toLowerCase();
		for (String str : temp.split(",")) {
			// System.out.println(str);
			str = str.replaceAll("\\s+", " ");
			str = str.trim();
			// System.out.println(str);
			ColumnInfo colu = new ColumnInfo();
			colu.setName(str.split(" ")[0]);
			String typeStr = str.split(" ")[1];
			if (typeStr.equals("int") || typeStr.equals("text") || typeStr.equals("clob") || typeStr.equals("integer") || typeStr.equals("bigint")) {
				colu.setType(typeStr);
			} else {
				if (typeStr.startsWith("char")) {
					Integer length = Integer.valueOf(typeStr.replaceAll("char", "").replaceAll("\\(", "").replaceAll("\\)", ""));
					colu.setType("char");
					colu.setLength(length);
				}
				if (typeStr.startsWith("varchar") && !typeStr.startsWith("varchar2")) {
					Integer length = Integer.valueOf(typeStr.replaceAll("varchar", "").replaceAll("\\(", "").replaceAll("\\)", ""));
					colu.setType("varchar");
					colu.setLength(length);
				}
				if (typeStr.startsWith("varchar2")) {
					Integer length = Integer.valueOf(typeStr.replaceAll("varchar2", "").replaceAll("\\(", "").replaceAll("\\)", ""));
					colu.setType("VARCHAR2");
					colu.setLength(length);
				}
			}
			if (StringUtils.isBlank(colu.getType())) {
				throw new RuntimeException("解析类型出错：" + str + ":" + sql);
			}
			this.columnInfos.add(colu);
		}
	}

	private void parseTableName() {
		String sql2 = sql.trim().toLowerCase();
		if (sql2.toLowerCase().startsWith("alter table") || sql2.toLowerCase().startsWith("create table")) {
			table = sql2.split(" ")[2];
		}
		if (sql2.startsWith("insert")) {
			String temp = sql2.substring(0, sql2.indexOf("("));
			temp = temp.replaceFirst("insert ", "").replaceAll("into", "").replaceAll("\\[dbo\\].", "").replaceAll("\\[", "").replaceAll("\\]", "");
			table = temp.trim();
		}
		if (sql2.startsWith("create index")) {
			String temp = sql2.substring(0, sql2.indexOf("("));
			temp = temp.substring(temp.lastIndexOf(" "), temp.length());
			table = temp.trim();
		}
		if (StringUtils.isBlank(table)) {
			throw new RuntimeException("无法解析table:" + sql);
		}
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public boolean isAlter() {
		return sql.trim().toLowerCase().startsWith("alter table");
	}

	public boolean isCreate() {
		return sql.trim().toLowerCase().startsWith("create table");
	}

	public boolean isInsert() {
		return sql.trim().toLowerCase().startsWith("insert");
	}

	public boolean isIndex() {
		return sql.trim().toLowerCase().startsWith("create index");
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<ColumnInfo> getColumnInfos() {
		return columnInfos;
	}

	public void setColumnInfos(List<ColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}

	public static void main(String[] args) {
		String sql = "insert into bd_module(id,name,nameI18n,code,num,deployFlag,ft,lt,fu,lu) values('20121015085922826707592361934822','工资管理','bd_module_fi','fi',null,'1','2012-10-15 08:59:22',null,'20120507134700000110225236178825',null);";
		sql = "INSERT [dbo].[bd_dictionary] ([id], [name], [code], [description], [kind], [enableFlag], [editableFlag], [systemFlag], [ft], [lt], [fu], [lu]) VALUES (N'20121015164821139161273534930864', N'特殊学生类型', N'specialStudentKind', NULL, N'0', NULL, N'1', NULL, N'2012-10-15 16:48:21', NULL, N'20120507134700000110225236178825', NULL)";
		sql = "CREATE INDEX USEROPERATIONDATASCOPE_ID_PAREHT ON bd_useroperationdatascope(id_parent);";
		sql = "create table fw_attachment (id                   char(32)             not null,id_owner             char(32)             null,path                 varchar(200)         null,serverFilename       varchar(200)         null,sourceFilename       varchar(200)         null,fileNum              int                  null,fileSize             varchar(50)          null,fileKind             varchar(20)          null,status               char(1)              null,ft                   char(19)             null,lt                   char(19)             null,fu                   char(32)             null,lu                   char(32)             null)";
		System.out.println(new CommonSql(sql).getTable());
		System.out.println("FK_SO_SOCIE_REFERENCE_SO_activity".length());
	}
}
