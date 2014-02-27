package com.hzth.myapp.dc.cache;

import org.springframework.stereotype.Component;

@Component("fw_CacheExpiredKeyCommonExecutor")
public class CacheExpiredKeyCommonExecutor implements CacheExpiredKeyExecutor {

	@Override
	public Object execute(Object obj) {
		return obj;
	}

}
