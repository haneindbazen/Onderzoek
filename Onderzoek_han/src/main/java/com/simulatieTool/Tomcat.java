package com.simulatieTool;

import java.awt.Desktop;
import java.net.URI;

import org.apache.catalina.startup.Bootstrap;

public class Tomcat {

	static Bootstrap bootstrap = new Bootstrap();
	static Thread tomcatRun = new Thread();

	public static void main(String[] args) throws Exception {
		synchronized(tomcatRun){
			runTomcat();
			tomcatRun.wait();
		}
	}

	public static void runTomcat() {
		bootstrap = new Bootstrap();
		bootstrap.setCatalinaHome(Tomcat.class.getResource("/Tomcat7").getPath());
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
