package com.han.simulator.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import com.han.simulator.servers.Guvnor;
import com.han.simulator.servers.Simulator;
import com.han.simulator.servers.TomcatStarter;
import com.simulatieTool.Webapp.*;

public class MainScreenController {

	@FXML private Button startSimulator;
	@FXML private Button startGuvnor;
	@FXML private Button stopGuvnor;
	@FXML private ImageView waitImage;
	@FXML private Label waitStartText;
	@FXML private Label waitStopText;
	@FXML private Button browseTomcat;
	@FXML private Button browseGuvnor;
	@FXML private TextField tomcatLocation;
	@FXML private TextField guvnorLocation;
	
	/**
	 * Start the guvnor app and open it in the browser
	 */
	public void StartGuvnor() {
		Thread t = new Thread() {
		    public void run() {
		    	StartGuvnorInternal();
		    }
		};
		t.start();
	}
	
	public void StopGunvorInternal(){
		stopGuvnor.setDisable(true);
		waitStopText.setVisible(true);
		stopGuvnor.setDisable(true);
		waitImage.setVisible(true);
		Guvnor.Stop();
		waitStopText.setVisible(false);
		waitImage.setVisible(false);
		startGuvnor.setDisable(false);
	}
	public void StopGuvnor(){
		Thread t = new Thread() {
		    public void run() {
		    	StopGunvorInternal();
		    }
		};
		t.start();
	}
	
	public void StartGuvnorInternal(){
		try {
			startGuvnor.setDisable(true);
			waitStartText.setVisible(true);
			waitImage.setVisible(true);
			Guvnor.Start();
			waitStartText.setVisible(false);
			waitImage.setVisible(false);
			stopGuvnor.setVisible(true);
			Guvnor.Open();
		} catch (IOException | URISyntaxException e) {
			startGuvnor.setDisable(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	public void BrowseTomcat(){
	}
	
	public void BrowseGuvnor(){
		
	}
	/**
	 * Start the main simulator app and open it in the browser
	 */
	public void StartSimulator() {
		Thread t = new Thread() {
		    public void run() {
		    	StartSimulatorInternal();
		    }
		};
		t.start();
	}
	
	public void StartSimulatorInternal(){
		startSimulator.setDisable(true);
		if (TomcatStarter.Start()) {
			try {
				Simulator.Open();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				startSimulator.setDisable(false);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


