package com.han.simulator.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.han.simulator.ui.MainScreenController;

/**
 * This class is responsible for configuring and starting the Drools Engine.
 * 
 * @author Armand Ndizigiye
 * @version 0.1
 */
public class Drools {

	static KnowledgeBase kbase;
	static StatefulKnowledgeSession ksession;
	static KnowledgeRuntimeLogger logger;

	/**
	 * Initialize the Drools Engine and fetch the rules from Guvnor
	 * 
	 * @param URL
	 *            - The URL String to the guvnor rules
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void Init(String URL) throws TransformerException,
			ParserConfigurationException, IOException, SAXException, Exception {
		setRemoteAddress(URL);
		// readKnowledgeBase() for reading a local drl file
		try{
		kbase = readRemoteKnowledgeBase();
		}
		catch(RuntimeException e){
		return;
		}
		ksession = kbase.newStatefulKnowledgeSession();
		logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
	}

	/**
	 * Start the Drools Engine based on Rules
	 * 
	 * @return true if startup was successful else false
	 * @see FireRules
	 * @param guvnorLink
	 * @throws IOException
	 */
	public static void Start(String guvnorLink) throws Exception {
		try {
			if (guvnorLink != null && !guvnorLink.equals("")) {
				try{
				Drools.Init(guvnorLink);
				}
				catch(RuntimeException e){
					MainScreenController.setError("Please provide a valid Guvnor link");
					return;
					}
			} else {
				MainScreenController.setError("Please provide a valid Guvnor link");
				throw new Exception("not a valid link");
			}
		} catch (Exception e1) {
			MainScreenController.setError("Please provide a valid Guvnor link");
			e1.printStackTrace();
			throw new Exception("not a valid link");
		}
		String transcriptPath = Workspace.TranscriptsDir + "/"
				+ MainScreenController.getTranscript();
		ArrayList<String> lines = new ArrayList<String>();
		try {
			lines = Transcript.Read(new File(transcriptPath).getPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (String line : lines) {
			System.out.println(line);
			Drools.FireRules(new Event(line));
			long delay;
			try {
				delay = Integer.parseInt(MainScreenController.getDelay()) * 1000;
			} catch (NumberFormatException e) {
				delay = 5000;
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Drools.Close();
	}

	/**
	 * This fires all the Rules, each event at a time using a given interval
	 * 
	 * @param event
	 *            - The event that occurs
	 * @param interval
	 *            - the interval between events
	 */
	public static void FireRules(Event event) {
		try {
			ksession.insert(event);
			ksession.fireAllRules();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Close the Drools Engine
	 */
	public static void Close() {
		logger.close();
	}

	/**
	 * Reading rules from Guvnor
	 * 
	 * @return KnowledgeBase - the Guvnor knowledge Base
	 */
	private static KnowledgeBase readRemoteKnowledgeBase() {
		KnowledgeAgentConfiguration kaconf = KnowledgeAgentFactory
				.newKnowledgeAgentConfiguration();
		kaconf.setProperty("drools.agent.scanDirectories", "false");
		KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent(
				"test agent", kaconf);
		kagent.applyChangeSet(ResourceFactory.newFileResource(new File(
				Workspace.SimulatorDir + "/guvnor.xml")));
		return kagent.getKnowledgeBase();
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"),
				ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}

	/**
	 * Set the remote address in the config xml for Guvnor
	 * 
	 * @param URL
	 *            - The URL String to the Guvnor rules
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void setRemoteAddress(String URL) throws SAXException,
			IOException, ParserConfigurationException, TransformerException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(Workspace.SimulatorDir + "/guvnor.xml");
		Node resource = doc.getElementsByTagName("resource").item(0);
		NamedNodeMap attr = resource.getAttributes();
		attr.getNamedItem("source").setNodeValue(URL);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource src = new DOMSource(doc);
		StreamResult result = new StreamResult(Workspace.SimulatorDir
				+ "/guvnor.xml");
		transformer.transform(src, result);
	}

}
