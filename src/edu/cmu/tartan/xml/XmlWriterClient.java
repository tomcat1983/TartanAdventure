package edu.cmu.tartan.xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

public class XmlWriterClient extends XmlWriter {

	private static final String PARSER_EXCEPTION = "ParserConfigurationException";
	private static final String SERVER_STR = "server";
	private static final String CLIENT_STR = "client";
	
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
	
	public String makeXmlForHeartBeat(String id) {
		String xmlString = null;
		
		try {
			//	<common_info user_id="takhh" /> <!-- heart beat message from client every 1 min -->
			startWritingXml(XmlMessageType.HEART_BEAT, CLIENT_STR, SERVER_STR); 			
			addChildElement("common_info");
			setAttributeToElement("user_id", id);
			
			xmlString = convertDocumentToString();
			
		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		} 
		
		gameLogger.info(xmlString);
		
		return xmlString; 
	}
	
	public String makeXmlForGameStartEnd(XmlMessageType msgType, String id) {
		String xmlString = null;
		
		if(msgType.equals(XmlMessageType.REQ_GAME_START) || msgType.equals(XmlMessageType.REQ_GAME_END))
		{
			try {
				//<common_info user_id="takhh" />	
				startWritingXml(msgType, CLIENT_STR, SERVER_STR); 			
				addChildElement("common_info");
				setAttributeToElement("user_id", id);
				
				xmlString = convertDocumentToString();
				
			} catch (ParserConfigurationException e) {
				gameLogger.severe(PARSER_EXCEPTION);
			} 
			
			gameLogger.info(xmlString);
		}
	
		return xmlString; 
	}
	
	public String makeXmlForCommand(String commandStr) {
		String xmlString = null;
		
		try {
			//	<common_info user_id="takhh" /> <!-- heart beat message from client every 1 min -->
			startWritingXml(XmlMessageType.SEND_COMMAND, CLIENT_STR, SERVER_STR); 			
			addChildElement("command");
			setAttributeToElement("text", commandStr);
			
			xmlString = convertDocumentToString();
			
		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		} 
		
		gameLogger.info(xmlString);
		
		return xmlString; 
	}
	
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
        content = new String (Files.readAllBytes( Paths.get(filePath)));
	    
	    return content;
	}
}
