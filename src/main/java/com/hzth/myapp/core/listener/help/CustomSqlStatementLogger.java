package com.hzth.myapp.core.listener.help;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;

public class CustomSqlStatementLogger extends SqlStatementLogger {

	public void logStatement(String statement) {
		setFormat(false);
		setLogToStdout(true);
		if (StringUtils.isNotBlank(statement)) {
			statement = statement.replaceAll("\n", "");
		}
		logStatement(statement, FormatStyle.BASIC.getFormatter());
	}

}
