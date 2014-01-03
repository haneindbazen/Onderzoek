package com.han.simulator.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 
 * @author ndizigiye
 *
 */
public class Transcript {

	private static String TRANSCRIPT = "meldingen.txt";
	
	public static ArrayList<String> Read(String transcriptPath) throws IOException{
		
		  FileInputStream fstream = new FileInputStream(transcriptPath);
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  ArrayList<String> lines = new ArrayList<String>();
		  String line;
		  while ((line = br.readLine()) != null)   {
		  lines.add(line);
		  }
		  br.close();
		return lines;
	}
}
