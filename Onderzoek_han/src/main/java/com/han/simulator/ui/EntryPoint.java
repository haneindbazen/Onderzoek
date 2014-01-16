package com.han.simulator.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entrypoint class for the project,
 * it starts the Main UI
 * @author ndizigiye
 * @version 0.1
 */
public class EntryPoint extends Application {
	
	public Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
	        Scene scene = new Scene(root);
	        
	        //code for displaying maximized screen
	        
	        /* Screen screen = Screen.getPrimary();
	        Rectangle2D bounds = screen.getVisualBounds();
	        primaryStage.setX(bounds.getMinX());
	        primaryStage.setY(bounds.getMinY());
	        primaryStage.setWidth(bounds.getWidth());
	        primaryStage.setHeight(bounds.getHeight());*/
	        
	        primaryStage.setTitle("FXML Welcome");
	        primaryStage.setScene(scene);
	        primaryStage.show();//
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}