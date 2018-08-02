package edu.cmu.tartan.xml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

public class XmlWriterClient extends XmlWriter {

	private static final String PARSER_EXCEPTION = "ParserConfigurationException";
	private static final String SERVER_STR = "server";
	private static final String CLIENT_STR = "client";
	private static final String USER_ID_STR = "user_id";

	/**
	 * @param id
	 * @param pw
	 * @param role
	 * @return
	 */
	public String makeXmlForLogin(String id, String pw, XmlLoginRole role) {
		String xmlString = null;

		try {
			//<login_info id="takhh" pw="awefaweg14ro4aw3"/>	<!-- password is encrypted -->
			startWritingXml(XmlMessageType.REQ_LOGIN, CLIENT_STR, SERVER_STR);
			addChildElement("login_info");
			setAttributeToElement("id", id);
			setAttributeToElement("pw", pw);
			setAttributeToElement("role", role.name().toLowerCase());

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param id
	 * @param pw
	 * @return
	 */
	public String makeXmlForAddUser(String id, String pw) {
		String xmlString = null;

		try {
			//<user_info id="takhh" pw="awefaweg14ro4aw3"/>	<!-- password is encrypted -->
			startWritingXml(XmlMessageType.ADD_USER, CLIENT_STR, SERVER_STR);
			addChildElement("user_info");
			setAttributeToElement("id", id);
			setAttributeToElement("pw", pw);

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param id
	 * @return
	 */
	public String makeXmlForHeartBeat(String id) {
		String xmlString = null;

		try {
			//	<common_info user_id="takhh" /> <!-- heart beat message from client every 1 min -->
			startWritingXml(XmlMessageType.HEART_BEAT, CLIENT_STR, SERVER_STR);
			addChildElement("common_info");
			setAttributeToElement(USER_ID_STR, id);

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param msgType
	 * @param id
	 * @return
	 */
	public String makeXmlForGameStartEnd(XmlMessageType msgType, String id) {
		String xmlString = null;

		if(msgType.equals(XmlMessageType.REQ_GAME_START) || msgType.equals(XmlMessageType.REQ_GAME_END))
		{
			try {
				//<common_info user_id="takhh" />
				startWritingXml(msgType, CLIENT_STR, SERVER_STR);
				addChildElement("common_info");
				setAttributeToElement(USER_ID_STR, id);

				xmlString = convertDocumentToString();

			} catch (ParserConfigurationException e) {
				gameLogger.severe(PARSER_EXCEPTION);
			}

			gameLogger.info(xmlString);
		}

		return xmlString;
	}

	/**
	 * @param id
	 * @param commandStr
	 * @return
	 */
	public String makeXmlForCommand(String id, String commandStr) {
		String xmlString = null;

		try {
			//<command user_id="takakak" text="go south" />
			startWritingXml(XmlMessageType.SEND_COMMAND, CLIENT_STR, SERVER_STR);
			addChildElement("command");
			setAttributeToElement(USER_ID_STR, id);
			setAttributeToElement("text", commandStr);

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public String makeXmlForUploadMap(String fileName) {
		String xmlString = null;

		try{
			xmlString = readAllBytes(fileName);
		}
		catch (IOException e) {
			gameLogger.severe("IOException");
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	private String readAllBytes(String filePath) throws IOException{
	    String content = "";
        content = new String (Files.readAllBytes( Paths.get(filePath)), StandardCharsets.UTF_8);

	    return content;
	}
}
