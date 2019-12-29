package com.ngu_software.capital_one_sp;

import java.io.File;

import com.ngu_software.capital_one_sp.controller.Parser;


public class App {
	public static void main(String[] args) {
		new Parser(new File("./"));
	}
}
