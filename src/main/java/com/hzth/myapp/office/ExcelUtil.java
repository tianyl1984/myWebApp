package com.hzth.myapp.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

public class ExcelUtil {

	public static void writeExcel() throws Exception {
		Workbook wb = new HSSFWorkbook();
		// Workbook wb = new XSSFWorkbook();//07格式
		Sheet sheet1 = wb.createSheet();
		Sheet sheet2 = wb.createSheet("sheet名称");

		CreationHelper createHelper = wb.getCreationHelper();
		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet1.createRow(0);

		// Create a cell and put a value in it.
		Cell cell = row.createCell(0);
		cell.setCellValue(1);
		// Or do it on one line.
		row.createCell(1).setCellValue(1.2);
		row.createCell(2).setCellValue(createHelper.createRichTextString("This is a string"));
		row.createCell(3).setCellValue(true);

		// 时间类型
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
		Cell dateCell = row.createCell(4);
		dateCell.setCellValue(new Date());
		dateCell.setCellStyle(cellStyle);

		// 错误类型
		row.createCell(5, Cell.CELL_TYPE_ERROR).setCellErrorValue(FormulaError.DIV0.getCode());

		// 带格式文本
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);// 水平
		style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);// 垂直
		style.setBorderTop(CellStyle.BORDER_MEDIUM_DASH_DOT_DOT);// 边框
		style.setTopBorderColor(IndexedColors.BLUE.getIndex());// 边框颜色
		Cell strCell = row.createCell(6);
		strCell.setCellValue(createHelper.createRichTextString("中文文本"));
		strCell.setCellStyle(style);

		Row row2 = sheet1.createRow(1);
		// 合并
		row2.createCell(0).setCellValue("合并单元格测试");
		sheet1.addMergedRegion(new CellRangeAddress(row2.getRowNum(), row2.getRowNum() + 2, 0, 2));

		Row row3 = sheet1.createRow(2);
		row3.createCell(0).setCellValue("aaa");

		sheet1.createRow(3).createCell(1).setCellValue("bbb");
		sheet1.createRow(4).createCell(2).setCellValue("ccc");
		sheet1.createRow(5).createCell(3).setCellValue("ddd");

		Cell wrapCell = sheet1.createRow(6).createCell(4);
		wrapCell.setCellValue(createHelper.createRichTextString("换行\n测试\nfjsdf\nshk"));
		CellStyle wrapStyle = wb.createCellStyle();
		wrapStyle.setWrapText(true);
		wrapCell.setCellStyle(wrapStyle);

		// sheet1.shiftRows(6, 7, 3);

		FileOutputStream fileOut = new FileOutputStream("e:/workbook.xlsx");
		wb.write(fileOut);
		fileOut.close();
	}

	public static void readExcel() throws Exception {
		FileInputStream fileIn = new FileInputStream("e:/工作簿1.xlsx");
		// FileInputStream fileIn = new FileInputStream("e:/test.xlsx");
		Workbook wb = WorkbookFactory.create(fileIn);// 通用
		// Workbook wb = new HSSFWorkbook(new POIFSFileSystem(fileIn));//03
		// Workbook wb = new XSSFWorkbook(fileIn);//07
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row) {
				CellReference cellRef = new CellReference(cell);
				System.out.print("[" + cellRef.formatAsString() + "]:[");
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					System.out.print("]");
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue() + "]");
					break;
				case Cell.CELL_TYPE_ERROR:
					System.out.print(cell.getErrorCellValue() + "]");
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue() + "]");
					break;
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue() + "]");
					break;
				case Cell.CELL_TYPE_FORMULA:
					System.out.print("公式结果:" + cell.getCellFormula() + "]");
					break;
				default:
					System.out.print("");
					break;
				}
			}
			System.out.println("");
		}
	}

	public static void readAll() throws Exception {
		ExcelExtractor extractor = new ExcelExtractor(new POIFSFileSystem(new FileInputStream("e:/test.xls")));
		extractor.setFormulasNotResults(false);
		extractor.setIncludeSheetNames(false);
		String text = extractor.getText();
		System.out.println("text:");
		System.out.println(text);
	}

	public static void main(String[] args) throws Exception {
		// writeExcel();
		// readExcel();
		// readAll();
		readTest();
		System.out.println("完成");
	}

	private static void readTest() throws Exception {
		File fileDir = new File("C:\\Users\\tianyl\\Desktop\\日文\\");
		FileInputStream fileIn = new FileInputStream("C:\\Users\\tianyl\\Desktop\\日文\\基础业务平台国际化翻译.xlsx");
		Workbook wb = WorkbookFactory.create(fileIn);// 通用
		int sheetCount = wb.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			String sheetName = wb.getSheetName(i);
			Sheet sheet = wb.getSheetAt(i);
			Properties prop = new Properties();
			for (Row row : sheet) {
				String str0 = "";
				String str1 = "";
				int index = 0;
				for (Cell cell : row) {
					if (index == 0) {
						str0 = cell.getStringCellValue();
					}
					if (index == 1) {
						str1 = cell.getStringCellValue();
					}
					index++;
				}
				if (StringUtils.isNotBlank(str0) && str0.contains("=")) {
					String key = str0.substring(0, str0.indexOf("="));
					String value = str0.substring(str0.indexOf("=") + 1);
					if (StringUtils.isNotBlank(str1)) {
						value = str1;
					}
					prop.put(key, value);

					// FileUtil.appendln(fileDir.getAbsolutePath() + "/" + fileName, key + "=" + value);
				}
			}
			String fileName = sheetName.substring(0, sheetName.indexOf("_zh.")) + "_en.properties";
			FileOutputStream fos = new FileOutputStream(new File(fileDir.getAbsolutePath() + "/" + fileName));
			prop.store(fos, "");
			fos.close();
		}
	}
}
