package edu.cmu.tartan.xml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.games.CustomizingGame;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomDark;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomLockable;
import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.room.RoomRequiredItem;

public class TestXmlParserClient {

	String validXmlFileName;
	String invalidXmlFileName;
	String notExistFileName; 
	String loginXmlFileName; 
	String addUserXmlFileName;
	String unknownMessageXmlFileName; 
	String gameXmlInvalidGoalCntMatchFileName;
	String gameXmlInvalidRoomCntMatchFileName;
	
    @BeforeEach
    public void testXmlSetup() {

    	validXmlFileName = "test/edu/cmu/tartan/xml/GameXmlResponse.xml";
    	loginXmlFileName = "test/edu/cmu/tartan/xml/LoginResponseOK.xml";
    	addUserXmlFileName = "test/edu/cmu/tartan/xml/AddUserResponse.xml";
    }
	
	@Test
	public void testParsingMessageTypeForLogin() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
		XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
		assertTrue(xr.getMsgType().equals(XmlMessageType.REQ_LOGIN));
	}
	
	@Test
	public void testParsingMessageTypeForUploadMap() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(readAllBytes(validXmlFileName));
		XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
		assertTrue(xr.getMsgType().equals(XmlMessageType.UPLOAD_MAP_DESIGN));
	}
	
	@Test
	public void testParsingMessageTypeForAddUser() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
		XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
		assertTrue(xr.getMsgType().equals(XmlMessageType.ADD_USER));
	}
	
	@Test
	public void testParsingLoginResult() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
		XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
		assertTrue(xr.getResultStr().equals(XmlResultString.OK));
	}
	
	@Test
	public void testParsingLoginNgReason() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
		XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
		assertTrue(xr.getNgReason().equals(XmlNgReason.OK));
	}
		
	@Test
	public void testParsingAddUserResult() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
		XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
		assertTrue(xr.getResultStr().equals(XmlResultString.OK));
	}
	
	@Test
	public void testParsingAddUserNgReason() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
		XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
		assertTrue(xr.getNgReason().equals(XmlNgReason.OK));
	}
	
	
	@Disabled("Describe how to make login XML")
	@Test
	public void testWritingXmlLogin() throws ParserConfigurationException {
		
		XmlWriterClient xw = new XmlWriterClient(); 
		xw.makeXmlForLogin("tak", "abcdefg", XmlLoginRole.PLAYER);
	}
	
	@Disabled("Describe how to make add user XML")
	@Test
	public void testWritingXmlForAddUser() throws ParserConfigurationException {
		
		XmlWriterClient xw = new XmlWriterClient(); 
		xw.makeXmlForAddUser("tak", "abcdefg");
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
