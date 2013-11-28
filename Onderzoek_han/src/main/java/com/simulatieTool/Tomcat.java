package com.simulatieTool;

import java.io.IOException;

public class Tomcat {

	static Process tomcatProcess;

	/**
	 * Starting Tomcat server
	 * 
	 * @param tomcatLocatie
	 *            tomcat path
	 * @return false or true if startup was successful
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static boolean Start(String tomcatLocatie) throws IOException, InterruptedException {
		
		boolean startSuccessFull = false; 
		try{
			String command = tomcatLocatie+"\\bin\\startup.bat";
			String c = "cmd.exe";
			tomcatProcess = Runtime.getRuntime().exec(c);
			tomcatProcess.waitFor();
			
			startSuccessFull = true;
		}
		catch(Exception e){
			startSuccessFull = false;
		}
		
		return startSuccessFull;
	}

	/**
	 * Stopping Tomcat server
	 * 
	 * @param tomcatLocatie
	 *            tomcat path
	 * @return false or true if stop was successful
	 */
	public static boolean Stop(String tomcatLocatie) {
		return false;
	}

}
