package com.hzth.myapp;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hzth.myapp.core.util.FileUtil;
import com.hzth.myapp.core.util.PropertyUtil;

public class Test {

	public static void main(String[] args) {
		// m1();
		System.out.println(Long.toHexString(Long.valueOf("72057594058113024")));
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
