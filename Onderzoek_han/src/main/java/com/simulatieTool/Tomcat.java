package com.simulatieTool;

import java.awt.Desktop;
import java.net.URI;

import org.apache.catalina.startup.Bootstrap;

public class Tomcat {
	
	static Bootstrap bootstrap=new Bootstrap();
	
	public static void main(String[] args) throws Exception{
		runTomcat();
		//Desktop.getDesktop().browse(new URI("http://localhost:8080/manager"));
	}
	
	public static void runTomcat() {
	    bootstrap = new Bootstrap();
	    bootstrap.setCatalinaHome("C:/Users/ndizigiye/workspace/Tomcat7");
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
