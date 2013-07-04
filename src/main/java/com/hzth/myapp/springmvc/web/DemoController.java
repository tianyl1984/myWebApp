package com.hzth.myapp.springmvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzth.myapp.springmvc.model.Foo;

@Controller
@RequestMapping("/demo")
public class DemoController extends CommonController {

	@RequestMapping("/m1")
	@ResponseBody
	public void m1(Foo foo) throws Exception {
		System.out.println(foo.getClass().getClassLoader());
		this.print("ok");
	}
}
