package com.hzth.myapp.dc;

import org.springframework.core.NestedRuntimeException;

public class BusinessException extends NestedRuntimeException {

	private static final long serialVersionUID = -4608484737354602352L;

	public BusinessException(String msg) {
		super(msg);
	}
}
