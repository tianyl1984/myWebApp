package com.hzth.myapp.sql;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;

/**
 * 解析sql
 * 
 * @author tianyl
 * 
 */
public class SQLParse {

	public static void main(String[] args) {
		String sql = "select u.aa as a,t.bb as b from bd_user u left join bd_teacher t on t.id_user = u.id where u.id = ? ";
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer();
		StringBuffer where = new StringBuffer();
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.MYSQL);
		List<SQLStatement> stmtList = parser.parseStatementList();
		SQLASTOutputVisitor fromVisitor = SQLUtils.createFormatOutputVisitor(from, stmtList, JdbcUtils.MYSQL);
		SQLASTOutputVisitor whereVisitor = SQLUtils.createFormatOutputVisitor(where, stmtList, JdbcUtils.MYSQL);
		for (SQLStatement stmt : stmtList) {
			if (stmt instanceof SQLSelectStatement) {
				SQLSelectStatement st = (SQLSelectStatement) stmt;
				SQLSelect sel = st.getSelect();
				SQLSelectQueryBlock query = (SQLSelectQueryBlock) sel.getQuery();
				query.getFrom().accept(fromVisitor);
				query.getWhere().accept(whereVisitor);
				List<SQLSelectItem> items = query.getSelectList();
				System.out.println("--------SQLSelectItem--------");
				for (SQLSelectItem item : items) {
					System.out.println(item.getAlias());
				}
				System.out.println("-----------from-----------");
				System.out.println(from);
				System.out.println("-----------where-----------");
				System.out.println(where);
			}
		}
	}
}
