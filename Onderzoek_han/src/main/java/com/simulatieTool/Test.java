package com.simulatieTool;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.htmlcleaner.HtmlCleaner.*;

public class Test {

	public static void main(String[] args) throws Exception{
		
		File prototypesDir = new File("C:/Users/ndizigiye/Pictures/simulator/html/Prototype1");
		File homePrototypesDir = new File(Test.class.getResource("/interfaces/"+prototypesDir.getName()).getPath());

		if(!homePrototypesDir.exists()){
			homePrototypesDir.mkdir();
		}
		
		FileUtils.copyDirectory(prototypesDir, homePrototypesDir);
		//org.htmlcleaner.HtmlCleaner html = new org.htmlcleaner.HtmlCleaner();
		//html.clean(file);
	
	}
}
