package com.han.simulator.servers;

import java.awt.Desktop;
import java.net.URI;

import org.apache.catalina.startup.Bootstrap;

public class Tomcat {

	static Bootstrap bootstrap = new Bootstrap();
	static Thread tomcatRun = new Thread();

	public static void main(String[] args) throws Exception {
		synchronized(tomcatRun){
			runTomcat();
			if(Desktop.isDesktopSupported())
			{
			  Desktop.getDesktop().browse(new URI("http://localhost:8080/guvnor"));
			}
			tomcatRun.wait();
		}
	}

	public static void runTomcat() {
		bootstrap = new Bootstrap();
		bootstrap.setCatalinaHome("C:/Users/ndizigiye/workspace/Tomcat7");//Tomcat.class.getResource("/Tomcat7").getPath());
		try {
			bootstrap.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Tomcat");
		}
	}

	public static void stopTomcat() {
		try {
			bootstrap.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
