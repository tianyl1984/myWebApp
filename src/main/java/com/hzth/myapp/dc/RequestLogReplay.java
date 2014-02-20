package com.hzth.myapp.dc;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import com.hzth.myapp.core.util.FileUtil;
import com.hzth.myapp.web.NetUtil;

public class RequestLogReplay {

	private static final String app_url = "http://127.0.0.1:8090/dc-exam";
	private static final String param_validate = "&sys_auto_authenticate=true&dataSourceName=dataSource1&sys_username=baimei&sys_password=000000";

	public static void main(String[] args) {
		m1();
	}

	private static void m1() {
		List<String> listStrs = FileUtil.readLines(new File("e:/request-bak.log"));
		int threadCount = 10;
		final int repeatCount = 10;
		ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
		for (String str : listStrs) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			String[] strs = str.split("\\|");
			final String timeStr = strs[0];
			final String method = strs[1];
			final String uri = strs[2];
			final String param = strs[3];
			if (!uri.startsWith("/ex/examSubject!saveSubjectLeaf")) {
				continue;
			}
			for (int i = 0; i < repeatCount; i++) {
				threadPool.submit(new Runnable() {
					@Override
					public void run() {
						try {
							System.out.println(uri);
							String result = NetUtil.getUrlResponse(app_url + uri, param + param_validate, method.equalsIgnoreCase("get"));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(1000, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
