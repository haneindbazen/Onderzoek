package com.simulatieTool.Webapp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

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
	protected StreamInbound createWebSocketInbound(String subProtocol,HttpServletRequest request) {
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
		
		public void senMessage(String message) throws IOException{
			getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
		}

		@Override
		protected void onTextMessage(CharBuffer msg) throws IOException {
			String message = msg.toString();
			System.out.println("client says: "+message.toString());
			try {
				Drools.Init(message.split("#")[1]);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			ArrayList<String> lines = TranscriptReader.GetLines(getClass().getResource("/meldingen.txt").getPath());
			for (String line:lines) {
				System.out.println(line);
				Drools.FireRules(new Event(line));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Drools.Close();
		}
	}
}