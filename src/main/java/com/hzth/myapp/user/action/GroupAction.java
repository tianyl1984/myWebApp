package com.hzth.myapp.user.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.core.web.MyBaseAction;
import com.hzth.myapp.user.model.Group;
import com.hzth.myapp.user.model.User;
import com.hzth.myapp.user.service.GroupService;
import com.hzth.myapp.user.service.UserService;

@ParentPackage(value = "crud-default")
@Namespace(value = "/um")
@Action(value = "group_*")
@Results({
		@Result(name = "success", location = "/um/group/jsp/group.jsp"),
		@Result(name = "input", location = "/um/group/jsp/group-input.jsp"),
		@Result(name = "reload", type = "redirect", location = "group!list.action") })
public class GroupAction extends MyBaseAction {

	public String list() {
		List<Group> groups = groupService.getAll();
		this.getRequest().setAttribute("groups", groups);
		return SUCCESS;
	}

	public String input() {
		String id = this.getRequest().getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			group = groupService.get(id);
		}
		List<User> users = userService.findAll();
		this.getRequest().setAttribute("users", users);
		return INPUT;
	}

	public String save() {
		String[] userIds = this.getRequest().getParameterValues("userIds");
		Set<User> users = new HashSet<User>();
		if (userIds != null) {
			for (String id : userIds) {
				if (StringUtils.isNotBlank(id)) {
					users.add(userService.get(id));
				}
			}
		}
		group.setUsers(users);
		groupService.saveOrUpdate(group);
		return "reload";
	}

	public String delete() {
		String id = this.getRequest().getParameter("id");
		groupService.delete(id);
		return "reload";
	}

	private static final long serialVersionUID = -9199660547987154373L;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
