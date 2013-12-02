package com.simulatieTool;

import java.io.IOException;

public class Tomcat {

	static Process tomcatProcess;

	/**
	 * Starting Tomcat server
	 * 
	 * @param tomcatLocatie tomcat path
	 * @return false or true if startup was successful
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static boolean Start(String tomcatLocatie){
		boolean startSuccessFull = false;
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
		
		boolean stopSuccessFull = false; 
		
		return stopSuccessFull;
	}

}
