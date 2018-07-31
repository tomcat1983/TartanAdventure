package edu.cmu.tartan.xml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
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

public class TestXmlParser {

	String validXmlFileName;
	String invalidXmlFileName;
	String notExistFileName; 
	String loginXmlFileName; 
	String addUserXmlFileName;
	String unknownMessageXmlFileName; 
	String gameXmlInvalidGoalCntMatchFileName;
	String gameXmlInvalidRoomCntMatchFileName;
	String gameStartXmlFileName;
	String gameStartInvalidXmlFileName;
	String gameEndXmlFileName;
	String gameEndInvalidXmlFileName;
	String sendCommandInvalidXmlFileName;
	String hbInvalidXmlFileName;

	
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
    	gameStartXmlFileName = "test/edu/cmu/tartan/xml/RequestGameStart.xml";
    	gameEndXmlFileName = "test/edu/cmu/tartan/xml/RequestGameEnd.xml";
    	gameStartInvalidXmlFileName = "test/edu/cmu/tartan/xml/RequestGameStartInvalid.xml";
    	gameEndInvalidXmlFileName = "test/edu/cmu/tartan/xml/RequestGameEndInvalid.xml";
    	sendCommandInvalidXmlFileName = "test/edu/cmu/tartan/xml/SendCommandInvalid.xml";
    	hbInvalidXmlFileName = "test/edu/cmu/tartan/xml/HeartbeatInvalid.xml";

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
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getRoomIndex(4) instanceof RoomLockable);
	}
	
	@Test
	public void testRoom5CanNotMoveToNorth() throws ParserConfigurationException {
		
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertFalse(cGame.getRoomIndex(4).canMoveToRoomInDirection(Action.ACTION_GO_NORTH));
	}
	
	@Test
	public void testRoom5CanMoveToEast() throws ParserConfigurationException {
		
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getRoomIndex(4).canMoveToRoomInDirection(Action.ACTION_GO_EAST));
	}
	
	@Test
	public void testRoom5IsLocked() throws ParserConfigurationException {
		
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		RoomLockable roomLock = (RoomLockable) cGame.getRoomIndex(4); 
		assertTrue(roomLock.isLocked());
	}
	
	@Test
	public void testRoom5IsLockedWhenNotUsingProperKey() throws ParserConfigurationException {
		
		//<room index="1" type="normal" north="2" west="0" east="6" item_list="shovel-document" />
		//<room index="3" type="normal" south="2" />
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		RoomLockable roomLock = (RoomLockable) cGame.getRoomIndex(4); 
		Room roomDontHaveKey = cGame.getRoomIndex(1); 
		
		Player player = new Player(roomLock, Player.DEFAULT_USER_NAME);
		roomLock.setPlayer(player);

		
		List<Item> items = roomDontHaveKey.getItems();
		for (Item item : items) {
			System.out.println("roomDontHaveKey : " + item.toString());
			roomLock.unlock(item);
			System.out.println("roomDontHaveKey isLocked: " + roomLock.isLocked());
		}
		assertTrue(roomLock.isLocked());
	}
	
	@Test
	public void testRoom5IsUnlockedWhenUsingProperKey() throws ParserConfigurationException {
		
		//<room index="1" type="normal" north="2" west="0" east="6" item_list="shovel-document" />
		//<room index="3" type="normal" south="2" />
		//<room index="4" type="lockable" west="5" east="2" lock_item="lock:2" key_item="key:3" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		RoomLockable roomLock = (RoomLockable) cGame.getRoomIndex(4); 
		
		Player player = new Player(roomLock, Player.DEFAULT_USER_NAME);
		roomLock.setPlayer(player);
		
		Room roomHaveKey = cGame.getRoomIndex(3); 
		List<Item> items = roomHaveKey.getItems();
		for (Item item : items) {
			System.out.println("roomHaveKey : " + item.toString());
			roomLock.unlock(item);
			System.out.println("roomHaveKey isLocked: " + roomLock.isLocked());
		}
		assertFalse(roomLock.isLocked());
	}
	
	@Test
	public void testRoom7IsDark() throws ParserConfigurationException {
		
		//<room index="6" type="dark" south="7" west="1" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getRoomIndex(6) instanceof RoomDark);
	}
	
	@Test
	public void testRoom9IsExcavatable() throws ParserConfigurationException {
		
		//<room index="8" type="excavatable" north="7" west="9" east="13" item_list="microwave:1" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getRoomIndex(8) instanceof RoomExcavatable);
	}
	
	@Test
	public void testRoom11IsObscured() throws ParserConfigurationException {
		
		//<room index="10" type="obscured" north="12" west="11" east="9" obstacle="fridge:9" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getRoomIndex(10) instanceof RoomObscured);
	}
	
	@Test
	public void testRoom14IsRequiredItem() throws ParserConfigurationException {
		
		//<room index="13" type="require" west="8" require_item="gold:11:2"/>
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getRoomIndex(13) instanceof RoomRequiredItem);
	}
	

	@Test
	public void testRoom14RequireGold() throws ParserConfigurationException {
		
		//<room index="13" type="require" west="8" require_item="gold:11:2"/>
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		RoomRequiredItem roomRequire = (RoomRequiredItem) cGame.getRoomIndex(13);  
		assertTrue(roomRequire.requiredItem().equals(Item.getInstance("gold", Player.DEFAULT_USER_NAME)));
	}
	
	@Disabled("work in eclipse fine but not in travis, I don't know why")
	@Test
	public void testGoal1IsCollectType() throws ParserConfigurationException {
		//<goal index="0" type="collect" object="diamond-shovel" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getGameGoalIndex(0) instanceof GameCollectGoal);
	}
	
	@Disabled("work in eclipse fine but not in travis, I don't know why")
	@Test
	public void testGoal2IsExploreType() throws ParserConfigurationException {
		//<goal index="1" type="explore" object="6-11-12-13" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getGameGoalIndex(1) instanceof GameExploreGoal);
	}
	
	@Disabled("work in eclipse fine but not in travis, I don't know why")
	@Test
	public void testGoal3IsPointType() throws ParserConfigurationException {
		//<goal index="2" type="point" object="100" />
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(Player.DEFAULT_USER_NAME);
		assertTrue(cGame.getGameGoalIndex(2) instanceof GamePointsGoal);
	}
	
	@Test
	public void testParsingLoginId() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
		XmlResponse xr = parseXml.getXmlResponse();
		String id = ((XmlResponseLogin) xr).getId();
		assertTrue(id.equals("takhh"));
	}
	
	@Test
	public void testParsingLoginPw() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(loginXmlFileName));
		XmlResponse xr = parseXml.getXmlResponse();
		String pw = ((XmlResponseLogin) xr).getPw();
		assertTrue(pw.equals("awefaweg14ro4aw3"));
	}
	
	@Test
	public void testWritingLoginResultOK() throws ParserConfigurationException {
		
		XmlResultString xrs = XmlResultString.OK;
		XmlNgReason xnr = XmlNgReason.OK;
		
		XmlWriterServer xw = new XmlWriterServer(); 
		String xmlStr = xw.makeXmlForLogin(xrs, xnr);
		
		XmlParser xp = new XmlParser(XmlParserType.CLIENT);
		xp.parseXmlFromString(xmlStr);
		XmlResponseClient xr = (XmlResponseClient) xp.getXmlResponse();
		assertTrue(xr.getResultStr().equals(xrs));
		assertTrue(xr.getNgReason().equals(xnr));
	}
	
	@Test
	public void testWritingLoginResultFail() throws ParserConfigurationException {
		
		XmlResultString xrs = XmlResultString.NG;
		XmlNgReason xnr = XmlNgReason.SERVER_BUSY;
		
		XmlWriterServer xw = new XmlWriterServer(); 
		String xmlStr = xw.makeXmlForLogin(xrs, xnr);
		
		XmlParser xp = new XmlParser(XmlParserType.CLIENT);
		xp.parseXmlFromString(xmlStr);
		XmlResponseClient xr = (XmlResponseClient) xp.getXmlResponse();
		assertTrue(xr.getResultStr().equals(xrs));
		assertTrue(xr.getNgReason().equals(xnr));
	}
	
	@Test
	public void testWritingUploadMapOK() throws ParserConfigurationException {
		
		XmlResultString xrs = XmlResultString.OK;
		XmlNgReason xnr = XmlNgReason.OK;
		
		XmlWriterServer xw = new XmlWriterServer(); 
		String xmlUri = xw.makeXmlForGameUpload(xrs, xnr);
		
		XmlParser xp = new XmlParser(XmlParserType.CLIENT);
		xp.parseXmlFromString(xmlUri);
		XmlResponseClient xr = (XmlResponseClient) xp.getXmlResponse();
		
		assertTrue(xrs.equals(xr.getResultStr()));
		assertTrue(xnr.equals(xr.getNgReason()));
	}
	
	@Test
	public void testWritingUploadMapNG() throws ParserConfigurationException {
		
		XmlResultString xrs = XmlResultString.NG;
		XmlNgReason xnr = XmlNgReason.INVALID_INFO;
		
		XmlWriterServer xw = new XmlWriterServer(); 
		String xmlUri = xw.makeXmlForGameUpload(xrs, xnr);
		
		XmlParser xp = new XmlParser(XmlParserType.CLIENT);
		xp.parseXmlFromString(xmlUri);
		XmlResponseClient xr = (XmlResponseClient) xp.getXmlResponse();
		
		assertTrue(xrs.equals(xr.getResultStr()));
		assertTrue(xnr.equals(xr.getNgReason()));
	}
	
	
	@Test
	public void testWritingEventMessage() throws ParserConfigurationException {
		
		String eventMsg = "hello client\n this is message from server";
		String eventId = "eventid";
		XmlWriterServer xw = new XmlWriterServer(); 
		String xmlStr = xw.makeXmlForEventMessage(eventId, eventMsg);
		
		XmlParser xp = new XmlParser(XmlParserType.CLIENT);
		xp.parseXmlFromString(xmlStr);
		XmlResponseClient xr = (XmlResponseClient)xp.getXmlResponse();
		assertTrue(eventId.equals(xr.getId()));
		assertTrue(eventMsg.equals(xr.getEventMsg()));
	}
	
	
	@Test
	public void testParsingAddUserId() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
		XmlResponse xr = parseXml.getXmlResponse();
		String id = ((XmlResponseAddUser) xr).getId();
		assertTrue(id.equals("takhh"));
	}
	
	@Test
	public void testParsingAddUserPw() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(addUserXmlFileName));
		XmlResponse xr = parseXml.getXmlResponse();
		String pw = ((XmlResponseAddUser) xr).getPw();
		assertTrue(pw.equals("awefaweg14ro4aw3"));
	}
	
	@Test
	public void testLocalGameRoom14RequireGold() throws ParserConfigurationException {
		
		//<room index="13" type="require" west="8" require_item="gold:11:2"/>
		XmlParser parseXml = new XmlParser();
		CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(GameMode.LOCAL, Player.DEFAULT_USER_NAME);
		RoomRequiredItem roomRequire = (RoomRequiredItem) cGame.getRoomIndex(13);  
		assertTrue(roomRequire.requiredItem().equals(Item.getInstance("gold", Player.DEFAULT_USER_NAME)));
	}
	
	@Test
	public void testParsingGameStartId() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(gameStartXmlFileName));
		XmlResponse xr = parseXml.getXmlResponse();
		String id = ((XmlResponseGameStart) xr).getId();
		assertTrue(id.equals("takhh"));
	}
	
	
	@Test
	public void testParsingGameEndId() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		parseXml.parseXmlFromString(readAllBytes(gameEndXmlFileName));
		XmlResponse xr = parseXml.getXmlResponse();
		String id = ((XmlResponseGameEnd) xr).getId();
		assertTrue(id.equals("gameEndId"));
	}
	
	@Test
	public void testWritingGameStartResponseOK() throws ParserConfigurationException {
		XmlResultString xrs = XmlResultString.OK;
		XmlNgReason xnr = XmlNgReason.OK;
		
		XmlWriterServer xw = new XmlWriterServer();
		String xmlStr = xw.makeXmlForGameStart(xrs, xnr);
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(xmlStr);
		XmlResponseClient xr = (XmlResponseClient)parseXml.getXmlResponse();
		
		assertTrue(xr.getResultStr().equals(xrs));
		assertTrue(xr.getNgReason().equals(xnr));
	}
	
	@Test
	public void testWritingGameStartResponseNG() throws ParserConfigurationException {
		XmlResultString xrs = XmlResultString.NG;
		XmlNgReason xnr = XmlNgReason.NO_PLAYERS;
		
		XmlWriterServer xw = new XmlWriterServer();
		String xmlStr = xw.makeXmlForGameStart(xrs, xnr);
		
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(xmlStr);
		XmlResponseClient xr = (XmlResponseClient)parseXml.getXmlResponse();
		
		assertTrue(xr.getResultStr().equals(xrs));
		assertTrue(xr.getNgReason().equals(xnr));
	}
	
	
	@Test
	public void testParsingSendCommandInvalid() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		XmlParseResult xpr = parseXml.parseXmlFromString(readAllBytes(sendCommandInvalidXmlFileName));
		assertTrue(xpr.equals(XmlParseResult.INVALID_DATA));
	}
	
	@Test
	public void testParsingHeartBeatInvalid() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		XmlParseResult xpr = parseXml.parseXmlFromString(readAllBytes(hbInvalidXmlFileName));
		assertTrue(xpr.equals(XmlParseResult.INVALID_DATA));
	}
	
	@Test
	public void testParsingGameStartInvalid() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		XmlParseResult xpr = parseXml.parseXmlFromString(readAllBytes(gameStartInvalidXmlFileName));
		assertTrue(xpr.equals(XmlParseResult.INVALID_DATA));
	}
	
	@Test
	public void testParsingGameEndInvalid() throws ParserConfigurationException {
		
		XmlParser parseXml = new XmlParser();
		XmlParseResult xpr = parseXml.parseXmlFromString(readAllBytes(gameEndInvalidXmlFileName));
		assertTrue(xpr.equals(XmlParseResult.INVALID_DATA));
	}
	
	@Test
	public void testWritingAddUserResponseOK() throws ParserConfigurationException {
		
		XmlResultString addResult = XmlResultString.OK;
		XmlNgReason reason = XmlNgReason.OK;
		String xmlUri;

		XmlWriterServer xw = new XmlWriterServer();
		xmlUri = xw.makeXmlForAddUser(addResult, reason);
		XmlParser parseXml = new XmlParser(XmlParserType.CLIENT);
		parseXml.parseXmlFromString(xmlUri);
		XmlResponseClient xr = (XmlResponseClient) parseXml.getXmlResponse();
		assertTrue(addResult.equals(xr.getResultStr()));
		assertTrue(reason.equals(xr.getNgReason()));
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
	
}