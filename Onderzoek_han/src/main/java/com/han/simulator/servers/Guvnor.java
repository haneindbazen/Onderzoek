package com.han.simulator.servers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.catalina.startup.Bootstrap;

import com.han.simulator.utils.Workspace;

/**
* This class starts and stops the guvnor app
* @author Armand Ndizigiye
* @version 0.1
*/
public class Guvnor {

	static Bootstrap bootstrap = new Bootstrap();
	static Thread tomcatRun = new Thread();

	/**
	 * Start the guvnor app in an installed Tomcat server
	 */
	public static void Start() {
		bootstrap = new Bootstrap();
		String tomcatDir = Workspace.TomcatDir.getPath();
		bootstrap.setCatalinaHome(tomcatDir);//"C:/Users/ndizigiye/workspace/Tomcat7");
		try {
			bootstrap.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Tomcat");
		}
	}

	/**
	 * Stop the guvnor app in an installed Tomcat server
	 */
	public static void Stop() {
		synchronized(tomcatRun){
			tomcatRun.notify();
			try {
				bootstrap.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void Open(){
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URL("http://localhost:8080/guvnor").toURI());
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
