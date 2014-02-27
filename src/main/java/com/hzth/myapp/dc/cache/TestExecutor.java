package com.hzth.myapp.dc.cache;

import org.springframework.stereotype.Component;

@Component
public class TestExecutor implements CacheExpiredKeyExecutor {

	@Override
	public Object execute(Object obj) {
		return obj.toString() + ":123";
	}

}
