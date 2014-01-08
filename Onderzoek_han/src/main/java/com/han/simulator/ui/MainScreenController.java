package com.han.simulator.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
public class MainScreenController implements Initializable {

	@FXML
	private Button startAlles;
	@FXML
	private ImageView waitImage;
	@FXML
	private Label statusLabel;
	@FXML
	private static ChoiceBox<String> transcriptChooser;
	@FXML
	private static ChoiceBox<String> prototypeChooser;
	@FXML
	public static TextField delay;

	public static String getTranscript() {
		String chosenTranscript = transcriptChooser.getValue();
		return chosenTranscript;
	}

	public static String getPrototype() {
		String chosenPrototype = prototypeChooser.getValue();
		return chosenPrototype;
	}

	public void setText(final String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				waitImage.setVisible(true);
				statusLabel.setVisible(true);
				statusLabel.setText(text);
			}
		});
	}

	public void clearText() {
		Platform.runLater(new Runnable() {
			public void run() {
				waitImage.setVisible(false);
				//statusLabel.setText("");
			}
		});
	}

	/**
	 * Starts everything
	 */
	public void StartAlles() {
		Thread t = new Thread() {
			public void run() {
				setText("configuring workspace directory...");
				Workspace.InitJustInMind();
				setText("initializing guvnor...");
				Workspace.InitGuvnor();
				setText("starting guvnor app...");
				Guvnor.Start();
				setText("starting simulator app...");
				Simulator.Start();
				Guvnor.Open();
				Simulator.Open();
				setText("Everything started succefully!");
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image ajaxLoader = new Image("file:"+getClass().getResource("/ajax-loader.gif").getPath());
		waitImage.setImage(ajaxLoader);
		Workspace.Init();
		transcriptChooser.getItems().clear();
		prototypeChooser.getItems().clear();
		for (String transcriptName : Workspace.listTranscripts()) {
			transcriptChooser.getItems().add(transcriptName);
		}

		for (String prototypeName : Workspace.listPrototypes()) {
			prototypeChooser.getItems().add(prototypeName);
		}

	}
}
