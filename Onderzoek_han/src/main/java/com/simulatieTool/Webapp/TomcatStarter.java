package com.simulatieTool.Webapp;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * This class starts and stops the embedded Tomcat server
 * @author ndizigiye
 *
 */
public class TomcatStarter {
	
	static String webappDirLocation = "src/main/webapps/Simulator";
    static Tomcat tomcat = new Tomcat();

	/**
	 * Starting Tomcat server
	 * @return false or true if startup was successful
	 */
	public static boolean Start(){
		boolean startSuccessFull = false;

        tomcat.setPort(9090);
        
        try {
        	tomcat.addWebapp("/simulator", new File(webappDirLocation).getAbsolutePath());
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
	 * @return false or true if stop was successful
	 */
	public static boolean Stop() {

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
