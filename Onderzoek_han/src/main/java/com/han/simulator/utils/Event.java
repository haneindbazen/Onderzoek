package com.han.simulator.utils;

import java.io.IOException;
import java.util.HashMap;

import com.han.simulator.utils.EventPusher.EchoMessageInbound;

/**
 * The Model for creating Rules in Guvnor
 * 
 * @author Armand Ndizigiye
 * @version 0.1
 */
public class Event {

	public String message;

	public Event(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Push the event to the Browser
	 * 
	 * @param e
	 *            - the occured event
	 * @param interfaceName
	 *            - the name of the interface for this event
	 * @throws IOException
	 */

	public void PushEventToBrowser(Event e, String interfaceName) {
		for (EchoMessageInbound mi : EventPusher.clients) {
			try {
				mi.sendMessage(e.getMessage() + "#" + interfaceName);
				System.out.println("message send");
			} catch (IOException e1) {
				EventPusher.clients.remove(mi);
			}
		}
	}
}
