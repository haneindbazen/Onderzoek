package com.simulatieTool;

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

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.simulatieTool.Webapp.Drools;

public class Test {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(Drools.class.getResource("/guvnor.xml").getPath());
		Node resource = doc.getElementsByTagName("resource").item(0);
		NamedNodeMap attr = resource.getAttributes();
		attr.getNamedItem("source").setNodeValue("test");
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource src = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(Drools.class.getResource("/guvnor.xml").getPath()));
		StreamResult result2 = new StreamResult(System.out);
		transformer.transform(src, result);
		
	}

}
