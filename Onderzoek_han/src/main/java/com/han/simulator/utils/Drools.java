package com.han.simulator.utils;

import java.io.File;
import java.io.IOException;

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

/**
* This class is responsible for configuring and starting 
* the Drools Engine.
* @author Armand Ndizigiye
* @version 0.1
*/
public class Drools {

	static KnowledgeBase kbase;
	static StatefulKnowledgeSession ksession;
	static KnowledgeRuntimeLogger logger;

	/**
	 * Initialize the Drools Engine and fetch
	 * the rules from Guvnor
	 * @param URL - The URL String to the guvnor rules
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void Init(String URL) throws TransformerException,ParserConfigurationException,IOException,SAXException,Exception{
		setRemoteAddress(URL);
		kbase = readKnowledgeBase();//readRemoteKnowledgeBase();
		ksession = kbase.newStatefulKnowledgeSession();
		logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
	}

	/**
	 * This fires all the Rules, each event at a time
	 * using a given interval
	 * @param event - The event that occurs
	 * @param interval - the interval between events
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
	 * @return KnowledgeBase - the Guvnor knowledge Base
	 */
    private static KnowledgeBase readRemoteKnowledgeBase() {
        KnowledgeAgentConfiguration kaconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
        kaconf.setProperty( "drools.agent.scanDirectories", "false" );
        KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent( "test agent", kaconf );
        kagent.applyChangeSet( ResourceFactory.newClassPathResource("guvnor.xml"));
        return kagent.getKnowledgeBase();
    }
    
    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
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
     * @param URL - The URL String to the Guvnor rules
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public static void setRemoteAddress(String URL) throws SAXException, IOException, ParserConfigurationException, TransformerException{
    	
    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(Drools.class.getResource("/guvnor.xml").getPath());
		Node resource = doc.getElementsByTagName("resource").item(0);
		NamedNodeMap attr = resource.getAttributes();
		attr.getNamedItem("source").setNodeValue(URL);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource src = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(Drools.class.getResource("/guvnor.xml").getPath()));
		transformer.transform(src, result);
    }

}
