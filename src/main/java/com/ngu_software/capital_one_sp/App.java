package com.ngu_software.capital_one_sp;

import java.io.File;

import com.ngu_software.capital_one_sp.export.Excel;
import com.ngu_software.capital_one_sp.parse.Parser;


public class App {
	public static void main(String[] args) {
		Parser p = new Parser(new File("./"));
//		Excel.export(p.getDocList());
		
	}
}
