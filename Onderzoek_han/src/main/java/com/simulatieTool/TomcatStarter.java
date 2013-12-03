package com.simulatieTool;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class TomcatStarter {
	
	static String webappDirLocation = "src/main/webapps/";
    static Tomcat tomcat = new Tomcat();

	/**
	 * Starting Tomcat server
	 * 
	 * @param tomcatLocatie
	 *            tomcat path
	 * @return false or true if startup was successful
	 */
	public static boolean Start(String tomcatLocatie){
		boolean startSuccessFull = false;
		
        String webPort = "9090";

        tomcat.setPort(Integer.valueOf(webPort));
        
        try {
        	tomcat.addWebapp("/guvnor", new File(tomcatLocatie+"\\webapps\\guvnor").getAbsolutePath());
        	tomcat.addWebapp("/home", new File(webappDirLocation).getAbsolutePath());
			tomcat.start();
			startSuccessFull = true;
		} catch (LifecycleException | ServletException e) {
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
		try {
			tomcat.stop();
			stopSuccessFull = true;
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stopSuccessFull;
	}

}
