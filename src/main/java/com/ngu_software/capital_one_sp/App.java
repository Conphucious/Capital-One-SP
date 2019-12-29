package com.ngu_software.capital_one_sp;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class App {
	public static void main(String[] args) {
		PDDocument document;
		try {
			document = PDDocument.load(new File("test.pdf"));
			if (!document.isEncrypted()) {
			    PDFTextStripper stripper = new PDFTextStripper();
			    String text = stripper.getText(document);
			    System.out.println("Text:" + text);
			}
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
