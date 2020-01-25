package com.ngu_software.capital_one_sp.parse.debit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ngu_software.capital_one_sp.model.Category;

public abstract class FormatParser {
	
	protected static final Date createDate(String date) {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected static final Category getCategory(double amount) {
		return amount < 0 ? Category.CREDIT : Category.DEBIT;
	}

}
