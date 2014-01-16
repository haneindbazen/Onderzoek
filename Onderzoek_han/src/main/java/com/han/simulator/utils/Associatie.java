package com.han.simulator.utils;

import java.io.IOException;
import java.util.HashMap;

import com.han.simulator.utils.EventPusher.EchoMessageInbound;

/**
* The Model for creating Rules in Guvnor
* @author Armand Ndizigiye
* @version 0.1
*/
public class Associatie {

	public String inhoud;
	public String tag;

	public Associatie(String message, String tag) {
		this.inhoud = message;
		this.tag = tag;
	}

	public String getMessage() {
		return inhoud;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setMessage(String message) {
		this.inhoud = message;
	}
	
	/**
	 * Push the event to the Browser
	 * @param e - the occured event
	 * @param interfaceName - the name of the interface for this event
	 * @throws IOException
	 */

	public void PushEventToBrowser(Associatie e, String interfaceName)throws IOException {
		for (EchoMessageInbound mi : EventPusher.clients) {
			mi.sendMessage(e.getMessage()+"#"+interfaceName);
		}

	}
	
	public void Test(HashMap<String,String> test){
		
	}
}
