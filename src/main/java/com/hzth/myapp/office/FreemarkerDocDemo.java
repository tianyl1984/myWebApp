package com.hzth.myapp.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

public class FreemarkerDocDemo {

	public static void main(String[] args) throws Exception {
		// Configuration config = new Configuration();
		// config.setClassForTemplateLoading(FreemarkerDocDemo.class, "/com/hzth/myapp/office");
		// // config.setDirectoryForTemplateLoading();
		// Template template = config.getTemplate("aaa.xml");
		// template.setEncoding("utf-8");
		// Map<String, Object> valMap = new HashMap<String, Object>();
		// String html = "<p>外部图片</p><p><img src=\"http://www.baidu.com/img/bdlogo.gif\" style=\"float:none;\" border=\"0\" height=\"129\" hspace=\"0\" vspace=\"0\" width=\"270\" /><br /></p>";
		// html = "";
		// valMap.put("title", "word标题" + html);
		// valMap.put("p1", "这是段落：");
		// valMap.put("p1Num", "1");
		// valMap.put("p2", "这是段落：");
		// valMap.put("p2Num", "2");
		// template.process(valMap, new OutputStreamWriter(new FileOutputStream(new File("e:/aa.doc"))));
		// System.out.println("完成");

		processDocFile();
	}

	private static void processDocFile() throws IOException {
		HWPFDocument doc = new HWPFDocument(new FileInputStream(new File("e:/bbb.doc")));
		Range range = doc.getRange();
		String text = range.text();
		System.out.println(text);
	}

}
