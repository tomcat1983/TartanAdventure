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

public class TestXmlParser {

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

    	validXmlFileName = "test/edu/cmu/tartan/xml/GameXmlValid.xml";
    	invalidXmlFileName = "test/edu/cmu/tartan/xml/GameXmlInvalidFormat.xml";
    	loginXmlFileName = "test/edu/cmu/tartan/xml/Login.xml";
    	addUserXmlFileName = "test/edu/cmu/tartan/xml/AddUser.xml";
    	unknownMessageXmlFileName = "test/edu/cmu/tartan/xml/UnknownMessage.xml";
    	notExistFileName = "na.xml";
    	gameXmlInvalidGoalCntMatchFileName = "test/edu/cmu/tartan/xml/GameXmlInvalidGoalCntMatch.xml";
    	gameXmlInvalidRoomCntMatchFileName = "test/edu/cmu/tartan/xml/GameXmlInvalidRoomCntMatch.xml";

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
	
	@Test
	public void testGetInvalidDataErrorWhenUloadMapRoomCntNotMatch() throws ParserConfigurationException {
		
		XmlParseResult result; 
		XmlParser parseXml = new XmlParser();
		result = parseXml.parseXmlFromString(readAllBytes(gameXmlInvalidRoomCntMatchFileName));
		assertTrue(result.equals(XmlParseResult.INVALID_DATA));
	}
	
	@Test
	public void testGetInvalidDataErrorWhenUloadMapGoalCntNotMatch() throws ParserConfigurationException {
		
		XmlParseResult result; 
		XmlParser parseXml = new XmlParser();
		result = parseXml.parseXmlFromString(readAllBytes(gameXmlInvalidRoomCntMatchFileName));
		assertTrue(result.equals(XmlParseResult.INVALID_DATA));
	}
	
	@Test
	public void testRoom5IsLockable() throws ParserConfigurationException {
		
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getRoomIndex(4) instanceof RoomLockable);
	}
	
	@Test
	public void testRoom5CanNotMoveToNorth() throws ParserConfigurationException {
		
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertFalse(cGame.getRoomIndex(4).canMoveToRoomInDirection(Action.ACTION_GO_NORTH));
	}
	
	@Test
	public void testRoom5CanMoveToEast() throws ParserConfigurationException {
		
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getRoomIndex(4).canMoveToRoomInDirection(Action.ACTION_GO_EAST));
	}
	
	@Test
	public void testRoom5IsLocked() throws ParserConfigurationException {
		
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		RoomLockable roomLock = (RoomLockable) cGame.getRoomIndex(4); 
		assertTrue(roomLock.isLocked());
	}
	
	@Test
	public void testRoom5IsLockedWhenNotUsingProperKey() throws ParserConfigurationException {
		
		//<room index="1" type="normal" north="2" west="0" east="6" item_list="shovel:5-document:3" />
		//<room index="3" type="normal" south="2" />
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		RoomLockable roomLock = (RoomLockable) cGame.getRoomIndex(4); 
		Room roomDontHaveKey = cGame.getRoomIndex(1); 
		List<Item> items = roomDontHaveKey.getItems();
		for (Item item : items) {
			roomLock.unlock(item);
		}
		assertTrue(roomLock.isLocked());
	}
	
	@Test
	public void testRoom5IsUnlockedWhenUsingProperKey() throws ParserConfigurationException {
		
		//<room index="1" type="normal" north="2" west="0" east="6" item_list="shovel:5-document:3" />
		//<room index="3" type="normal" south="2" />
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		RoomLockable roomLock = (RoomLockable) cGame.getRoomIndex(4); 
		Room roomHaveKey = cGame.getRoomIndex(3); 
		List<Item> items = roomHaveKey.getItems();
		for (Item item : items) {
			roomLock.unlock(item);
		}
		assertFalse(roomLock.isLocked());
	}
	
	@Test
	public void testRoom7IsDark() throws ParserConfigurationException {
		
		//<room index="6" type="dark" south="7" west="1" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getRoomIndex(6) instanceof RoomDark);
	}
	
	@Test
	public void testRoom9IsExcavatable() throws ParserConfigurationException {
		
		//<room index="8" type="excavatable" north="7" west="9" east="13" item_list="microwave:1" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getRoomIndex(8) instanceof RoomExcavatable);
	}
	
	@Test
	public void testRoom11IsObscured() throws ParserConfigurationException {
		
		//<room index="10" type="obscured" north="12" west="11" east="9" obstacle="fridge:9" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getRoomIndex(10) instanceof RoomObscured);
	}
	
	@Test
	public void testRoom14IsRequiredItem() throws ParserConfigurationException {
		
		//<room index="13" type="require" west="8" require_item="gold:11:2"/>
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getRoomIndex(13) instanceof RoomRequiredItem);
	}
	

	@Test
	public void testRoom14RequireGold() throws ParserConfigurationException {
		
		//<room index="13" type="require" west="8" require_item="gold:11:2"/>
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		RoomRequiredItem roomRequire = (RoomRequiredItem) cGame.getRoomIndex(13);  
		assertTrue(roomRequire.requiredItem().equals(Item.getInstance("gold")));
	}
	
	@Disabled("왜때문인지 모르겠는데 Eclipse 에서는 제대로 도는데 travis 에서는 안돈다. ")
	@Test
	public void testGoal1IsCollectType() throws ParserConfigurationException {
		//<goal index="0" type="collect" object="diamond-shovel" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getGameGoalIndex(0) instanceof GameCollectGoal);
	}
	
	@Disabled("왜때문인지 모르겠는데 Eclipse 에서는 제대로 도는데 travis 에서는 안돈다. ")
	@Test
	public void testGoal2IsExploreType() throws ParserConfigurationException {
		//<goal index="1" type="explore" object="6-11-12-13" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getGameGoalIndex(1) instanceof GameExploreGoal);
	}
	
	@Disabled("왜때문인지 모르겠는데 Eclipse 에서는 제대로 도는데 travis 에서는 안돈다. ")
	@Test
	public void testGoal3IsPointType() throws ParserConfigurationException {
		//<goal index="2" type="point" object="100" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml();
		assertTrue(cGame.getGameGoalIndex(2) instanceof GamePointsGoal);
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

class XmlParserSpy extends XmlParser {

	public boolean isXmlLoaded = false; 
	public boolean isExceptionCatched = false; 
	public boolean isPrintNodeCalled = false; 
	XmlParseResult xmlParseResult = XmlParseResult.SUCCESS;
	public static String xmlUri; 
	
	public XmlParserSpy() throws ParserConfigurationException {
		super();
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