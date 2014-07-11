package com.hzth.myapp;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hzth.myapp.core.util.FileUtil;
import com.hzth.myapp.core.util.PropertyUtil;
import com.hzth.myapp.web.NetUtil;

public class Test {

	public static void main(String[] args) throws Exception {
		// Robot r = new Robot();
		// r.mouseMove(0, 0);
		// r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		// r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		// m1();
		// System.out.println(Long.toHexString(Long.valueOf("72057594058113024")));
		ExecutorService threadPool = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 100000; i++) {
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						String result = NetUtil.getUrlResponse("http://192.168.30.123:81/dc/demo.jsp").replaceAll("<.*?>", "").trim();
						System.out.println(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		}
		threadPool.shutdown();
		threadPool.awaitTermination(100000, TimeUnit.DAYS);
	}

	private static void m1() {
		List<String> strs = FileUtil.readLines(new File("E:\\workspace3.7\\minicourse\\webapp\\framework\\component\\dataGrid\\js\\com.ue.datagrid.js"));
		for (String str : strs) {
			if (str.contains("jQuery.i18n.prop(")) {
				Pattern pattern = Pattern.compile("jQuery.i18n.prop\\(.*?\\)");
				Matcher matcher = pattern.matcher(str);
				while (matcher.find()) {
					String img = matcher.group();
					String img1 = img.replace("jQuery.i18n.prop(\"", "").replace("\")", "");
					String replacement = "\"" + PropertyUtil.getProperty2("E:\\workspace3.7\\dc-framework\\src\\main\\resources\\i18n\\dataGrid_zh.properties", img1) + "\"";
					str = str.replace(img, replacement);
				}
				System.out.println(str);
			}
		}

	}

}
