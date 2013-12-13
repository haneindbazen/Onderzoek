package com.simulatieTool;

import java.io.BufferedReader;
import java.io.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EventPusher {
	
	private static String TRANSCRIPT;
	
	public static ArrayList<String> Push(String transcriptPath) throws IOException{
		
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
	
	public static ArrayList<String> Push() throws IOException{
		
	
		  ArrayList<String> lines = new ArrayList<String>();
	return null;
	}

}
