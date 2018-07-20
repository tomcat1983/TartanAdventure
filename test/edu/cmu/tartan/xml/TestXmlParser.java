package edu.cmu.tartan.xml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
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
	String loginXmlFileName; 
	String addUserXmlFileName;
	String unknownMessageXmlFileName; 

    @BeforeEach
    public void testXmlSetup() {

    	validXmlFileName = "test/edu/cmu/tartan/xml/GameXmlValid.xml";
    	invalidXmlFileName = "test/edu/cmu/tartan/xml/GameXmlInvalid.xml";
    	loginXmlFileName = "test/edu/cmu/tartan/xml/Login.xml";
    	addUserXmlFileName = "test/edu/cmu/tartan/xml/AddUser.xml";
    	unknownMessageXmlFileName = "test/edu/cmu/tartan/xml/UnknownMessage.xml";
    	notExistFileName = "na.xml";
    	
    }
	
	@Test
	public void testParseXmlFromStringInvalidXmlSAXException() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		
		assertThrows(SAXParseException.class,
				()->{
					parseXmlSpy.parseXmlFromStringThrowException(readAllBytes(invalidXmlFileName));
				});
	}
	
	@Test
	public void testParseXmlFromStringInvalidXmlSAXExceptionCatch() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		parseXmlSpy.parseXmlFromString(readAllBytes(invalidXmlFileName));
		assertTrue(parseXmlSpy.isExceptionCatched);
	}
	
	@Test
	public void testParseXmlFromStringLoaded() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		parseXmlSpy.parseXmlFromString(readAllBytes(validXmlFileName));
		assertTrue(parseXmlSpy.isXmlLoaded);
	}
	
	@Test
	public void testParseXmlFromStringNotLoaded() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		parseXmlSpy.parseXmlFromString(readAllBytes(invalidXmlFileName));
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
	public void testParseXmlFromFileInvalidXmlSAXExceptionCatch() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		parseXmlSpy.parseXmlFromFile(invalidXmlFileName);
		assertTrue(parseXmlSpy.isExceptionCatched);
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
	public void testPrintNodeFromFile() throws ParserConfigurationException {
		
		XmlParserSpy parseXmlSpy = new XmlParserSpy();
		parseXmlSpy.parseXmlFromFile(validXmlFileName);
		parseXmlSpy.printNodeInfo(parseXmlSpy.getNodeList().item(0));
		assertTrue(parseXmlSpy.isPrintNodeCalled);
	}
	
	@Test
	public void testGetValueByTagAndAttributeResultFoundFromString() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(validXmlFileName));
		assertTrue(parseXml.getValueByTagAndAttribute("message", "type").equals("UPLOAD_MAP_DESIGN"));
	}
	
	@Test
	public void testGetValueByTagAndAttributeResultNotFoundFromString() throws ParserConfigurationException {

		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(validXmlFileName));		
		assertTrue(parseXml.getValueByTagAndAttribute("message", "user_id") == null);
	}
	
	@Test
	public void testGetMessageTypeOfUploadMapDesign() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(validXmlFileName));
		assertTrue(parseXml.getMessageType().equals("UPLOAD_MAP_DESIGN"));
	}
	
	@Test
	public void testGetMessageTypeOfLogin() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
		assertTrue(parseXml.getMessageType().equals("REQ_LOGIN"));
	}
	
	@Test
	public void testGetMessageTypeOfAddUser() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
		assertTrue(parseXml.getMessageType().equals("ADD_USER"));

	}
	
	@Test
	public void testGetMessageTypeOfUnknown() throws ParserConfigurationException {
		
		XmlParseResult result; 
		XmlParser parseXml = new XmlParser();
		result = parseXml.parseXmlFromString(readAllBytes(unknownMessageXmlFileName));
		assertTrue(result.equals(XmlParseResult.UNKNOWN_MESSAGE));
	}
	
	public String readAllBytes(String filePath) {
	    String content = "";
	    try{
	        content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    
	    return content;
	}
}

class XmlParserSpy extends XmlParser {

	public boolean isXmlLoaded = false; 
	public boolean isExceptionCatched = false; 
	public boolean isPrintNodeCalled = false; 
	XmlParseResult xmlParseResult = XmlParseResult.SUCCESS;
	public static String xmlUri; 
	
	public XmlParserSpy() throws ParserConfigurationException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public XmlParseResult parseXmlFromFile(String fileName) {
		
		xmlParseResult = super.parseXmlFromFile(fileName);
		if(xmlParseResult.equals(XmlParseResult.SUCCESS))
			isXmlLoaded = true; 
		else if(xmlParseResult.equals(XmlParseResult.INVALID_XML))
			isExceptionCatched = true; 
		
		return xmlParseResult; 
	}

	@Override
	public XmlParseResult parseXmlFromString(String xmlUri) {
		
		xmlParseResult = super.parseXmlFromString(xmlUri);
		if(xmlParseResult.equals(XmlParseResult.SUCCESS))
			isXmlLoaded = true; 
		else if(xmlParseResult.equals(XmlParseResult.INVALID_XML))
			isExceptionCatched = true; 
		
		return xmlParseResult; 
	}
	
	protected void printNodeInfo(Node node) {
		super.printNodeInfo(node);
		isPrintNodeCalled = true; 
	}
	
}