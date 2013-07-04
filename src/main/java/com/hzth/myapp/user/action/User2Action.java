package com.hzth.myapp.user.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.hzth.myapp.core.web.MyBaseAction;
import com.hzth.myapp.user.model.Student;
import com.hzth.myapp.user.model.Teacher;
import com.hzth.myapp.user.model.User;

@ParentPackage(value = "myPkg")
@Namespace(value = "/um")
@Action(value = "user2_*")
@Results({
		@Result(name = "OK", type = "myCustomType", params = { "param1", "/um/user/jsp/user.jsp", "param2", "sfadsdf挥洒的" }),
		@Result(name = "showjson", type = "json", params = { "ignoreHierarchy", "false", "includeProperties", "user1.*", "excludeProperties", "user1.teacher.students\\[\\d+\\]\\.address" }) })
public class User2Action extends MyBaseAction {

	private static final long serialVersionUID = -7217667200094337442L;

	public String list() {
		return "OK";
	}

	public String list2() {
		user1 = new User("user1", "sdfsdf", "laskjflkasjdflk");
		user1.setTeacher(Teacher.getATeacher());
		// user2 = new User("user2", "sdfdddsdf", "laskjflkasjdflk");
		user3 = new User("user3", "asfdasdf", "asdfasdfsdaf");
		User user4 = new User("user4", "safdsfd", "sfsadfsdcvxc");
		ServletActionContext.getRequest().setAttribute("user4", user4);
		return "showjson";
	}

	public void testStudents() {
		for (Student stu : students) {
			System.out.println(stu.getName());
		}
		this.print("OK");
	}

	private List<Student> students = new ArrayList<Student>();

	private User user1;

	private User user3;

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser3() {
		return user3;
	}

	public void setUser3(User user3) {
		this.user3 = user3;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
