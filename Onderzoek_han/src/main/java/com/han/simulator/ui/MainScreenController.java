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
import com.han.simulator.utils.Drools;
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
	private Button simulate;
	@FXML
	private Button simulatePauzeorResume;
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
	private static Pane simulatePanel;
	@FXML
	private static ChoiceBox<String> transcriptChooser;
	@FXML
	private static ChoiceBox<String> prototypeChooser;
	@FXML
	public static TextField delay;
	@FXML
	public TextField guvnorLink;

	private String guvnoradress;

	private Thread simulatorThread;

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
	public void StartAll() {
		Thread startThread = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				stopAlles.setDisable(true);
				startAlles.setDisable(true);
				setText("configuring workspace directory...");
				try {
					Workspace.InitJustInMind();
				} catch (Exception e) {
					setError("Initializing interfaces failed, provide a valid prototype directory");
					e.printStackTrace();
					clearText(true);
					stopAlles.setDisable(true);
					startAlles.setDisable(false);
					return;
				}
				setText("starting guvnor app...");
				// Guvnor.Start();
				setText("starting simulator app...");
				Simulator.Start();
				// Guvnor.Open();
				Simulator.Open();
				setText("Everything started succefully!");
				clearText(false);
				startAlles.setDisable(true);
				stopAlles.setDisable(false);
				simulatePanel.setDisable(false);
			}
		};

		if (getTranscript() == null || getPrototype() == null) {
			setError("Please choose a transcript file and a prototype");
		} else if (!getTranscript().endsWith(".txt")) {
			setError("Only txt transcripts are allowed");
		} else {
			clearError();
			startThread.start();
		}
	}

	/**
	 * Stops everything
	 */
	public void StopAll() {
		Thread stopThread = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				setText("stopping guvnor...");
				try {
					Guvnor.Stop();
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
		stopThread.start();
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
				setText("Program is now installed, press start to start!");
				clearText(false);
				afterInstall();
			}
		};
		t.start();
	}

	/**
	 * Show options
	 */
	public void afterInstall() {
		Platform.runLater(new Runnable() {
			public void run() {
				projectDir.setText("Project directory : "
						+ Workspace.SimulatorDir.getPath());
				transcriptChooser.getItems().clear();
				prototypeChooser.getItems().clear();
				for (String transcriptName : Workspace.listTranscripts()) {
					transcriptChooser.getItems().add(transcriptName);
				}

				for (String prototypeName : Workspace.listPrototypes()) {
					prototypeChooser.getItems().add(prototypeName);
				}

				installPanel.setVisible(false);
				startAlles.setDisable(false);
			}
		});
	}

	public void Simulate() {
		guvnoradress = guvnorLink.getText().trim();
	    simulatorThread = new Thread() {
			public void run() {
					Platform.runLater(new Runnable() {
						public void run() {
							simulate.setDisable(true);
							simulatePauzeorResume.setDisable(false);
						}
					});
					try{
						Platform.runLater(new Runnable() {
							public void run() {
								clearError();
							}
						});
						Drools.Start(guvnoradress);
					}
					catch(Exception e){
						Platform.runLater(new Runnable() {
							public void run() {
								simulate.setDisable(false);
								simulatePauzeorResume.setDisable(true);
							}
						});
						return;
					}
					Platform.runLater(new Runnable() {
						public void run() {
							simulate.setDisable(false);
							simulatePauzeorResume.setDisable(true);
						}
					});
			}
		};
		simulatorThread.start();
	}

	public void SimulatePauzeorResume() {
		String buttontext = simulatePauzeorResume.getText();
		if (buttontext.equals("Pauze")) {
			simulatorThread.suspend();
			simulatePauzeorResume.setText("Resume");
		} else {
			simulatorThread.resume();
			simulatePauzeorResume.setText("Pauze");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (Workspace.isInstalled()) {
			installPanel.setVisible(false);
			afterInstall();
		} else {
			installPanel.setVisible(true);
			stopAlles.setDisable(true);
			startAlles.setDisable(true);
		}
		stopAlles.setDisable(true);
		Image ajaxLoader = new Image(getClass().getResourceAsStream(
				"ajax-loader.gif"));
		Image error = new Image(getClass().getResourceAsStream("error.png"));
		waitImage.setImage(ajaxLoader);
		errorImage.setImage(error);

	}
}
