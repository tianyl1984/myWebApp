package com.hzth.myapp.dc.cache;

import javax.servlet.ServletRequestEvent;

public interface IRequestDestroyed {

	public void destroyed(ServletRequestEvent sre);
}
