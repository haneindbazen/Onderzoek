package com.han.simulator.utils;

import java.io.IOException;
import java.util.ArrayList;
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

	public void PushEventToBrowser(Event e, String interfaceName,Associatie a1,Associatie a2,Associatie a3,Associatie a4,Associatie a5,Associatie a6,Associatie a7,Associatie a8,Associatie a9,Associatie a10) {
		System.out.println("start pushing");
		ArrayList<Associatie> associaties = new ArrayList<Associatie>();
		associaties.add(a1);associaties.add(a2);associaties.add(a3);associaties.add(a4);associaties.add(a5);associaties.add(a6);associaties.add(a7);associaties.add(a8);associaties.add(a9);associaties.add(a10);
		for (EchoMessageInbound mi : EventPusher.clients) {
			try {
				mi.sendMessage(e.getMessage() + "#" + interfaceName,associaties);
				System.out.println("message send");
			} catch (IOException e1) {
				EventPusher.clients.remove(mi);
			}
		}
	}
}
