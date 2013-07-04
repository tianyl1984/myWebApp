package com.hzth.myapp.user.util;

import org.springframework.stereotype.Component;

@Component
public class TestInterfaceImpl implements ITestInterface {

	public void test2() {
		System.out.println("-------test2-------");
	}

}
