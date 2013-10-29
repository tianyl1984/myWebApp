package com.hzth.myapp.office;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerDocDemo {

	public static void main(String[] args) throws Exception {
		Configuration config = new Configuration();
		config.setClassForTemplateLoading(FreemarkerDocDemo.class, "/com/hzth/myapp/office");
		// config.setDirectoryForTemplateLoading();
		Template template = config.getTemplate("aaa.xml");
		template.setEncoding("utf-8");
		Map<String, Object> valMap = new HashMap<String, Object>();
		valMap.put("title", "word标题");
		valMap.put("p1", "这是段落：");
		valMap.put("p1Num", "1");
		valMap.put("p2", "这是段落：");
		valMap.put("p2Num", "2");
		template.process(valMap, new OutputStreamWriter(new FileOutputStream(new File("e:/aa.doc"))));
		System.out.println("完成");
	}
}
