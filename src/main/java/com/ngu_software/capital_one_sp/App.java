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
//		System.out.println(text);
		
		// TODO Need to use regex instead of all these regex splits
		String date = text.contains(CO.IDENTIFIER_2014) ? text.split(CO.IDENTIFIER_2014)[1].split(" ")[0] : text.split(CO.IDENTIFIER_2019)[1].split("\n")[0].split("- ")[1];
		System.out.println(date);
		Date d = createDate(date.replace(",", ""));
		System.out.println(d);

		System.out.println(d.after(createDate(CO.NEW_FORMAT_DATE)) ? "Yes" : "No");

	}

	private static String processDocument(File pdf) {
		PDDocument document = null;
		String text = null;
		try {
			document = PDDocument.load(pdf);
			if (!document.isEncrypted()) {
				PDFTextStripper stripper = new PDFTextStripper();
				text = stripper.getText(document);
			}
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}

	private static Date createDate(String date) {
		try {
			String format = date.contains("/") ? "MM/dd/yyyy" : "MMM dd yyyy";
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
