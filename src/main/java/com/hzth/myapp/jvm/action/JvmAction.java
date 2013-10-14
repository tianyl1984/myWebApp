package com.hzth.myapp.jvm.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.hzth.myapp.core.util.JsonUtil;
import com.hzth.myapp.core.web.MyBaseAction;

@ParentPackage(value = "crud-default")
@Namespace(value = "/jvm")
@Action(value = "info_*")
public class JvmAction extends MyBaseAction {

	private static final long serialVersionUID = -8463274986717677869L;

	public void getJvmInfo() {
		Map<String, String> result = new HashMap<String, String>();
		// 内存
		Runtime runtime = Runtime.getRuntime();
		double max = runtime.maxMemory() * 1.0 / 1024.0 / 1024.0;
		result.put("maxMemory", max + "");
		double total = runtime.totalMemory() * 1.0 / 1024.0 / 1024.0;
		result.put("totalMemory", total + "");
		double freeMemory = runtime.freeMemory() * 1.0 / 1024.0 / 1024.0;
		result.put("freeMemory", freeMemory + "");
		// 线程
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;
		// 遍历线程组树，获取根线程组
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		// 激活的线程数加倍
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		// 获取根线程组的所有线程
		int actualSize = topGroup.enumerate(slackList);
		// copy into a list that is the exact size
		Thread[] list = new Thread[actualSize];
		System.arraycopy(slackList, 0, list, 0, actualSize);
		for (int i = 0; i < list.length; i++) {
			// System.out.println(list[i].getName());
		}
		result.put("thread", list.length + "");
		String resultStr = JsonUtil.map2Json(result);
		this.print(resultStr);
	}
}
