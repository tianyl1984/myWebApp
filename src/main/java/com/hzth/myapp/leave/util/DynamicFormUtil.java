package com.hzth.myapp.leave.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.form.FormProperty;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.hzth.myapp.core.util.JsonUtil;

public class DynamicFormUtil {

	public static final Map<String, String> getFormProperties(HttpServletRequest request) {
		Map<String, String> formProperties = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();
			// fp_的意思是form paremeter
			if (StringUtils.defaultString(key).startsWith("fp_")) {
				formProperties.put(key.split("_")[1], entry.getValue()[0]);
			}
		}
		return formProperties;
	}

	public static final String getFormValue(List<FormProperty> formProperties, String key) {
		for (FormProperty formProperty : formProperties) {
			if (formProperty.getId().equals(key)) {
				return formProperty.getValue();
			}
		}
		return null;
	}

	public static final String getFormJsonString(List<FormProperty> formProperties) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (FormProperty formProperty : formProperties) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", formProperty.getId());
			map.put("name", formProperty.getName());
			map.put("value", StringUtils.defaultString(formProperty.getValue()));
			map.put("typeName", formProperty.getType().getName());
			map.put("writable", formProperty.isWritable());
			map.put("readable", formProperty.isReadable());
			map.put("required", formProperty.isRequired());
			map.put("datePattern", ObjectUtils.toString(formProperty.getType().getInformation("datePattern"), ""));
			map.put("values", ObjectUtils.defaultIfNull(formProperty.getType().getInformation("values"), null));
			resultList.add(map);
		}
		return JsonUtil.collection2Json(resultList);
	}
}
