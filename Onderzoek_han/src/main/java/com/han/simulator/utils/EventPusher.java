package com.han.simulator.utils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import com.han.simulator.ui.MainScreenController;

/**
 * The Websocket servlet that handles websocket traffics
 * 
 * @author Armand Ndizigiye
 * @version 0.1
 */

@SuppressWarnings("deprecation")
public class EventPusher extends WebSocketServlet {

	public static ArrayList<EchoMessageInbound> clients = new ArrayList<EchoMessageInbound>();

	private static final long serialVersionUID = 1L;
	private volatile int byteBufSize;
	private volatile int charBufSize;

	@Override
	public void init() throws ServletException {
		super.init();
		byteBufSize = getInitParameterIntValue("byteBufferMaxSize", 2097152);
		charBufSize = getInitParameterIntValue("charBufferMaxSize", 2097152);
	}

	public int getInitParameterIntValue(String name, int defaultValue) {
		String val = this.getInitParameter(name);
		int result;
		if (null != val) {
			try {
				result = Integer.parseInt(val);
			} catch (Exception x) {
				result = defaultValue;
			}
		} else {
			result = defaultValue;
		}

		return result;
	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		EchoMessageInbound mi = new EchoMessageInbound(byteBufSize, charBufSize);
		clients.add(mi);
		return mi;
	}

	public static class EchoMessageInbound extends MessageInbound {

		public EchoMessageInbound(int byteBufferMaxSize, int charBufferMaxSize) {
			super();
			setByteBufferMaxSize(byteBufferMaxSize);
			setCharBufferMaxSize(charBufferMaxSize);
		}

		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			getWsOutbound().writeBinaryMessage(message);

		}

		/**
		 * Send an event message to the browser
		 * 
		 * @param message
		 *            - the event message
		 * @throws IOException
		 */
		public void sendMessage(String message) throws IOException {
			getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
		}

		@Override
		protected void onTextMessage(CharBuffer msg) throws IOException {
			
			String[] message = msg.toString().trim().split("#");
			String guvnorLink = "";
			String command = message[0];
			try {
				guvnorLink = message[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				guvnorLink = "";
			}
			try {
				Drools.Start(guvnorLink);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}