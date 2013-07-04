package com.hzth.myapp.user.util;

import org.apache.struts2.ServletActionContext;

import com.hzth.myapp.user.model.User;

public class UserUtil {

	public static final void saveLoginUser(User user) {
		ServletActionContext.getRequest().getSession().setAttribute("currentUser", user);
	}

	public static final void clearUser() {
		ServletActionContext.getRequest().getSession().removeAttribute("currentUser");
	}

	public static final User getCurrentUser() {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("currentUser");
		if (user == null) {
			throw new RuntimeException("用户未登录");
		}
		return user;
	}

	public static final boolean synUserInfo() {
		return true;
	}
}
