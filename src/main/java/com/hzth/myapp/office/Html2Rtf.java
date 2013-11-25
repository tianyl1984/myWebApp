package com.hzth.myapp.office;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

import com.hzth.myapp.html.HtmlDemoUtil;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;

public class Html2Rtf {

	public static void main(String[] args) throws Exception {
		// m1();
		m2();
	}

	private static void m2() throws Exception {
		File file = new File("e:/aa.rtf");
		// 设置纸张大小
		Document document = new Document(PageSize.A4);
		// 建立一个书写器，与document对象关联
		RtfWriter2.getInstance(document, new FileOutputStream(file));
		document.open();
		StyleSheet ss = new StyleSheet();
		ArrayList<Element> list = HTMLWorker.parseToList(new FileReader(HtmlDemoUtil.getHtmlFile()), ss);
		for (Element ele : list) {
			document.add(ele);
		}
		document.close();
	}

	private static void m1() throws Exception {
		File file = new File("e:/aa.rtf");
		// 设置纸张大小
		Document document = new Document(PageSize.A4);
		// 建立一个书写器，与document对象关联
		RtfWriter2.getInstance(document, new FileOutputStream(file));
		document.open();
		// 设置中文字体
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		// 标题字体风格
		Font titleFont = new Font(bfChinese, 12, Font.BOLD);
		// 正文字体风格
		Font contextFont = new Font(bfChinese, 10, Font.NORMAL);
		Paragraph title = new Paragraph("标题");
		// 设置标题格式对齐方式
		title.setAlignment(Element.ALIGN_CENTER);
		title.setFont(titleFont);
		document.add(title);
		Paragraph context = new Paragraph("aaaaaaaaaaa");
		context.setAlignment(Element.ALIGN_LEFT);
		context.setFont(contextFont);
		// 段间距
		context.setSpacingBefore(3);
		// 设置第一行空的列数
		context.setFirstLineIndent(20);
		document.add(context);
		// 设置Table表格,创建一个三列的表格
		Table table = new Table(3);
		int width[] = { 25, 25, 50 };// 设置每列宽度比例
		table.setWidths(width);
		table.setWidth(90);// 占页面宽度比例
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		// 设置表头
		Cell haderCell = new Cell("表格表头");
		haderCell.setHeader(true);
		haderCell.setColspan(3);
		table.addCell(haderCell);
		table.endHeaders();

		Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, Color.BLACK);
		Cell cell = new Cell(new Paragraph("这是一个3*3测试表格数据", fontChinese));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		table.addCell(new Cell("#1"));
		table.addCell(new Cell("#2"));
		table.addCell(new Cell("#3"));

		document.add(table);
		document.close();
	}
}
