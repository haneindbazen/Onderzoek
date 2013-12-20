package com.simulatieTool;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Simulator {

	/**
	 * Open the simulator app in the browser
	 */
	public static void Open() throws IOException, URISyntaxException {
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new
			URI("http://localhost:9090/simulator"));
		}
	}
}
