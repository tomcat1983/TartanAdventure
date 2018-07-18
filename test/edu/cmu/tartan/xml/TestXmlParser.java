package edu.cmu.tartan.xml;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import jdk.nashorn.internal.ir.annotations.Ignore;

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
	public void testParseDomFromFileInvalidXmlSAXException() throws ParserConfigurationException, SAXException, IOException {
		
		XmlParser parseDom = new XmlParser();
		parseDom.parseDomFromFileThrowException(invalidXmlFileName);
	}
	
	@Test(expected = IOException.class) 
	public void testParseDomFromFileInvalidXmlIOException() throws ParserConfigurationException, SAXException, IOException {
		
		XmlParser parseDom = new XmlParser();
		parseDom.parseDomFromFileThrowException(notExistFileName);
	}
	
	@Test
	public void testParseDomFromFile() throws ParserConfigurationException {
		
		XmlParser parseDom = new XmlParser();
		parseDom.parseDomFromFile(validXmlFileName);
		
	}
}

