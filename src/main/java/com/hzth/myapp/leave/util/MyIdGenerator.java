package com.hzth.myapp.leave.util;

import org.activiti.engine.impl.cfg.IdGenerator;

import com.hzth.myapp.core.util.UUID;

public class MyIdGenerator implements IdGenerator {

	@Override
	public String getNextId() {
		return UUID.getUUID();
	}

}
