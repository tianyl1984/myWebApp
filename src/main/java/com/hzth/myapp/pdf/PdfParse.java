package com.hzth.myapp.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfParse {

	public static void main(String[] args) {
		PDDocument document = null;
		try {
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\iOS_5_Programming_Cookbook.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\password.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Maven的生命周期和插件.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Maven的生命周期和插件2.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Maven的生命周期和插件3.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Java加密与解密的艺术 (1).pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Apress.Expert.MySQL.Jan.2007.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\ThinkingInUML.pdf"));
			document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\aaa.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\The American Pageant.pdf"));
			// pdfInfo(document);
			// readText(document);
			saveToPic(document);
			// readMenu(document);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void readMenu(PDDocument document) throws Exception {
		List allPages = document.getDocumentCatalog().getAllPages();
		PDDocumentOutline root = document.getDocumentCatalog().getDocumentOutline();
		PDOutlineItem item = root.getFirstChild();
		while (item != null) {
			item.findDestinationPage(document).getRotation();
			PDPage page = item.findDestinationPage(document);
			allPages.contains(page);
			System.out.println(allPages.indexOf(page));
			System.out.println("Item:" + item.getTitle() + ":");
			PDOutlineItem child = item.getFirstChild();
			while (child != null) {
				System.out.println("    Child:" + child.getTitle());
				child = child.getNextSibling();
			}
			item = item.getNextSibling();
		}
	}

	private static void readText(PDDocument document) throws Exception {
		Writer writer = new OutputStreamWriter(System.out);
		PDFTextStripper stripper = new PDFTextStripper();
		stripper.setStartPage(3);
		stripper.setEndPage(3);
		stripper.writeText(document, writer);
		writer.flush();
		writer.close();
	}

	private static void saveToPic(PDDocument document) throws Exception {
		List<PDPage> pages = document.getDocumentCatalog().getAllPages();
		PDPage page = pages.get(3);
		BufferedImage image = page.convertToImage();
		File file = new File("E:\\测试文档\\aaa.jpg");
		ImageIO.write(image, "jpg", file);
	}

	private static void pdfInfo(PDDocument document) throws Exception {
		PDDocumentInformation info = document.getDocumentInformation();
		System.out.println("Pages:" + document.getNumberOfPages());
		System.out.println("Author:" + info.getAuthor());
		System.out.println("Creator:" + info.getCreator());
		System.out.println("Keywords:" + info.getKeywords());
		System.out.println("Producer:" + info.getProducer());
		System.out.println("Subject:" + info.getSubject());
		System.out.println("Title:" + info.getTitle());
		System.out.println("Trapped:" + info.getTrapped());
		System.out.println("CreationDate:" + getCalendar(info.getCreationDate()));
		System.out.println("ModificationDate:" + info.getModificationDate() != null ? getCalendar(info.getModificationDate()) : "");
		String vals = "";
		for (String str : info.getMetadataKeys()) {
			vals = str + "--";
		}
		System.out.println("MetadataKeys:" + vals);
	}

	private static String getCalendar(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		String str = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
		return str;
	}

	private static void m1() {
		PDDocument document = null;
		try {
			Writer writer = new OutputStreamWriter(System.out);
			// Writer writer = new OutputStreamWriter(new FileOutputStream(new File("c:/aaa.html")));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\iOS_5_Programming_Cookbook.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\password.pdf"));
			document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Maven的生命周期和插件.pdf"));
			document.print();
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Maven的生命周期和插件2.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Maven的生命周期和插件3.pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Java加密与解密的艺术 (1).pdf"));
			// document = PDDocument.load(new File("C:\\Documents and Settings\\Administrator\\桌面\\Apress.Expert.MySQL.Jan.2007.pdf"));
			// int pageNum = document.getNumberOfPages();
			// System.out.println(pageNum + ":" + document.isEncrypted());
			// PDFTextStripper stripper = new PDFTextStripper();
			// stripper.setStartPage(1);
			// stripper.setEndPage(1);
			// stripper.writeText(document, writer);
			// stripper.writeText(document, writer);
			// PDFText2HTML pdfText2HTML = new PDFText2HTML("utf-8");
			// pdfText2HTML.setStartPage(1);
			// pdfText2HTML.setEndPage(5);
			// pdfText2HTML.writeText(document, writer);
			// writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
