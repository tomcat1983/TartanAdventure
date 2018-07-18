package edu.cmu.tartan.xml;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NodeList;
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
	public void testParseXmlFromFileLoaded() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		parseXmlSpy.parseXmlFromFile(validXmlFileName);
		assertTrue(parseXmlSpy.isXmlLoaded);	
	}
	
	@Test
	public void testParseXmlFromFileNotLoaded() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		parseXmlSpy.parseXmlFromFile(invalidXmlFileName);
		assertFalse(parseXmlSpy.isXmlLoaded);
	}
	
	@Test
	public void testGetValueByTagAndAttributeUsingGameDesignXMLFile() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromFile(validXmlFileName);
		assertTrue(parseXml.getValueByTagAndAttribute("message", "type").equals("UPLOAD_MAP_DESIGN"));
	}
}

class XmlParserSpy extends XmlParser{

	public boolean isXmlLoaded = false; 
	
	public XmlParserSpy() throws ParserConfigurationException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void parseXmlFromFile(String fileName){
		try {
			parseXmlFromFileThrowException(fileName);
			isXmlLoaded = true; 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

