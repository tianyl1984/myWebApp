package com.hzth.myapp.spring.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FooModel {

	@Value("${hibernate.connection.username}")
	private String userName;

	@Value("${aaa:}")
	private String aaa;

	@Value("${bbb:bbb}")
	private String bbb;

	@Value("${aaa.bbb.${aaa.bbb.ccc}}")
	private String eee;

	@Override
	public String toString() {
		return "FooModel [userName=" + userName + ", aaa=" + aaa + ", bbb=" + bbb + ", eee=" + eee + "]";
	}

}
