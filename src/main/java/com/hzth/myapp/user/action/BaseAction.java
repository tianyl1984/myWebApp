package com.hzth.myapp.user.action;

import com.hzth.myapp.user.model.User;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = -9029208319069160374L;

	protected User user2;

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}
