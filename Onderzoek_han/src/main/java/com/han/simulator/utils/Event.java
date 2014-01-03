package com.han.simulator.utils;

import java.io.IOException;
import java.nio.CharBuffer;

import com.han.simulator.utils.EventPusher.EchoMessageInbound;

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
	
	public boolean contains(String a,String b, String c){
		
		boolean contains = false;
		if(message.contains(a) || message.contains(b) || message.contains(c)){
			contains = true;
		}
		return contains;
	}
	
}
