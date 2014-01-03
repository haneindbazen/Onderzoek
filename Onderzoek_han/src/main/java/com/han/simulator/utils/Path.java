package com.han.simulator.utils;

/**
 * This gives paths to different programs
 * @author Armand Ndizigiye
 *
 */
public class Path {
	
	public static String INTERFACE = Path.class.getResource("/Simulator/interfaces").getPath();
	public static String SIMULATOR = Path.class.getResource("/Simulator").getPath();
	public String prototypeName = "";
	
	public Path(String prototypeName){
		this.prototypeName = prototypeName;
	}
	
	

}
