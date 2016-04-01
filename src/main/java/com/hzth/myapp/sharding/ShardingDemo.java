package com.hzth.myapp.sharding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSource;
import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;

public class ShardingDemo {

	public static void main(String[] args) throws Exception {
		Map<String, DataSource> dataSourceMap = new HashMap<>();
		dataSourceMap.put("db0", createDataSource("db0"));
		dataSourceMap.put("db1", createDataSource("db1"));
		DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);
		TableRule orderTableRule = new TableRule("t_order", Arrays.asList("t_order_0", "t_order_1"), dataSourceRule);
		TableRule itemTableRule = new TableRule("t_order_item", Arrays.asList("t_order_item_0", "t_order_item_1"), dataSourceRule);
		ShardingRule shardingRule = new ShardingRule(dataSourceRule,
				Arrays.asList(orderTableRule, itemTableRule),
				Collections.<BindingTableRule> emptyList(),
				new DatabaseShardingStrategy("user_id", new CustomSingleKeyDatabaseShardingAlgorithm()),
				new TableShardingStrategy("order_id", new CustomSingleKeyTableShardingAlgorithm())
				);
		DataSource dataSource = new ShardingDataSource(shardingRule);
		Connection conn = dataSource.getConnection();
		// insert(conn);
		// delete(conn);
		select(conn);
		conn.close();
	}

	private static void select(Connection conn) throws Exception {
		String sql = "select * from t_order";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		printRs(rs);
	}

	private static void printRs(ResultSet rs) throws Exception {
		System.out.println();
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			System.out.print(rsmd.getColumnLabel(i + 1) + "\t");
			if (i == rsmd.getColumnCount() - 1) {
				System.out.println();
			}
		}
		while (rs.next()) {
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				System.out.print(rs.getString(i + 1) + "\t");
				if (i == rsmd.getColumnCount() - 1) {
					System.out.println();
				}
			}
		}
		rs.close();
	}

	private static void delete(Connection conn) throws Exception {
		String sql = "delete from t_order";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.execute();
	}

	private static void insert(Connection conn) throws Exception {
		String sql = "insert into t_order(order_id,user_id) values(?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				ps.setInt(1, i);
				ps.setInt(2, j);
				ps.addBatch();
			}
		}
		ps.executeBatch();
		ps.close();
	}

	private static DataSource createDataSource(String name) {
		DruidDataSource dds = new DruidDataSource();
		dds.setDriverClassName("com.mysql.jdbc.Driver");
		dds.setUrl("jdbc:mysql://127.0.0.1:3306/" + name + "?useUnicode=true&characterEncoding=utf-8");
		dds.setUsername("root");
		dds.setPassword("hzth-801");
		dds.setName(name);
		List<Filter> filters = new ArrayList<>();
		ConsoleLogFilter filter = new ConsoleLogFilter();
		filter.setStatementLogEnabled(true);
		filter.setStatementExecuteQueryAfterLogEnabled(true);

		// filter.setStatementParameterSetLogEnabled(false);
		// filter.setStatementPrepareAfterLogEnabled(false);
		// filter.setStatementCloseAfterLogEnabled(false);
		// filters.add(new CommonsLogFilter());
		filters.add(filter);
		dds.setProxyFilters(filters);
		return dds;
	}

}
