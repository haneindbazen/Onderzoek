package com.simulatieTool;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Guvnor {

	public static void Start() throws IOException, URISyntaxException {
		
		if(Desktop.isDesktopSupported())
		{
		   //start the guvnor app
		  Desktop.getDesktop().browse(new URI("http://localhost:9090/guvnor"));
		  //start also our app
		  Desktop.getDesktop().browse(new URI("http://localhost:9090/home"));
		}
	}

	public static void Stop() {
		// TODO Auto-generated method stub
		
	}

}
