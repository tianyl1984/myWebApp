package com.hzth.myapp.html;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupDemo {

	public static void main(String[] args) throws IOException {
		m1();
	}

	private static void m1() throws IOException {
		Document doc = Jsoup.connect("http://www.baidu.com").userAgent("Mozilla").get();
		// System.out.println(doc.html());
		Element ele = doc.getElementById("u");
		System.out.println(ele.html());
		Elements eles = ele.select("a");
		System.out.println(eles.first().text());
	}
}
