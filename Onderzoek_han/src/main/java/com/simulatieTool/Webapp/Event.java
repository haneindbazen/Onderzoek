package com.simulatieTool.Webapp;

import java.io.IOException;
import java.nio.CharBuffer;

import com.simulatieTool.Webapp.EventPusher.EchoMessageInbound;

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

	public void PushEventToBrowser(Event e, String interfaceName)
			throws IOException {
		for (EchoMessageInbound mi : EventPusher.clients) {
			mi.senMessage(e.getMessage()+"#"+interfaceName);
		}

	}
}
