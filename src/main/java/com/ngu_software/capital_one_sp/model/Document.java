package com.ngu_software.capital_one_sp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Document {
	 
	private Date date;
//	private double endBalance;
	
	private List<Transaction> transList;

	public Document(Date date) {
		this.date = date;
		transList = new ArrayList<>();
	}
	
	public void addTransaction(Transaction t) {
		transList.add(t);
	}

	@Override
	public String toString() {
		return "Document [date=" + date + "\ntransList=" + transList + "]";
	}
	
}
