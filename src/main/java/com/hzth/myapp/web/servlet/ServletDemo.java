package com.hzth.myapp.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletDemo", urlPatterns = "/servletDemo", asyncSupported = true)
public class ServletDemo extends HttpServlet {

	private static final long serialVersionUID = 6281913042215428096L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Connection", "Keep-Alive");
		resp.setContentType("text/html;charset=utf-8");

		// asyn(req, resp);
		sync(req, resp);

	}

	private void sync(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.write("hello sync");
		out.write("<br/>");
		out.flush();
		try {
			Thread.sleep(1000);
			out.write("1");
			out.flush();
			Thread.sleep(1000);
			out.write("2");
			out.flush();
			Thread.sleep(1000);
			out.write("3");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		resp.reset();
		req.getRequestDispatcher("/").forward(req, resp);
	}

	private void asyn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("start:" + Thread.currentThread().getName());

		PrintWriter out = resp.getWriter();
		out.write("hello async");
		out.write("<br/>");
		out.flush();
		// 1、开启异步
		final AsyncContext asyncContext = req.startAsync();
		// 2、设置超时时间，如果不设置如jetty是30000L
		// asyncContext.setTimeout(10L * 1000);
		// 3、告诉容器分派一个新的线程执行异步任务
		// 这种方式的缺点就是可能和请求用同一个线程池，不推荐这种做法；从本质上讲和同步没啥区别（都要占用一个服务器线程）
		// 不过如果请求压力较小，可以使用这种方法（因为有超时设置，可以防止一直不响应）
		asyncContext.start(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("in async:" + Thread.currentThread().getName());
					PrintWriter out = asyncContext.getResponse().getWriter();
					Thread.sleep(1000);
					out.write("1");
					out.flush();
					Thread.sleep(1000);
					out.write("2");
					out.flush();
					Thread.sleep(1000);
					out.write("3");
					out.flush();
					asyncContext.complete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("finish");
	}
}
