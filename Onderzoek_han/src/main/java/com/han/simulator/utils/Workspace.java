package com.han.simulator.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

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
			String transcriptSource = Workspace.class.getResource("Meldingen.zip").getPath();
			ZipFile zipFile;
			try {
				zipFile = new ZipFile(transcriptSource);
				zipFile.extractAll(TranscriptsDir.getPath());
			} catch (ZipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		InterfacesDir = new File(SimulatorDir.getPath() + "/Interfaces");
		if (!InterfacesDir.exists()) {
			InterfacesDir.mkdir();
			/*Sample prototype*/
//			String prototypeSource = Workspace.class.getResource("/Prototype.zip").getPath();
//			ZipFile zipFile;
//			try {
//				zipFile = new ZipFile(prototypeSource);
//				zipFile.extractAll(InterfacesDir.getPath());
//			} catch (ZipException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		}
	}
	
	public static void InitJustInMind(){
		JustInMind.CopyPrototype(InterfacesDir+"/"+MainScreenController.getPrototype());
		JustInMind.RenameInterfaces();
		prototypeName = MainScreenController.getPrototype();
		
	}
	public static void InitGuvnor() throws ZipException{

		TomcatDir = new File(SimulatorDir.getPath() + "/Tomcat");
		if (!TomcatDir.exists()) {
			TomcatDir.mkdir();
			String tomcatSource = Workspace.class.getResource("Tomcat.zip").getPath();
				ZipFile zipFile = new ZipFile(tomcatSource);
				zipFile.extractAll(TomcatDir.getPath());
		}
	}
	/**
	 * Get a list of all present prototypes
	 * @return String[] - a list of prototype names
	 */
	public static ArrayList<String> listPrototypes(){
		
		ArrayList<String> prototypeNames = new ArrayList<String>();
		// TODO only dirs allowed
		File[] prototypesDirs = InterfacesDir.listFiles();
		for(File prototype : prototypesDirs ){
			prototypeNames.add(prototype.getName());
		}
		return prototypeNames;
	}
	
	public static ArrayList<String> listTranscripts(){
		// TODO only files allowed
		ArrayList<String> fileNames = new ArrayList<String>();
		File[] Transcripts = TranscriptsDir.listFiles();
		for(File transcript : Transcripts ){
			fileNames.add(transcript.getName());
		}
		return fileNames;
	}

}
