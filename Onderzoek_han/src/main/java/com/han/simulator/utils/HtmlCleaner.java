package com.han.simulator.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HtmlCleaner {
	
	// String url: Example: "C:/HtmlCleaner".
	public static void CleanDirectory(String url) throws IOException{
		File folder = new File(url);
		if(!folder.exists()){
			folder.mkdir();
		}
		for(File file : folder.listFiles()){
			if(file.isFile()){
				if(file.getName().endsWith(".html")){
					
					String filename = url + "/" + file.getName();
					
					BufferedReader br = new BufferedReader(new FileReader(filename));
				    try {
				        StringBuilder sb = new StringBuilder();
				        String line = br.readLine();

				        while (line != null) {
				            sb.append(line);
				            sb.append('\n');
				            line = br.readLine();
				        }
				        String everything = sb.toString();
				        String clean = HtmlCleaner.FilterHtml(everything);
				        String outputPath = url + "/Output";
				        
				        File outputFolder = new File(outputPath);
				        if(!outputFolder.exists()){
				        	outputFolder.mkdir();
				        }
				        
				        br.close();
				        
				        System.out.println("Creating: " + outputPath + "/" + file.getName());
				        PrintWriter writer = new PrintWriter(outputPath + "/" + file.getName(), "UTF-8");
				        writer.println(clean);
				        writer.close();
				        
				        File fileToRemove = new File(filename);
				        System.out.println("Deleting: " + filename);
				        fileToRemove.delete();
				        
				    } finally {
				        br.close();
				    }
					
				}
			}
		}
	}
	
	public static String FilterHtml(String h)
	{
		String oldHtml = h;

	    int firstIndex = 0;
	    int secondIndex = 0;

	    while (true)
	    {
	        // Also possible with the following regular expression: <script[^>]*(></script>|$)
	        firstIndex = oldHtml.indexOf("<script");
	        secondIndex = oldHtml.indexOf("</script>");

	        if (firstIndex < 1 || secondIndex < 1)
	            break;
	        //oldHtml = oldHtml.Remove(firstIndex, secondIndex - firstIndex + 9);
	        String s = oldHtml.substring(firstIndex, secondIndex + 9);
	        oldHtml = oldHtml.replace(s,"");
	    }

	    return oldHtml;
	}
}