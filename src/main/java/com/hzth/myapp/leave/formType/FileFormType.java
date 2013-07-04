package com.hzth.myapp.leave.formType;

import org.activiti.engine.impl.form.AbstractFormType;

public class FileFormType extends AbstractFormType {

	@Override
	public String getName() {
		return "file";
	}

	@Override
	public Object convertFormValueToModelValue(String propertyValue) {
		return propertyValue;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) {
		if (modelValue == null) {
			return "没有值";
		}
		return null;
	}

	public Object getInformation(String key) {
		return null;
	}
}
