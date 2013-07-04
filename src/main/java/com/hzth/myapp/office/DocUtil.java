package com.hzth.myapp.office;

import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocUtil {

	public static void main(String[] args) throws Exception {
		createDocx();
	}

	private static void createDocx() throws Exception {
		XWPFDocument doc = new XWPFDocument();

		XWPFTable tab = doc.createTable();
		XWPFTableRow row = tab.getRow(0);
		XWPFTableCell cell1 = row.getCell(0);
		cell1.setText("sadfsdfa");
		XWPFTableCell cell2 = row.createCell();
		cell2.setText("中文");

		XWPFTableRow row2 = tab.createRow();
		row2.createCell().setText("asdf");

		FileOutputStream out = new FileOutputStream("c:\\simple.docx");
		doc.write(out);
		out.close();
	}
}
