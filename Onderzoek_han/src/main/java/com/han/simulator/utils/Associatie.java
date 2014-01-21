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

	public String getInhoud() {
		return inhoud;
	}

	public void setInhoud(String inhoud) {
		this.inhoud = inhoud;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
