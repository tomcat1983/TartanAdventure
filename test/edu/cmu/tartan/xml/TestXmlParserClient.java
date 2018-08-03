package edu.cmu.tartan.xml;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;


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


	@Test
	public void testWritingXmlLogin() throws ParserConfigurationException {

		final String ID_STR = "loginUserId";
		final String PW_STR = "loginUserPw";
		XmlLoginRole xlr = XmlLoginRole.PLAYER;

		XmlWriterClient xw = new XmlWriterClient();
		String xmlStr = xw.makeXmlForLogin(ID_STR, PW_STR, xlr);

		XmlParser xp = new XmlParser(XmlParserType.SERVER);
		xp.parseXmlFromString(xmlStr);

		XmlResponseLogin xr = (XmlResponseLogin) xp.getXmlResponse();
		assertTrue(xr.getId().equals(ID_STR));
		assertTrue(xr.getPw().equals(PW_STR));
		assertTrue(xr.getRole().equals(xlr));
	}

	@Test
	public void testWritingXmlForAddUser() throws ParserConfigurationException {

		final String ID_STR = "addUserId";
		final String PW_STR = "addUserPw";

		XmlWriterClient xw = new XmlWriterClient();
		String xmlStr = xw.makeXmlForAddUser(ID_STR, PW_STR);

		XmlParser xp = new XmlParser(XmlParserType.SERVER);
		xp.parseXmlFromString(xmlStr);

		XmlResponseAddUser xr = (XmlResponseAddUser) xp.getXmlResponse();
		assertTrue(xr.getId().equals(ID_STR));
		assertTrue(xr.getPw().equals(PW_STR));
	}

	@Disabled
	@Test
	public void testWritingXmlForUploadMap() {

		final String MAP_STR = "userMap.xml";

		XmlWriterClient xw = new XmlWriterClient();
		String xmlStr = xw.makeXmlForUploadMap(MAP_STR);
		String fileRead = readAllBytes(MAP_STR);
		assertTrue(xmlStr.equals(fileRead));
	}

	@Test
	public void testWritingXmlForGameStart() throws ParserConfigurationException {

		final String ID_STR = "startId";

		XmlWriterClient xw = new XmlWriterClient();
		String xmlStr = xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_START, ID_STR);

		XmlParser xp = new XmlParser(XmlParserType.SERVER);
		xp.parseXmlFromString(xmlStr);

		XmlResponseGameStart xr = (XmlResponseGameStart) xp.getXmlResponse();
		assertTrue(xr.getId().equals(ID_STR));
	}

	@Test
	public void testWritingXmlForGameEnd() throws ParserConfigurationException {

		final String ID_STR = "endId";

		XmlWriterClient xw = new XmlWriterClient();
		String xmlStr = xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_END, ID_STR);

		XmlParser xp = new XmlParser(XmlParserType.SERVER);
		xp.parseXmlFromString(xmlStr);

		XmlResponseGameEnd xr = (XmlResponseGameEnd) xp.getXmlResponse();
		assertTrue(xr.getId().equals(ID_STR));
	}

	@Test
	public void testWritingXmlForHeartBeat() throws ParserConfigurationException {

		final String ID_STR = "heartBeatId";

		XmlWriterClient xw = new XmlWriterClient();
		String xmlStr = xw.makeXmlForHeartBeat(ID_STR);

		XmlParser xp = new XmlParser(XmlParserType.SERVER);
		xp.parseXmlFromString(xmlStr);

		XmlResponseHeartBeat xr = (XmlResponseHeartBeat) xp.getXmlResponse();
		assertTrue(xr.getId().equals(ID_STR));
	}

	@Test
	public void testWritingXmlForSendCommand() throws ParserConfigurationException {

		final String COMMAND = "go south";
		final String ID_STR = "commandId";

		XmlWriterClient xw = new XmlWriterClient();
		String commandXml = xw.makeXmlForCommand(ID_STR, COMMAND);

		XmlParser xp = new XmlParser(XmlParserType.SERVER);
		xp.parseXmlFromString(commandXml);

		XmlResponseCommand xr = (XmlResponseCommand) xp.getXmlResponse();
		assertTrue(xr.getCmd().equals(COMMAND));
		assertTrue(xr.getId().equals(ID_STR));
	}

	@Test
	public void testParsingEventMessage() throws ParserConfigurationException {

		final String MESSAGE = "hello client\nthis is message from server";
		final String ID_STR = "eventId";

		XmlWriterServer xw = new XmlWriterServer();
		String eventMsgXml = xw.makeXmlForEventMessage(ID_STR, MESSAGE);

		XmlParser xp = new XmlParser(XmlParserType.CLIENT);
		xp.parseXmlFromString(eventMsgXml);

		XmlResponseClient xr = (XmlResponseClient) xp.getXmlResponse();
		assertTrue(xr.getEventMsg().equals(MESSAGE));
		assertTrue(xr.getId().equals(ID_STR));
	}

	@Test
	public void testWritingXmlForGameEndWin() throws ParserConfigurationException {

		final String RESULT = "win";
		final String ID_STR = "gameEndId";
		final String TEXT_STR = "cong";

		XmlWriterServer xw = new XmlWriterServer();
		String commandXml = xw.makeXmlForGameEnd(ID_STR, RESULT, TEXT_STR);

		XmlParser xp = new XmlParser(XmlParserType.CLIENT);
		xp.parseXmlFromString(commandXml);

		XmlResponseClient xr = (XmlResponseClient) xp.getXmlResponse();
		assertTrue(xr.getGameResult().equals(RESULT));
		assertTrue(xr.getId().equals(ID_STR));
		assertTrue(xr.getGameText().equals(TEXT_STR));

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
