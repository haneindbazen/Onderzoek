package com.han.simulator.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BrowseController implements Initializable {

	@FXML
	private Pane guvnorPanel;
	@FXML
	private AnchorPane simulatorPanel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(new Runnable() {
			public void run() {
				WebView view = new WebView();
				WebEngine engine = view.getEngine();
				engine.load("http://localhost:9090/simulator");
				guvnorPanel.getChildren().add(view);
			}
		});

	}
}
