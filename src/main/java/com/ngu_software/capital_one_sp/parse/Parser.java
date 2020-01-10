package com.ngu_software.capital_one_sp.parse;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.ngu_software.capital_one_sp.Identifier;
import com.ngu_software.capital_one_sp.model.Document;

public class Parser {
	
	private List<Document> docList;
	private int filesParsed;

	public Parser(File directory) {
		
		docList = new ArrayList<>();
		
		filesParsed = 0;
		
		if (!directory.isDirectory()) {
			//create throwable error here
		}
		
		File[] files = directory.listFiles();
		for (File file: files) {
			if (FilenameUtils.getExtension(file.getName()).contains("pdf")) {
				process(file);
				filesParsed++;
			}
		}
		
		System.out.println("FILES PARSED: " + filesParsed);
	}

	public List<Document> getDocList() {
		return docList;
	}
	
	public void process(File file) {
		String text = extractDocument(file);
//		System.out.println(text);
//		System.out.println("-----------------------");

		// TODO Need to use regex instead of all these regex splits
		String date = text.contains(Identifier.FORMAT_2014) ? text.split(Identifier.FORMAT_2014)[1].split(" ")[0]
				: text.split(Identifier.FORMAT_2019)[1].split("\n")[0].split("- ")[1];
//		System.out.println(date);
		Date d = createDate(date.replace(",", ""));
//		System.out.println(d);
//		System.out.println(d.after(createDate(CO.NEW_FORMAT_DATE)) ? "Yes" : "No");
		
		if (d.after(createDate(Identifier.FORMAT_DATE_2019))) {
//			parseNewFormat(d, text);
			docList.add(FormatParser2019.parse(d, text));
		} else {
//			docList.add(FormatParser2014.parse(d, text));
//			parseOldFormat(d, text);
		}
		
	}
	
//	private void parseNewFormat(Date date, String text) {
//		
//	}
	
//	private void parseOldFormat(Date date, String text) {
//		Document d = new Document(date);
//		
//		d.addTransaction(t);
//	}

	private String extractDocument(File pdf) {
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
