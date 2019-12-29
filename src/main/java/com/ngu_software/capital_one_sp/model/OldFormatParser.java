package com.ngu_software.capital_one_sp.model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldFormatParser {

	private Pattern pattern = Pattern.compile("(?<=Opening Balance )(.*\n?)(?=Closing Balance )", Pattern.DOTALL);

	public OldFormatParser(Date d, String text) {
		Document doc = new Document(d);
//		String transText = text.split("Opening Balance ")[1].split("Go mobile and bank on the fly.")[0];
		String transText = null;
		Matcher m = pattern.matcher(text);
		
		while (m.find()) {
			transText = text.substring(m.start(), m.end() - 1);
			System.out.println(transText);
		}

//		String tt = text.split("?<=Opening Balance )(.*\n?)(?=Go mobile and bank on the fly.")[0];
//		System.out.println(transText);
	}

}
