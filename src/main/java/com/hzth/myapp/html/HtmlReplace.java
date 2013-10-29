package com.hzth.myapp.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class HtmlReplace {

	public static void main(String[] args) {
		String str = "asfsfd中生代<p><img src=\"/dc-notice//component/attachment!showPic.action?checkUser=false&amp;period=forever&amp;downloadToken=201310210912566469440685450170326210e537ab7425ada8f8cd2430ccb36f&amp;configCode=fw_ueditor\" style=\"float:none;\" title=\"bug.jpg\" /></p>";
		str += "<p><img src aa=\"bb\"/><img src=\"/dc-notice//component/attachment!showPic.action?checkUser=false&amp;period=forever&amp;downloadToken=201310210912572327787889149801096210e537ab7425ada8f8cd2430ccb36f&amp;configCode=fw_ueditor\" style=\"float:none;\" title=\"QQ截图20130613144834.jpg\" /></p>";
		str += "<img src=\"http://www.baidu.com\" style=\"float:none;\" title=\"bug.jpg\" />结尾内容";
		Pattern pattern = Pattern.compile("<img.*?/>");
		Matcher matcher = pattern.matcher(str);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			String img = matcher.group();
			// System.out.println(img);
			Pattern p1 = Pattern.compile("src=\".*?\"");
			if (p1.matcher(img).find()) {// 有src
				if (!img.contains("src=\"http")) {// src不是以http开头
					img = img.replaceAll("src=\"", "src=\"http://192.168.1.4:8888");
				}
			}
			matcher.appendReplacement(buffer, img);
		}
		HttpServletRequest request = null;
		matcher.appendTail(buffer);
		System.out.println(buffer.toString());
	}
}
