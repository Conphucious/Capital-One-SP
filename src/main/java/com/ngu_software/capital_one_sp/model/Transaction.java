package com.ngu_software.capital_one_sp.model;

import java.util.Date;

public class Transaction {
	
	private Date date;
	private String description;
	private Category category;
	private double amount;
	
	public Transaction(Date date, String description, Category category, double amount) {
		this.date = date;
		this.description = description;
		this.category = category;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [date=" + date + ", description=" + description + ", category=" + category + ", amount="
				+ amount + "]";
	}

}
