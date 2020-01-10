package com.ngu_software.capital_one_sp.parse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ngu_software.capital_one_sp.model.Category;
import com.ngu_software.capital_one_sp.model.Document;
import com.ngu_software.capital_one_sp.model.Transaction;

public class FormatParser2019 extends FormatParser {

	private static Pattern pt = Pattern.compile("(?<=CYCLE)(.*\n?)(?=360 Checking)", Pattern.DOTALL);
//	private static Pattern pd = Pattern.compile("\\b(\\d{2}\\/\\d{2}\\/\\d{4})");	// REGEX for MMM/dd/YYYY
	private static Pattern pAct;
	private static Pattern pAmt = Pattern.compile("\\$(.*?)\\ ");

	public static Document parse(Date d, String text) {
		Document doc = new Document(d);
		String[] transText = findTransactions(text);

//		System.out.println(text);

		// need to filter DATE DESCRIPTION CATEGORY AMOUNT BALANCE and extraneous stuff
		// for pages
		// if not have MMM - dd || Debit Card Purchase (trailing) then reject.

		for (int i = 0; i < transText.length; i++) {

//			System.out.println(transText[i]);
			System.out.println(isValidLine(transText[i]));

//			Date date = getDate(transText[i]);
//			String activity = getActivity(date, transText[i]);
//			double amt = getAmount(transText[i]);
//			Category category = getCategory(amt);
//			Transaction t = new Transaction(date, activity, category, amt);
//			doc.addTransaction(t);
		}
		// Aug 31, 2019
//		System.out.println(doc);

		return doc;
	}

	private static String[] findTransactions(String text) {
		Matcher m = pt.matcher(text);
		String[] transText = null;

		while (m.find())
			transText = text.substring(m.start(), m.end() - 1).split("\n");

		return transText;
	}

	private static boolean isValidLine(String text) {
		if (text.length() > 6) {
			System.out.print(text.substring(0,6));
			
			Pattern pd = Pattern.compile("^([a-zA-Z]{3})(\\s{1})([0-9]{1,2})");
			Matcher m = pd.matcher(text.substring(0,6).trim());
			
			if (m.matches())
				return true;
		}
		
		return false;
	}

//	private static Date getDate(String text) {
//		Matcher m = pd.matcher(text);
//		
//		while (m.find())
//			return createDate(text.substring(m.start(), m.end()));
//		
//		return null;
//	}
//	
//	private static String getActivity(Date date, String text) {
//		pAct = Pattern.compile("([^" + new SimpleDateFormat("MM/dd/yyyy").format(date) + "]+)");
//		Matcher m = pAct.matcher(text);
//		
//		while (m.find())
//			return text.substring(m.start(), m.end() - 1).trim();
//		
//		return null;
//	}
//	
//	private static double getAmount(String text) {
//		Matcher m = pAmt.matcher(text);
//		while (m.find()) {
//			String amt = text.substring(m.start(), m.end() - 1).replace("$", "");
//			return amt.contains("(") ? Double.parseDouble(amt.replaceAll("[()]", "")) * -1 : Double.parseDouble(amt);
//		}
//		
//		return 0;
//	}

}
