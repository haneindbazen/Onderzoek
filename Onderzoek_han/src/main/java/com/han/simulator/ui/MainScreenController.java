package com.han.simulator.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javafx.application.Platform;
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
import com.han.simulator.servers.Simulator;
import com.han.simulator.utils.Workspace;

/**
 * Controller class for MainScreen.fxml
 * 
 * @author ndizigiye
 * @version 0.1
 */
public class MainScreenController {

	@FXML
	private Button startAlles;
	@FXML
	private ImageView waitImage;
	@FXML
	private Label statusLabel;

	/**
	 * Starts everything
	 */
	
	public void setText(final String text){
		Platform.runLater(new Runnable(){
			public void run() {
				waitImage.setVisible(true);
				statusLabel.setVisible(true);
				statusLabel.setText(text);
				statusLabel.setText("");
			}});
	}
	
	public void clearText(){
		Platform.runLater(new Runnable(){
			public void run() {
				waitImage.setVisible(false);
				statusLabel.setText("");
			}});
	}

	public void StartAlles() {
		Thread t = new Thread() {
			public void run() {
				setText("configuring workspace directory...");
				Workspace.Init();
				Guvnor.Start();
				Guvnor.Open();
				Simulator.Start();
				Simulator.Open();
				clearText();
			}
		};
		t.start();
	}

	/**
	 * Start guvnor in a new thread
	 */
	public void StartGuvnor() {
		Thread t = new Thread() {
			public void run() {
				StartGuvnorInternal();
			}
		};
		t.start();
	}

	public void StopGunvorInternal() {
	}

	/**
	 * Stop the guvnor app
	 */
	public void StopGuvnor() {
		Thread t = new Thread() {
			public void run() {
				StopGunvorInternal();
			}
		};
		t.start();
	}

	public void StartGuvnorInternal() {
	}

	/**
	 * Start the simulator in a new thread
	 */
	public void StartSimulator() {
		Thread t = new Thread() {
			public void run() {
				StartSimulatorInternal();
			}
		};
		t.start();
	}

	public void StartSimulatorInternal() {
	}
}
