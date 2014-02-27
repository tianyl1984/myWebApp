package com.hzth.myapp.dc.cache;

import javax.servlet.ServletRequestEvent;

import org.springframework.stereotype.Component;

/**
 * 控制缓存的初始化和销毁
 * 
 * @author tianyl
 * 
 */
@Component("fw_Cache4RequestController")
public class Cache4RequestController implements IRequestInit, IRequestDestroyed {

	@Override
	public void init(ServletRequestEvent sre) {
		Cache4RequestUtil.init();
	}

	@Override
	public void destroyed(ServletRequestEvent sre) {
		Cache4RequestUtil.clear();
	}

}
