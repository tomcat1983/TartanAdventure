package edu.cmu.tartan.xml;

import static org.junit.Assert.*;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class TestXmlParser {

	String validXmlFileName;
	String invalidXmlFileName; 
	
	@Before
	public void testXmlSetup() {
		validXmlFileName = "test/edu/cmu/tartan/xml/ValidXml.xml";
		invalidXmlFileName = "test/edu/cmu/tartan/xml/InvalidXml.xml";
	}
	
	@Ignore
	public void testParseDom() throws ParserConfigurationException {
		
		XmlParser parseDom = new XmlParser();
		String xmlName = this.getClass().getResource("test/edu/cmu/tartan/xml/NewFile.xml").getPath();
		parseDom.parseDOM(xmlName);
		
	}
	
	
	@Test
	public void testParseDomFromFile() throws ParserConfigurationException {
		
		XmlParser parseDom = new XmlParser();
		parseDom.parseDomFromFile(validXmlFileName);
		
	}
}
