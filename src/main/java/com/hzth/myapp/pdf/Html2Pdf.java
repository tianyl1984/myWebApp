package com.hzth.myapp.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

import com.hzth.myapp.html.HtmlDemoUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class Html2Pdf {

	public static void main(String[] args) throws Exception {
		File file = new File("E:/测试文件/html2pdf.pdf");
		if (file.exists()) {
			file.delete();
		}
		// m1();
		m2();
		// m3(new URL("http://192.168.1.20:8012/dc/ex/scoreCount!getImgStudentCourse.action?examStudentId=20131112155604538189137229734577&totalScoreId=20131126134042917589915830999169&_t=20131210141947908828115219233922_&sys_username=administrator&sys_password=000000&sys_auto_authenticate=true&dataSourceName=dataSource1"));
		System.out.println("完成!");
		// process("a哈萨克奋斗b");
	}

	private static void m2() throws Exception {
		// ITextRenderer renderer = new ITextRenderer();
		// renderer.getSharedContext().setBaseURL("http://www.baidu.com/");
		// ITextFontResolver fontResolver = renderer.getFontResolver();
		// // fontResolver.addFont("STSong-Light.ttc", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		// // renderer.setDocument(HtmlDemoUtil.getHtmlFile());
		// String content = HtmlDemoUtil.getHtmlStr();
		// // content = process(content);
		// renderer.setDocumentFromString(content);
		// fontResolver.addFont("C:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// renderer.layout();
		// renderer.createPDF(new FileOutputStream("E:/测试文件/html2pdf.pdf"));
		// renderer.finishPDF();
	}

	private static String process(String content) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (isChinese(c)) {
				sb.append(c + "<wbr/>");
			} else {
				sb.append(c);
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private static void m1() throws Exception {
		Document doc = new Document();
		PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream("E:/测试文件/html2pdf.pdf"));
		doc.open();
		doc.addAuthor("tianyl");
		doc.addCreationDate();
		doc.addTitle("html2pdf title");
		StyleSheet ss = new StyleSheet();
		// ss.loadTagStyle("body", "leading", "16,0");
		ArrayList<Element> list = HTMLWorker.parseToList(new FileReader(HtmlDemoUtil.getHtmlFile2()), ss);
		for (Element ele : list) {
			doc.add(ele);
		}
		BaseFont font = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
		Font font1 = new Font(font, 12);
		// doc.add(new Paragraph("First page of中文中文 the document.", font1));
		// doc.add(new Paragraph("First page of中文中文 the document."));
		doc.close();
		pdfWriter.flush();
		pdfWriter.close();
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
}
