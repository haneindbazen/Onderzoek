package com.han.simulator.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import net.lingala.zip4j.exception.ZipException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import com.han.simulator.servers.*;
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
	private Button stopAlles;
	@FXML
	private Button installAlles;
	@FXML
	private ImageView waitImage;
	@FXML
	private ImageView errorImage;
	@FXML
	private Label statusLabel;
	@FXML
	private Label projectDir;
	@FXML
	private static Label errorLabel;
	@FXML
	private static Pane errorPanel;
	@FXML
	private static Pane installPanel;
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

	public static String getDelay() {
		String delayValue = delay.getText();
		return delayValue;
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

	public static void setError(final String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				errorPanel.setVisible(true);
				errorLabel.setText(text);
			}
		});
	}

	public void clearError() {
		Platform.runLater(new Runnable() {
			public void run() {
				errorPanel.setVisible(false);
			}
		});
	}

	public void clearText(final boolean clearAlsoStatus) {
		Platform.runLater(new Runnable() {
			public void run() {
				waitImage.setVisible(false);
				if (clearAlsoStatus) {
					statusLabel.setText("");
				}
			}
		});
	}

	/**
	 * Starts everything
	 */
	public void StartAlles() {
		Thread t = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				setText("configuring workspace directory...");
				try {
					Workspace.InitJustInMind();
				} catch (Exception e) {
					setError("Initializing interfaces failed, provide a valid prototype directory");
					clearText(true);
					this.stop();
				}
				setText("starting guvnor app...");
				Guvnor.Start();
				setText("starting simulator app...");
				Simulator.Start();
				Guvnor.Open();
				// Simulator.Open();
				setText("Everything started succefully!");
				clearText(false);
			}
		};

		if (getTranscript() == null || getPrototype() == null) {

			setError("Please choose a transcript file and a prototype");
		} else if (!getTranscript().endsWith(".txt")) {
			setError("Only txt transcripts are allowed");
		} else {
			clearError();
			t.start();
			startAlles.setDisable(true);
			stopAlles.setDisable(false);
		}
	}

	/**
	 * Stops everything
	 */
	public void StopAlles() {
		Thread t = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				setText("stopping guvnor...");
				try {
					// Guvnor.Stop();
				} catch (Exception e) {
					setError("Stopping guvnor failed");
					clearText(true);
					this.stop();
				}
				setText("stopping simulator server...");
				try {
					Simulator.Stop();
				} catch (Exception e) {
					setError("Stopping simulator server failed");
					this.stop();
				}
				setText("Everything stopped succefully!");
				clearText(false);
			}
		};
		clearError();
		t.start();
		startAlles.setDisable(false);
		stopAlles.setDisable(true);
	}

	/**
	 * Install for the first time use
	 */

	public void InstallAll() {
		Thread t = new Thread() {
			public void run() {
				setText("install program on disk please wait...");
				Workspace.Install();
				clearText(false);
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
		if (Workspace.isInstalled()) {
			installPanel.setVisible(true);
		} else {
			installPanel.setVisible(false);
			stopAlles.setDisable(true);
			startAlles.setDisable(true);
		}
		stopAlles.setDisable(true);
		Image ajaxLoader = new Image(getClass().getResourceAsStream(
				"ajax-loader.gif"));
		Image error = new Image(getClass().getResourceAsStream("error.png"));
		waitImage.setImage(ajaxLoader);
		errorImage.setImage(error);
//		projectDir.setText("Project directory : "
//				+ Workspace.SimulatorDir.getPath());
//		transcriptChooser.getItems().clear();
//		prototypeChooser.getItems().clear();
//		for (String transcriptName : Workspace.listTranscripts()) {
//			transcriptChooser.getItems().add(transcriptName);
//		}
//
//		for (String prototypeName : Workspace.listPrototypes()) {
//			prototypeChooser.getItems().add(prototypeName);
//		}

	}
}
