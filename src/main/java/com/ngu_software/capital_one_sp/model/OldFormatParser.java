package com.ngu_software.capital_one_sp.model;

import java.util.Date;

public class OldFormatParser {
	
	public OldFormatParser(Date d, String text) {
		Document doc = new Document(d);
		String transText = text.split("Opening Balance ")[1].split("Go mobile and bank on the fly.")[0];
		
//		String tt = text.split("?<=beginningstringname)(.*\n?)(?=endstringname")[0];
		System.out.println(transText);
	}

}
