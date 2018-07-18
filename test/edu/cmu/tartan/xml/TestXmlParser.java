package edu.cmu.tartan.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class TestXmlParser {

	String validXmlFileName;
	String invalidXmlFileName;
	String notExistFileName; 
	
	@Before
	public void testXmlSetup() {
		validXmlFileName = "test/edu/cmu/tartan/xml/GameXmlValid.xml";
		invalidXmlFileName = "test/edu/cmu/tartan/xml/GameXmlInvalid.xml";
		notExistFileName = "na.xml";
	}
	
	@Test(expected = SAXException.class) 
	public void testParseXmlFromFileInvalidXmlSAXException() throws ParserConfigurationException, SAXException, IOException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromFileThrowException(invalidXmlFileName);
	}
	
	@Test(expected = IOException.class) 
	public void testParseXmlFromFileInvalidXmlIOException() throws ParserConfigurationException, SAXException, IOException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromFileThrowException(notExistFileName);
	}
	
	@Test
	public void testParseXmlFromFile() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromFile(validXmlFileName);
		
	}
}

