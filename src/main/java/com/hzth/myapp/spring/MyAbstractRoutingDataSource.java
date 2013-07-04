package com.hzth.myapp.spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return MyContextHolder.getUserType();
	}

}
