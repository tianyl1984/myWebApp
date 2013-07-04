package com.hzth.myapp.spring.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.core.web.MyBaseAction;
import com.hzth.myapp.spring.model.FooModel;

@ParentPackage(value = "struts-default")
@Namespace(value = "/test")
@Action(value = "propertyPlaceholderTest_*")
public class PropertyPlaceholderTestAction extends MyBaseAction {

	public void test() {
		this.print(fooModel.toString());
	}

	private static final long serialVersionUID = 7691202521518155098L;

	@Autowired
	private FooModel fooModel;

}
