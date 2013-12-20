package com.simulatieTool.Webapp;

import javax.xml.parsers.DocumentBuilder;

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

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.simulatieTool.Test;

public class Drools {

	static KnowledgeBase kbase;
	static StatefulKnowledgeSession ksession;
	static KnowledgeRuntimeLogger logger;

	public static void Init(String URL) throws Exception {
		setRemoteAddress(URL);
		kbase = readRemoteKnowledgeBase();
		ksession = kbase.newStatefulKnowledgeSession();
		logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
	}

	public static void FireRules(Event event) {
		try {
			ksession.insert(event);
			ksession.fireAllRules();
			Thread.sleep(2000);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void Close() {
		logger.close();
	}

	public static KnowledgeBase readKnowledgeBase() throws Exception {
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
	
    private static KnowledgeBase readRemoteKnowledgeBase() {
        KnowledgeAgentConfiguration kaconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
        kaconf.setProperty( "drools.agent.scanDirectories", "false" );
        KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent( "test agent", kaconf );
        kagent.applyChangeSet( ResourceFactory.newClassPathResource(Drools.class.getResource("/guvnor.xml").getPath()));
        return kagent.getKnowledgeBase();
    }
    
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
		StreamResult result2 = new StreamResult(System.out);
		transformer.transform(src, result);
    }

}
