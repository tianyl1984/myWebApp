package com.hzth.myapp.core.listener;

import java.lang.reflect.Field;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.internal.SessionFactoryImpl;

import com.hzth.myapp.core.listener.help.CustomSqlStatementLogger;
import com.hzth.myapp.core.util.SpringContextHolder;

public class ServerContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		SessionFactoryImpl factory = SpringContextHolder.getBeanOneOfType(SessionFactoryImpl.class);
		JdbcServices jdbcService = factory.getJdbcServices();
		try {
			Field field = jdbcService.getClass().getDeclaredField("sqlStatementLogger");
			field.setAccessible(true);
			field.set(jdbcService, new CustomSqlStatementLogger());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
