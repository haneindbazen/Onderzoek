package com.han.simulator.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	public static File TempInterfacesDir;
	public static File AppDir;
	public static File currentTranscript;
	public static File currentPrototype;
	public static String prototypeName;
	public static String userDir = System.getProperty("user.home");

	
	/**
	 * Check whether the program is installed
	 * @return {@code true} if the program is already installed else {@code false}
	 */
	public static boolean isInstalled(){
		SimulatorDir = new File(userDir + "/" + "com.han.simulator");
		TomcatDir = new File(SimulatorDir.getPath() + "/Tomcat");
		TranscriptsDir = new File(SimulatorDir.getPath() + "/Transcript");
		InterfacesDir = new File(SimulatorDir.getPath() + "/Interfaces");
		AppDir  = new File(SimulatorDir.getPath() + "/App");
		if(SimulatorDir.exists() && TomcatDir.exists() && AppDir .exists()){
			return true;
		}
		return false;
	}
	/**
	 * Install files on the user Disk for first use
	 * 
	 */
	public static void Install(){
			SimulatorDir = new File(userDir + "/" + "com.han.simulator");
			AppDir = new File(SimulatorDir.getPath() + "/App");
			TomcatDir = new File(SimulatorDir.getPath() + "/Tomcat");
			TranscriptsDir = new File(SimulatorDir.getPath() + "/Transcript");
			InterfacesDir = new File(SimulatorDir.getPath() + "/Interfaces");
			TempInterfacesDir = new File(SimulatorDir.getPath() + "/App/interfaces");
			if (!SimulatorDir.exists()) {
				SimulatorDir.mkdir();
			}
			if (!AppDir.exists()) {
				AppDir.mkdir();
			}
			if (!TomcatDir.exists()) {
				TomcatDir.mkdir();
			}
			if (!TranscriptsDir.exists()) {
				TranscriptsDir.mkdir();
			}
			if (!InterfacesDir.exists()) {
				InterfacesDir.mkdir();
			}
			if (!TempInterfacesDir.exists()) {
				TempInterfacesDir.mkdir();
			}
			
		//install app dir and temp interfaces dir
			InputStream IndexSource = Workspace.class.getResourceAsStream("/com/han/simulator/servers/App/index.jsp");
			InputStream WebSocketSource = Workspace.class.getResourceAsStream("/com/han/simulator/servers/App/websocket.jsp");
			InputStream JsSource = Workspace.class.getResourceAsStream("/com/han/simulator/servers/App/default.js");
			InputStream placeHolderSource = Workspace.class.getResourceAsStream("/com/han/simulator/servers/App/interfaces/placeholder");
			CopyFileToUserDir(IndexSource,AppDir.getPath()+"/index.jsp");
			CopyFileToUserDir(WebSocketSource,AppDir.getPath()+"/websocket.jsp");
			CopyFileToUserDir(JsSource,AppDir.getPath()+"/default.js");
			CopyFileToUserDir(placeHolderSource,TempInterfacesDir.getPath()+"/placeholder.txt");
			
		//install guvnor config file
		InputStream configSource = Workspace.class.getResourceAsStream("/com/han/simulator/utils/guvnor.xml");
		CopyFileToUserDir(configSource,SimulatorDir+ "/guvnor.xml");

			//install tomcat and guvnor
			InputStream tomcatSource = Workspace.class.getResourceAsStream("/com/han/simulator/utils/Tomcat.zip");
			CopyFileToUserDir(tomcatSource, TomcatDir.getPath()+ "/Tomcat.zip");
			ZipFile tomcatzip;
			try {
				tomcatzip = new ZipFile(TomcatDir+"/Tomcat.zip");
				tomcatzip.extractAll(TomcatDir.getPath());
			} catch (ZipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//install sample transcript
			InputStream transcriptSource = Workspace.class.getResourceAsStream("/com/han/simulator/utils/Meldingen.zip");
			CopyFileToUserDir(transcriptSource, TranscriptsDir.getPath()+ "/Meldingen.zip");
			ZipFile transcriptZip;
			try {
				transcriptZip= new ZipFile(TranscriptsDir.getPath()+ "/Meldingen.zip");
				transcriptZip.extractAll(TranscriptsDir.getPath());
				new File(TranscriptsDir.getPath()+ "/Meldingen.zip").delete();
			} catch (ZipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//install sample prototype
			InputStream prototypeSource = Workspace.class.getResourceAsStream("/com/han/simulator/utils/Prototype.zip");
			CopyFileToUserDir(prototypeSource, InterfacesDir+"/Prototype.zip");
			 ZipFile prototypeZip;
			 try {
				 prototypeZip = new ZipFile(InterfacesDir.getPath()+"/Prototype.zip");
				 prototypeZip.extractAll(InterfacesDir.getPath());
				 new File(InterfacesDir.getPath()+"/Prototype.zip").delete();
			 } catch (ZipException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 }
	}

	public static void InitJustInMind() {
		JustInMind.CopyPrototype(InterfacesDir + "/"
				+ MainScreenController.getPrototype());
		JustInMind.RenameInterfaces();
		prototypeName = MainScreenController.getPrototype();

	}

	public static void CopyFileToUserDir(InputStream i, String destination) {
		
		try {
			// write the inputStream to a FileOutputStream
			File destFile = new File(destination);
			if(!destFile.exists()) destFile.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(destFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = i.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
			System.out.println("Written to: "+destFile.getPath());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get a list of all present prototypes
	 * 
	 * @return String[] - a list of prototype names
	 */
	public static ArrayList<String> listPrototypes() {

		ArrayList<String> prototypeNames = new ArrayList<String>();
		// TODO only dirs allowed
		File[] prototypesDirs = InterfacesDir.listFiles();
		for (File prototype : prototypesDirs) {
			prototypeNames.add(prototype.getName());
		}
		return prototypeNames;
	}

	public static ArrayList<String> listTranscripts() {
		// TODO only files allowed
		ArrayList<String> fileNames = new ArrayList<String>();
		File[] Transcripts = TranscriptsDir.listFiles();
		for (File transcript : Transcripts) {
			fileNames.add(transcript.getName());
		}
		return fileNames;
	}

}
