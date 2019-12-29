package com.ngu_software.capital_one_sp;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

// 01/31/2019 is after the new pdf formats for capital one 360

public class App {
	public static void main(String[] args) {
		String text = processDocument(new File("test.pdf"));
		
		String date = text.split("Summary as of ")[1].split(" ")[0];
		System.out.println(date);
		Date d = createDate(date);
		System.out.println(d);
		
		if (d.after(createDate(CO.NEW_FORMAT_DATE))) {
			System.out.println("YUP");
		}
		
	}
	
	private static String processDocument(File pdf) {
		PDDocument document = null;
		String text = null;
		try {
			document = PDDocument.load(pdf);
			if (!document.isEncrypted()) {
			    PDFTextStripper stripper = new PDFTextStripper();
			    text = stripper.getText(document);
//			    System.out.println("Text:" + text);
			}
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text;
	}
	
	private static Date createDate(String date) {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
