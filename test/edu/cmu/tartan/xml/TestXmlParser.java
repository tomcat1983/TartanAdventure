package edu.cmu.tartan.xml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TestXmlParser {

	String validXmlFileName;
	String invalidXmlFileName;
	String notExistFileName; 
	
	@BeforeEach
	public void testXmlSetup() {
		validXmlFileName = "test/edu/cmu/tartan/xml/GameXmlValid.xml";
		invalidXmlFileName = "test/edu/cmu/tartan/xml/GameXmlInvalid.xml";
		notExistFileName = "na.xml";
	}
	
	@Test
	public void testParseXmlFromStringInvalidXmlSAXException() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		XmlParserSpy.readAllBytes(invalidXmlFileName);
		
		assertThrows(SAXParseException.class,
				()->{
					parseXmlSpy.parseXmlFromStringThrowException(XmlParserSpy.xmlUri);
				});
	}
	
	@Test
	public void testParseXmlFromStringLoaded() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		XmlParserSpy.readAllBytes(validXmlFileName);
		parseXmlSpy.parseXmlFromString(XmlParserSpy.xmlUri);
		assertTrue(parseXmlSpy.isXmlLoaded);	
	}
	
	@Test
	public void testParseXmlFromStringNotLoaded() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		XmlParserSpy.readAllBytes(invalidXmlFileName);
		parseXmlSpy.parseXmlFromString(XmlParserSpy.xmlUri);
		assertFalse(parseXmlSpy.isXmlLoaded);
	}
		
	
	@Test
	public void testParseXmlFromFileInvalidXmlSAXException() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		
		assertThrows(SAXException.class,
				()->{
					parseXml.parseXmlFromFileThrowException(invalidXmlFileName);
				});
	}
	
	@Test
	public void testParseXmlFromFileInvalidXmlIOException() throws ParserConfigurationException, SAXException, IOException {
		
		XmlParser parseXml = new XmlParser();
		
		assertThrows(IOException.class,
				()->{
					parseXml.parseXmlFromFileThrowException(notExistFileName);
				});
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
	public void testGetValueByTagAndAttributeResultFoundFromFile() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromFile(validXmlFileName);
		assertTrue(parseXml.getValueByTagAndAttribute("message", "type").equals("UPLOAD_MAP_DESIGN"));
	}
	
	@Test
	public void testGetValueByTagAndAttributeResultNotFoundFromFile() throws ParserConfigurationException {

		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromFile(validXmlFileName);
		assertTrue(parseXml.getValueByTagAndAttribute("message", "user_id") == null);
	}
	
	@Test
	public void testGetValueByTagAndAttributeResultFoundFromString() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		XmlParserSpy.readAllBytes(validXmlFileName);
		parseXmlSpy.parseXmlFromString(XmlParserSpy.xmlUri);
		assertTrue(parseXmlSpy.getValueByTagAndAttribute("message", "type").equals("UPLOAD_MAP_DESIGN"));
	}
	
	@Test
	public void testGetValueByTagAndAttributeResultNotFoundFromString() throws ParserConfigurationException {

		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		XmlParserSpy.readAllBytes(validXmlFileName);
		parseXmlSpy.parseXmlFromString(XmlParserSpy.xmlUri);		
		assertTrue(parseXmlSpy.getValueByTagAndAttribute("message", "user_id") == null);
	}
}

class XmlParserSpy extends XmlParser{

	public boolean isXmlLoaded = false; 
	public static String xmlUri; 
	
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

	@Override
	public void parseXmlFromString(String xmlUri) {

		try {
			parseXmlFromStringThrowException(xmlUri);
			isXmlLoaded = true; 
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
	
	
	public static String readAllBytes(String filePath){
	    String content = "";
	    try
	    {
	        content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    xmlUri = content;
	    System.out.println(xmlUri);
	    return content;
	}

	
}