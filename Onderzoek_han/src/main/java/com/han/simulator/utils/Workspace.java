package com.han.simulator.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * This class initialize directories and files for the simulator
 * 
 * @author ndizigiye
 * 
 */
public class Workspace {

	public static File SimulatorDir;
	public static File TomcatDir;
	public static File TranscriptsDir;
	public static File InterfacesDir;
	public static File currentTranscript;
	public static File currentPrototype;
	/**
	 * Initialize the simulator working space
	 * by creating needed directories
	 */
	public static void Init() {

		String userDir = System.getProperty("user.home");
		File currentTomcatDir = new File(Workspace.class
				.getResource("/Tomcat7").getPath());
		try {
			SimulatorDir = new File(userDir + "/" + "com.han.simulator");
		} catch (NullPointerException | IllegalArgumentException e) {
			SimulatorDir.mkdir();
		}

		TomcatDir = new File(SimulatorDir.getPath() + "/Tomcat");
		if (!TomcatDir.exists()) {
			try {
				FileUtils.copyDirectory(currentTomcatDir, TomcatDir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		TranscriptsDir = new File(SimulatorDir.getPath() + "/Transcript");
		if (!TranscriptsDir.exists()) {
			TranscriptsDir.mkdir();
		}
		
		InterfacesDir = new File(SimulatorDir.getPath() + "/Interfaces");
		if (!InterfacesDir.exists()) {
			InterfacesDir.mkdir();
		}
	}
	/**
	 * Get a list of all present prototypes
	 * @return String[] - a list of prototype names
	 */
	public static ArrayList<File> listPrototypes(){
		
		// TODO only dirs allowed
		Collection<File> prototypesDirs = FileUtils.listFiles(InterfacesDir,
				TrueFileFilter.TRUE, TrueFileFilter.TRUE);
		ArrayList<File> prototypesDirsList = new ArrayList<File>(prototypesDirs);
		
		return prototypesDirsList;
	}
	
	public static ArrayList<File> listTranscripts(){
		// TODO only files allowed
		Collection<File> TranscriptsDirs = FileUtils.listFiles(TranscriptsDir,
				TrueFileFilter.TRUE, TrueFileFilter.TRUE);
		ArrayList<File> TranscriptsDirsList = new ArrayList<File>(TranscriptsDirs);
		return TranscriptsDirsList;
	}

}
