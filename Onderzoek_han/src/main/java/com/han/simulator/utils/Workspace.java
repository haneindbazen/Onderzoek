package com.han.simulator.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.han.simulator.ui.MainScreenController;

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
	public static String prototypeName;
	/**
	 * Initialize the simulator working space
	 * by creating needed directories
	 */
	public static void Init() {

		String userDir = System.getProperty("user.home");
		try {
			SimulatorDir = new File(userDir + "/" + "com.han.simulator");
		} catch (NullPointerException | IllegalArgumentException e) {
			SimulatorDir.mkdir();
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
	
	public static void InitJustInMind(){
		JustInMind.CopyPrototype(InterfacesDir+"/"+MainScreenController.getPrototype());
		JustInMind.RenameInterfaces();
		prototypeName = MainScreenController.getPrototype();
		
	}
	public static void InitGuvnor(){

		File currentTomcatDir = new File(Workspace.class
				.getResource("/Tomcat7").getPath());
		
		TomcatDir = new File(SimulatorDir.getPath() + "/Tomcat");
		if (!TomcatDir.exists()) {
			try {
				FileUtils.copyDirectory(currentTomcatDir, TomcatDir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Get a list of all present prototypes
	 * @return String[] - a list of prototype names
	 */
	public static ArrayList<String> listPrototypes(){
		
		ArrayList<String> prototypeNames = new ArrayList<String>();
		
		// TODO only dirs allowed
		Collection<File> prototypesDirs = FileUtils.listFilesAndDirs(InterfacesDir,
				TrueFileFilter.TRUE, TrueFileFilter.TRUE);
		ArrayList<File> prototypesDirsList = new ArrayList<File>(prototypesDirs);
		
		for(File prototype : prototypesDirsList ){
			prototypeNames.add(prototype.getName());
		}
		
		// TODO lazy solution
		prototypeNames.remove(0);
		return prototypeNames;
	}
	
	public static ArrayList<String> listTranscripts(){
		
		ArrayList<String> fileNames = new ArrayList<String>();
		
		// TODO only files allowed
		Collection<File> Transcripts = FileUtils.listFiles(TranscriptsDir,
				TrueFileFilter.TRUE, TrueFileFilter.TRUE);
		ArrayList<File> TranscriptsList = new ArrayList<File>(Transcripts);
		for(File transcript : TranscriptsList ){
			fileNames.add(transcript.getName());
		}
		return fileNames;
	}

}
