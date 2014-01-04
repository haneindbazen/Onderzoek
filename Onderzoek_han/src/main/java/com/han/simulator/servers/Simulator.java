package com.han.simulator.servers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.han.simulator.utils.EventPusher;

/**
* This class starts and stops the embedded Tomcat server
* @author Armand Ndizigiye
* @version 0.1
*/
public class Simulator {
	
	static String webappDirLocation = Simulator.class.getResource("/Simulator").getPath();
    static Tomcat tomcat = new Tomcat();

	/**
	 * Starting Tomcat server
	 * This also configure also the websocket Servlet
	 * @see EventPusher
	 * @return false or true if startup was successful
	 */
	public static boolean Start(){
		boolean startSuccessFull = false;

        tomcat.setPort(9090);
        File base = new File(System.getProperty("java.io.tmpdir"));
        Context rootCtx = tomcat.addContext("", base.getAbsolutePath());
        try {
        	tomcat.addWebapp("/simulator", new File(webappDirLocation).getAbsolutePath());
        	Tomcat.addServlet(rootCtx, "eventPusher", new EventPusher());
        	rootCtx.addServletMapping("/pusher", "eventPusher");
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
	
	/**
	 * Open the simulator app in the browser
	 */
	public static void Open(){
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URL("http://localhost:9090/simulator").toURI());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}

}
