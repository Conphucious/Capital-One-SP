package com.ngu_software.capital_one_sp.parse.credit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ngu_software.capital_one_sp.model.Category;
import com.ngu_software.capital_one_sp.model.Document;
import com.ngu_software.capital_one_sp.model.Transaction;
import com.ngu_software.capital_one_sp.parse.debit.FormatParser;

public class FormatParser2019 extends FormatParser {
	
	private static final int DATE_END_CHAR_COUNT = 6;

	private static Pattern pt = Pattern.compile("(?<=CYCLE)(.*\n?)(?=360 Checking)", Pattern.DOTALL);
	private static Pattern pd = Pattern.compile("[a-zA-Z]{3} [0-9]{1,2}");	// REGEX for MMM/dd/YYYY
	// TODO change activity to use pattern
//	private static Pattern pAct;
	private static Pattern pAmt = Pattern.compile("(?<= \\$)(.*?)(?=\\$)");
	
	public static Document parse(Date d, String text) {
		Document doc = new Document(d);
		String[] transText = findTransactions(text);

		for (int i = 0; i < transText.length; i++) {
			if (!isValidLine(transText[i]))
				continue;
			
			String activity = getActivity(transText[i]);
			Date date = getDate(d, transText[i]);

			double amt = getAmount(transText[i]);
			Category category = getCategory(amt);
			Transaction t = new Transaction(date, activity, category, amt);
			doc.addTransaction(t);
		}
		System.out.println(doc);

		return doc;
	}

	private static String[] findTransactions(String text) {
		Matcher m = pt.matcher(text);
		String[] transText = null;

		while (m.find())
			transText = text.substring(m.start(), m.end() - 1).split("\n");
		
		List<String> lineList = new ArrayList<>();
		
		for (int i = 0; i < transText.length; i++) {
			if (transText[i].contains("$") && !transText[i].contains("Opening Balance") && !transText[i].contains("Closing Balance"))
				lineList.add(transText[i]);
		}
		
		return lineList.stream().toArray(String[] :: new);
	}

	private static boolean isValidLine(String text) {
		if (text.length() > 6) {
			Pattern pd = Pattern.compile("^([a-zA-Z]{3})(\\s{1})([0-9]{1,2})");
			Matcher m = pd.matcher(text.substring(0, DATE_END_CHAR_COUNT).trim());
			if (m.matches())
				return true;
		}

		return false;
	}

	private static String getActivity(String text) {
		return text.substring(6, text.length()).trim().split(" - \\$| \\+ \\$")[0];
	}

	private static Date getDate(Date d, String text) {
		Matcher m = pd.matcher(text);
		while (m.find()) {
			String date = text.substring(m.start(), m.end()) + " " + d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
			// TODO createDate method needs update... Check with diff regex?
			try {
				return new SimpleDateFormat("MMM dd yyyy").parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private static double getAmount(String text) {
		Matcher m = pAmt.matcher(text);

		int multiplier = 1;
		if (!text.contains("+"))
			multiplier *= -1;
		
		while (m.find())
			return Double.parseDouble(text.substring(m.start(), m.end())) * multiplier;
		
		return 0;
	}

}
