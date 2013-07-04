package com.hzth.myapp.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;

public class MyConnectionProvider implements MultiTenantConnectionProvider {

	private static final long serialVersionUID = -8381883862784228817L;

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		return null;
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {

	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		return null;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {

	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

}
