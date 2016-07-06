package com.hzth.myapp.sql;

import java.util.List;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;

public class JsqlparserDemo {

	public static void main(String[] args) throws Exception {
		String sql = "select b.name as name1,f.name as fname,(select name from foo where id = f.id) as name3 from bar b left join foo f on f.id = b.id ";
		sql = "select b.name2 as name2 from (select '***' as name2 from bar c) as b";
		sql = "select *,'**',bar.* from bar b group by b.name having(b.name) > 0";
		Statement st = CCJSqlParserUtil.parse(sql);
		Select sel = (Select) st;
		SelectBody body = sel.getSelectBody();
		PlainSelect ps = (PlainSelect) body;
		System.out.println(body);
		List<SelectItem> siList = ps.getSelectItems();
		for (SelectItem si : siList) {
			System.out.println(si);
		}
	}

}
