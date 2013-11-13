package com.hzth.myapp.pdf;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.hzth.myapp.html.HtmlDemoUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class Html2Pdf {

	public static void main(String[] args) throws Exception {
		// m1();
		m2();
		System.out.println("完成!");
	}

	private static void m2() throws Exception {
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver();
		// fontResolver.addFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		renderer.setDocument(HtmlDemoUtil.getHtmlFile());
		fontResolver.addFont("C:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		renderer.layout();
		renderer.createPDF(new FileOutputStream("E:/测试文件/html2pdf.pdf"));
		renderer.finishPDF();
	}

	private static void m1() throws Exception {
		Document doc = new Document();
		PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream("E:/测试文件/html2pdf.pdf"));
		doc.open();
		doc.addAuthor("tianyl");
		doc.addCreationDate();
		doc.addTitle("html2pdf title");
		StyleSheet ss = new StyleSheet();
		// ss.loadTagStyle("body", "leading", "16,0");
		ArrayList<Element> list = HTMLWorker.parseToList(new FileReader(HtmlDemoUtil.getHtmlFile()), ss);
		for (Element ele : list) {
			// doc.add(ele);
		}
		BaseFont font = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
		Font font1 = new Font(font, 12);
		doc.add(new Paragraph("First page of中文中文 the document.", font1));
		doc.add(new Paragraph("First page of中文中文 the document."));
		doc.close();
		pdfWriter.flush();
		pdfWriter.close();
	}
}
