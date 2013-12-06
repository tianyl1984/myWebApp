package com.hzth.myapp.user.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.core.util.JsonUtil;
import com.hzth.myapp.core.util.WebUtil;
import com.hzth.myapp.core.web.MyBaseAction;
import com.hzth.myapp.user.model.User;
import com.hzth.myapp.user.service.UserService;
import com.hzth.myapp.user.util.UserUtil;

@ParentPackage(value = "crud-default")
@Namespace(value = "/um")
@Action(value = "user_*")
@Results({
		@Result(name = "success", location = "/um/user/jsp/user.jsp"),
		@Result(name = "input", location = "/um/user/jsp/user-input.jsp"),
		@Result(name = "login", location = "/um/user/jsp/user-login.jsp"),
		@Result(name = "index", location = "/"),
		@Result(name = "reload", type = "redirect", location = "user!list.action") })
public class UserAction extends MyBaseAction {
	private static final long serialVersionUID = -1717527484115479541L;

	public UserAction() {
		user = new User();
	}

	public String login() {
		List<User> users = userService.findAll();
		this.getRequest().setAttribute("users", users);
		return "login";
	}

	public String logout() {
		UserUtil.clearUser();
		return "index";
	}

	public String saveLoginUser() {
		String userId = this.getRequest().getParameter("userId");
		UserUtil.saveLoginUser(userService.get(userId));
		return "index";
	}

	public String list() {
		List<User> users = userService.findAll();
		ServletActionContext.getRequest().setAttribute("users", users);
		return "success";
	}

	public void validateList() {
		// System.out.println("validateList");
	}

	public String input() {
		if (StringUtils.isNotBlank(id)) {
			user = userService.get(id);
		}
		return "input";
	}

	public String save() {
		userService.saveOrUpdate(user);
		return "reload";
	}

	public String delete() {
		userService.delete(id);
		return "reload";
	}

	public void ajaxGetData() throws Exception {
		PrintWriter pw = ServletActionContext.getResponse().getWriter();
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 32; i++) {
			User user = new User("<td>" + i + "</td>", "张三" + i, "zhangsan@126.com<script>alert(1)</script>");
			users.add(user);
		}
		WebUtil.printParameter(ServletActionContext.getRequest());
		String json = JsonUtil.collection2Json(users, null);
		pw.print("{\"gridModel\":" + json + ",\"page\":" + 1 + ",\"record\":" + users.size() + ",\"rows\":" + 10 + ",\"total\":" + 4 + "}");
		pw.flush();
		pw.close();
	}

	private String id;

	private User user;

	@Autowired
	private UserService userService;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
