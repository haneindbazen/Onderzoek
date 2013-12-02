package com.simulatieTool;

import java.io.IOException;

import org.apache.catalina.startup.Bootstrap;

public class Tomcat {

	static Process tomcatProcess;
	static Bootstrap bootstrap = new Bootstrap();

	/**
	 * Starting Tomcat server
	 * 
	 * @param tomcatLocatie
	 *            tomcat path
	 * @return false or true if startup was successful
	 * @throws Exception 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static boolean Start(String tomcatLocatie){
		boolean startSuccessFull = false;
		bootstrap.setCatalinaHome(tomcatLocatie);
		
			try {
				bootstrap.start();
				startSuccessFull = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

		boolean stopSuccessFull = false;
		
		try{
		bootstrap.stop();
		stopSuccessFull = true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return stopSuccessFull;
	}

}
