package com.simulatieTool;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.codehaus.cargo.container.InstalledLocalContainer;
import org.codehaus.cargo.container.configuration.LocalConfiguration;
import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployable.WAR;
import org.codehaus.cargo.container.property.ServletPropertySet;
import org.codehaus.cargo.container.tomcat.Tomcat7xInstalledLocalContainer;
import org.codehaus.cargo.container.tomcat.Tomcat7xStandaloneLocalConfiguration;

public class Guvnor {

	private static Deployable war;
	private static LocalConfiguration configuration = new Tomcat7xStandaloneLocalConfiguration("src/main/webapps/guvnor");
	private static InstalledLocalContainer container =  new Tomcat7xInstalledLocalContainer(configuration);
	private static String tomcatLocation;
	
	public static void Open() throws IOException, URISyntaxException {
		
		if(Desktop.isDesktopSupported())
		{
		   //start the guvnor app
		  Desktop.getDesktop().browse(new URI("http://localhost:9091/guvnor"));
		}
	}

	public static void Start() {
		war = new WAR("C:/Users/ndizigiye/workspace/guvnor5.war");
		tomcatLocation = "C:/Users/ndizigiye/workspace/Tomcat7";
		configuration.setProperty(ServletPropertySet.PORT, "9091");
		configuration.addDeployable(war);
		container.setHome(tomcatLocation);
		container.start();
		System.out.println("guvnor started");
	}
	
	public static void Stop() {
		container.stop();
		
	}

}
