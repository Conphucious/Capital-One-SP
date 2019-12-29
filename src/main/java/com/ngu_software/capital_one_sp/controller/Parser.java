package com.ngu_software.capital_one_sp.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.ngu_software.capital_one_sp.CO;

public class Parser {

	public Parser(File directory) {
		if (!directory.isDirectory()) {
			//create throwable error here
		}
		
		File[] files = directory.listFiles();
		for (File file: files) {
			if (FilenameUtils.getExtension(file.getName()).contains("pdf"))
				process(file);
		}
	}

	public void process(File file) {
		String text = processDocument(file);
//		System.out.println(text);

		// TODO Need to use regex instead of all these regex splits
		String date = text.contains(CO.IDENTIFIER_2014) ? text.split(CO.IDENTIFIER_2014)[1].split(" ")[0]
				: text.split(CO.IDENTIFIER_2019)[1].split("\n")[0].split("- ")[1];
//		System.out.println(date);
		Date d = createDate(date.replace(",", ""));
//		System.out.println(d);

		System.out.println(d.after(createDate(CO.NEW_FORMAT_DATE)) ? "Yes" : "No");

	}

	private String processDocument(File pdf) {
		PDDocument document = null;
		String text = null;
		try {
			document = PDDocument.load(pdf);
			if (!document.isEncrypted())
				text = new PDFTextStripper().getText(document);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}

	private Date createDate(String date) {
		try {
			String format = date.contains("/") ? "MM/dd/yyyy" : "MMM dd yyyy";
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
