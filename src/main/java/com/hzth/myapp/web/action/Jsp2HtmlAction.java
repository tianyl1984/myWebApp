package com.hzth.myapp.web.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.core.web.MyBaseAction;
import com.hzth.myapp.user.service.UserService;

@ParentPackage(value = "struts-default")
@Namespace(value = "/web")
@Action(value = "jsp2Html_*")
public class Jsp2HtmlAction extends MyBaseAction {

	public void test() throws Exception {
		String jspUrl = "/um/user/jsp/user.jsp";
		RequestDispatcher rd = this.getRequest().getRequestDispatcher(jspUrl);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(baos, "utf-8"));
		HttpServletResponse resp = new HttpServletResponseWrapper(this.getResponse()) {
			@Override
			public PrintWriter getWriter() throws IOException {
				return pw;
			}
		};
		// jsp页面需要的数据
		this.getRequest().setAttribute("users", userService.findAll());
		rd.include(getRequest(), resp);
		pw.flush();
		String html = baos.toString("utf-8");
		System.out.println(html);
		System.out.println(System.getProperty("java.vm.name"));
		this.print("完成");
	}

	private static final long serialVersionUID = -6930183484527961765L;

	@Autowired
	private UserService userService;
}
