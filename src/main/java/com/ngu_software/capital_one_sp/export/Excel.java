package com.ngu_software.capital_one_sp.export;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	public static void main(String[] args) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");

		sheet.getWorkbook().createCellStyle();

		createHeaderRow(sheet);

		Object[][] bookData = { { "Head First Java", "Kathy Serria", 79 }, { "Effective Java", "Joshua Bloch", 36 },
				{ "Clean Code", "Robert martin", 42 }, { "Thinking in Java", "Bruce Eckel", 35 }, };

		int rowCount = 0;

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(++rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(++columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}

		}

		String fileName = "JavaBooks.xlsx";
		
		autoSizeColumns(workbook);
		
		// longest text count characters and put in column size.
//		sheet.autoSizeColumn(300);
		
		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
			workbook.write(outputStream);
			Desktop d = Desktop.getDesktop();
			d.open(new File(fileName));
		}
	}

	private static void createHeaderRow(XSSFSheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		cellStyle.setFont(font);
		
		Row row = sheet.createRow(0);
		
		Cell cellTitle = row.createCell(1);
		cellTitle.setCellStyle(cellStyle);
		cellTitle.setCellValue("Date");
		
		Cell cellAuthor = row.createCell(2);
		cellAuthor.setCellStyle(cellStyle);
		cellAuthor.setCellValue("Description");
		
		Cell cellPrice = row.createCell(3);
		cellPrice.setCellStyle(cellStyle);
		cellPrice.setCellValue("Category");
		
		Cell cellPrice2 = row.createCell(3);
		cellPrice2.setCellStyle(cellStyle);
		cellPrice2.setCellValue("Amount");
	}
	
	/**
	 * Ondrej Kvasnovsky
	 * https://stackoverflow.com/questions/4611018/apache-poi-excel-how-to-configure-columns-to-be-expanded
	 * @param workbook
	 */
	public static void autoSizeColumns(Workbook workbook) {
	    int numberOfSheets = workbook.getNumberOfSheets();
	    for (int i = 0; i < numberOfSheets; i++) {
	    	XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(i);
	        if (sheet.getPhysicalNumberOfRows() > 0) {
	            Row row = sheet.getRow(sheet.getFirstRowNum());
	            Iterator<Cell> cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                int columnIndex = cell.getColumnIndex();
	                sheet.autoSizeColumn(columnIndex);
	            }
	        }
	    }
	}

}
