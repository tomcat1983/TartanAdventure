package edu.cmu.tartan.xml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;


public class TestXmlParserClient {

	String validXmlFileName;
	String invalidXmlFileName;
	String notExistFileName; 
	String loginXmlFileName; 
	String addUserXmlFileName;
	String unknownMessageXmlFileName; 
	String gameXmlInvalidGoalCntMatchFileName;
	String gameXmlInvalidRoomCntMatchFileName;
	protected Logger gameLogger = Logger.getGlobal();

	
    @BeforeEach
    public void testXmlSetup(TestInfo testInfo) {

    	validXmlFileName = "test/edu/cmu/tartan/xml/GameXmlResponse.xml";
    	loginXmlFileName = "test/edu/cmu/tartan/xml/LoginResponseOK.xml";
    	addUserXmlFileName = "test/edu/cmu/tartan/xml/AddUserResponse.xml";
    }
	
	@Test
	public void testParsingMessageTypeForLogin() {
		
		XmlParser parseXml;
		try {
			parseXml = new XmlParser(XmlParserType.CLIENT);
			parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
			XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
			assertTrue(xr.getMsgType().equals(XmlMessageType.REQ_LOGIN));
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		}
	}
	
	@Test
	public void testParsingMessageTypeForUploadMap() {
		
		XmlParser parseXml;
		try {
			parseXml = new XmlParser(XmlParserType.CLIENT);
			parseXml.parseXmlFromString(readAllBytes(validXmlFileName));
			XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
			assertTrue(xr.getMsgType().equals(XmlMessageType.UPLOAD_MAP_DESIGN));
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		}
	}
	
	@Test
	public void testParsingMessageTypeForAddUser() {
		
		XmlParser parseXml;
		try {
			parseXml = new XmlParser(XmlParserType.CLIENT);
			parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
			XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
			assertTrue(xr.getMsgType().equals(XmlMessageType.ADD_USER));
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		}
	}
	
	@Test
	public void testParsingLoginResult() {

		XmlParser parseXml;
		try {
	
			parseXml = new XmlParser(XmlParserType.CLIENT);
			parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
			XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
			assertTrue(xr.getResultStr().equals(XmlResultString.OK));
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		}
	}
	
	@Test
	public void testParsingLoginNgReason() {
		
		XmlParser parseXml;
		try {
			parseXml = new XmlParser(XmlParserType.CLIENT);
			parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
			XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
			assertTrue(xr.getNgReason().equals(XmlNgReason.OK));
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		}
	}
		
	@Test
	public void testParsingAddUserResult() {
		
		XmlParser parseXml;
		try {
			parseXml = new XmlParser(XmlParserType.CLIENT);
			parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
			XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
			assertTrue(xr.getResultStr().equals(XmlResultString.OK));
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		}
	}
	
	@Test
	public void testParsingAddUserNgReason() {
		
		XmlParser parseXml;
		try {
			parseXml = new XmlParser(XmlParserType.CLIENT);
			parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
			XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
			assertTrue(xr.getNgReason().equals(XmlNgReason.OK));
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		}
	}
	
	
	@Disabled("Describe how to make login XML")
	@Test
	public void testWritingXmlLogin() {
		
		XmlWriterClient xw = new XmlWriterClient(); 
		xw.makeXmlForLogin("tak", "abcdefg", XmlLoginRole.PLAYER);
	}
	
	@Disabled("Describe how to make add user XML")
	@Test
	public void testWritingXmlForAddUser() {
		
		XmlWriterClient xw = new XmlWriterClient(); 
		xw.makeXmlForAddUser("tak", "abcdefg");
	}
	
	@Disabled("Describe how to make upload Map XML")
	@Test
	public void testWritingXmlForUploadMap() {
		
		XmlWriterClient xw = new XmlWriterClient(); 
		xw.makeXmlForUploadMap("userMap.xml"); 
	}
	
	@Disabled("Describe how to make game start packet")
	@Test
	public void testWritingXmlForGameStart() {
		
		XmlWriterClient xw = new XmlWriterClient(); 
		xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_START, "startId");
	}
	
	@Disabled("Describe how to make game end packet")
	@Test
	public void testWritingXmlForGameEnd() {
		
		XmlWriterClient xw = new XmlWriterClient(); 
		xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_END, "endId");
	}
	
	@Disabled("Describe how to make HB packet")
	@Test
	public void testWritingXmlForHeartBeat() {
		
		XmlWriterClient xw = new XmlWriterClient(); 
		xw.makeXmlForHeartBeat("heartBeatId");
	}
	
	/*
	 * methods for test only 
	 */
	public String readAllBytes(String filePath) {
	    String content = "";
	    try{
	        content = new String (Files.readAllBytes( Paths.get(filePath)));
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return content;
	}
}
