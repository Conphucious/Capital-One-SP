package com.ngu_software.capital_one_sp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldFormatParser {

	private Pattern pt = Pattern.compile("(?<=Opening Balance )(.*\n?)(?=Closing Balance )", Pattern.DOTALL);
	private Pattern pd = Pattern.compile("\\b(\\d{2}\\/\\d{2}\\/\\d{4})");

	public OldFormatParser(Date d, String text) {
		Document doc = new Document(d);
		String[] transText = findTransactions(text);
		
		for (int i = 1; i < transText.length; i++) {
			Date date = findDate(transText[i]);
			String activity = findActivity(date, transText[i]);
			
//			Transaction t = new Transaction(date, activity);

		}
	}

	private String[] findTransactions(String text) {
		Matcher m = pt.matcher(text);
		String[] transText = null;

		while (m.find())
			transText = text.substring(m.start(), m.end() - 1).split("\n");
		
		return transText;
	}

	private Date findDate(String text) {
		Date date = null;
		Matcher m = pd.matcher(text);
		while (m.find()) {
			date = createDate(text.substring(m.start(), m.end()));
		}
		return date;
	}
	
	private String findActivity(Date date, String text) {
		Pattern pActivity = Pattern.compile("([^" + new SimpleDateFormat("MM/dd/yyyy").format(date) + "]+)");
		Matcher m = pActivity.matcher(text);
		
		while (m.find()) {
			return text.substring(m.start(), m.end() - 1);
		}
		
		return null;
	}

	private Date createDate(String date) {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
