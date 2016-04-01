package com.hzth.myapp.sharding;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.proxy.jdbc.CallableStatementProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;

public class ConsoleLogFilter extends LogFilter {

	public ConsoleLogFilter() {
		setConnectionConnectBeforeLogEnabled(false);
		setConnectionConnectAfterLogEnabled(false);
		setConnectionCommitAfterLogEnabled(false);
		setConnectionRollbackAfterLogEnabled(false);
		setConnectionCloseAfterLogEnabled(false);

		setStatementCreateAfterLogEnabled(false);
		setStatementPrepareAfterLogEnabled(false);
		setStatementPrepareCallAfterLogEnabled(false);

		setStatementExecuteAfterLogEnabled(false);
		setStatementExecuteQueryAfterLogEnabled(false);
		setStatementExecuteUpdateAfterLogEnabled(false);
		setStatementExecuteBatchAfterLogEnabled(false);
		setStatementExecutableSqlLogEnable(false);

		setStatementCloseAfterLogEnabled(false);

		setStatementParameterClearLogEnable(false);
		setStatementParameterSetLogEnabled(false);

		setResultSetNextAfterLogEnabled(false);
		setResultSetOpenAfterLogEnabled(false);
		setResultSetCloseAfterLogEnabled(false);

		setDataSourceLogEnabled(false);
		setConnectionLogEnabled(false);
		setConnectionLogErrorEnabled(false);
		setStatementLogEnabled(false);
		setStatementLogErrorEnabled(false);
		setResultSetLogEnabled(false);
		setResultSetLogErrorEnabled(false);
	}

	@Override
	public String getDataSourceLoggerName() {
		return dataSourceLoggerName;
	}

	@Override
	public void setDataSourceLoggerName(String loggerName) {
		super.dataSourceLoggerName = loggerName;
	}

	@Override
	public String getConnectionLoggerName() {
		return connectionLoggerName;
	}

	@Override
	public void setConnectionLoggerName(String loggerName) {
		super.connectionLoggerName = loggerName;
	}

	@Override
	public String getStatementLoggerName() {
		return statementLoggerName;
	}

	@Override
	public void setStatementLoggerName(String loggerName) {
		super.statementLoggerName = loggerName;
	}

	@Override
	public String getResultSetLoggerName() {
		return resultSetLoggerName;
	}

	@Override
	public void setResultSetLoggerName(String loggerName) {
		super.resultSetLoggerName = loggerName;
	}

	@Override
	protected void connectionLog(String message) {
		System.out.println(message);
	}

	@Override
	protected void statementLog(String message) {
		System.out.println(message);
	}

	@Override
	protected void statementLogError(String message, Throwable error) {
		System.out.println(message);
		error.printStackTrace();
	}

	@Override
	protected void resultSetLog(String message) {
		System.out.println(message);
	}

	@Override
	protected void resultSetLogError(String message, Throwable error) {
		System.out.println(message);
		error.printStackTrace();
	}

	@Override
	protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
		logExecutableSql(statement, sql);

		if (isStatementExecuteQueryAfterLogEnabled() && isStatementLogEnabled()) {
			statement.setLastExecuteTimeNano();
			double nanos = statement.getLastExecuteTimeNano();
			double millis = nanos / (1000 * 1000);

			statementLog("{ds:" + statement.getConnectionProxy().getDirectDataSource().getName() + ", conn:" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + ", rs:"
					+ resultSet.getId() + "} query executed. " + millis + " millis. \n" + sql);
		}
	}

	protected void statementPrepareAfter(PreparedStatementProxy statement) {
		if (isStatementPrepareAfterLogEnabled() && isStatementLogEnabled()) {
			statementLog("{ds:" + statement.getConnectionProxy().getDirectDataSource().getName() + ", conn:" + statement.getConnectionProxy().getId() + ", pstmt:" + statement.getId()
					+ "} created. \n" + statement.getSql());
		}
	}

	private void logExecutableSql(StatementProxy statement, String sql) {
		if (!isStatementExecutableSqlLogEnable()) {
			return;
		}

		int parametersSize = statement.getParametersSize();
		if (parametersSize == 0) {
			statementLog("{conn:" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} executed. \n"
					+ sql);
			return;
		}

		List<Object> parameters = new ArrayList<Object>(parametersSize);
		for (int i = 0; i < parametersSize; ++i) {
			JdbcParameter jdbcParam = statement.getParameter(i);
			parameters.add(jdbcParam.getValue());
		}

		String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
		String formattedSql = SQLUtils.format(sql, dbType, parameters);
		statementLog("{conn:" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} executed. \n"
				+ formattedSql);
	}

	private String stmtId(StatementProxy statement) {
		StringBuffer buf = new StringBuffer();
		if (statement instanceof CallableStatementProxy) {
			buf.append("cstmt:");
		} else if (statement instanceof PreparedStatementProxy) {
			buf.append("pstmt:");
		} else {
			buf.append("stmt:");
		}
		buf.append(statement.getId());

		return buf.toString();
	}
}
