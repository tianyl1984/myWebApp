package com.hzth.myapp.pdf;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PdfSave {

	public static void main(String[] args) throws Exception {
		// m1();
		m2();
	}

	private static void m2() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:ebook.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from teachingmaterialcontent where id_teachingmaterial = '20120608100230593929944108289191' order by pageNo;"); // 查询数据
		List<String> fileNames = new ArrayList<String>();
		while (rs.next()) { // 将查询到的数据打印出来
			String fileName = rs.getString("filePath").replaceFirst("/mnt/sdcard/ebookdata/", "");
			fileNames.add(fileName);
		}
		rs.close();
		conn.close();
		// PDDocument doc = new PDDocument();
		int i = 0;
		for (String str : fileNames) {
			// PDPage page = new PDPage();
			// PDStream contents = new PDStream(doc, new FileInputStream(new File("F:/android程序/电子书包/ebookdatabak/" + str)));
			// page.setContents(contents);
			// doc.addPage(page);
			String num = i + "";
			if (num.length() == 1) {
				num = "00" + i;
			}
			if (num.length() == 2) {
				num = "0" + i;
			}
			FileUtils.copyFile(new File("F:/android程序/电子书包/ebookdatabak/" + str), new File("F:/android程序/电子书包/ebookdatabak2/" + num));
			i++;
		}
		// doc.save("e:/aaa.pdf");
	}

	private static void m1() throws Exception {
		PDDocument document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\aaa.pdf"));
		List allPages = document.getDocumentCatalog().getAllPages();
		PDDocument newDocument = new PDDocument();
		for (Object obj : allPages) {
			newDocument.addPage((PDPage) obj);
		}
		newDocument.save("C:\\Documents and Settings\\Administrator\\桌面\\bbb.pdf");
		System.out.println("完成。共" + allPages.size() + "页");

	}
}
