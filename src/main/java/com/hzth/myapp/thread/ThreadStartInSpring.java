package com.hzth.myapp.thread;

import java.util.List;

import com.hzth.myapp.core.util.SpringContextHolder;
import com.hzth.myapp.user.model.User;
import com.hzth.myapp.user.service.UserService;

public class ThreadStartInSpring {

	public ThreadStartInSpring() {

	}

	public void init() {
		System.out.println("init...");
		// new DBOperateThread().start();
	}

	class DBOperateThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("数据库操作");
				UserService userService = (UserService) SpringContextHolder.getApplicationContext().getBean("userService");
				List<User> users = userService.findAll();
				if (users.size() > 0) {
					User user = users.get(0);
					user.setName("aaaa");
					userService.saveOrUpdate(user);
				}
				System.out.println("数据库操作完成");
			}
		}
	}
}
