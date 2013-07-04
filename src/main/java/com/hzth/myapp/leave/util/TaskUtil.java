package com.hzth.myapp.leave.util;

import java.util.HashMap;
import java.util.Map;

public class TaskUtil {

	public static final String getComplateJsFunction(String taskDefinitionKey) {
		Map<String, String> values = new HashMap<String, String>();
		values.put("usertask1", "deptComplate");
		values.put("usertask2", "leaderComplate");
		values.put("usertask3", "userDelLeave");
		values.put("usertask4", "userReAppLeave");
		return values.get(taskDefinitionKey);
	}

}
