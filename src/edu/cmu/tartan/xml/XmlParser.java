package edu.cmu.tartan.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.cmu.tartan.GameConfiguration;


public class XmlParser {

	private final DocumentBuilderFactory dbFactory;
	private final DocumentBuilder dBuilder;
	private Document doc;
	private NodeList nList;
	private XmlResponse xmlResponse; 	//server will refer this object
	private XmlParserType parserType;

	/**
	 * Game logger for game log
	 */
	protected Logger gameLogger = Logger.getGlobal();

	/**
	 * @throws ParserConfigurationException
	 */
	public XmlParser() throws ParserConfigurationException {
		this(XmlParserType.SERVER);		// default is parser for server
	}

	/**
	 * @param parserType
	 * @throws ParserConfigurationException
	 */
	public XmlParser(XmlParserType parserType) throws ParserConfigurationException {

		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		doc = null;
		nList = null;
		this.parserType = parserType;
	}

	/**
	 * @return
	 */
	public NodeList getNodeList() {
		return nList;
	}

	/**
	 * @return
	 */
	public XmlResponse getXmlResponse() {
		return xmlResponse;
	}

	/**
	 * @param xmlUri
	 * @return
	 */
	public XmlParseResult parseXmlFromString(String xmlUri) {

		XmlParseResult result = XmlParseResult.SUCCESS;

		try {
			result = parseXmlFromStringThrowException(xmlUri);
		} catch (Exception e) {
			gameLogger.severe(e.getClass().getSimpleName());
			result = XmlParseResult.INVALID_XML;
		}

		return result;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public XmlParseResult parseXmlFromFile(String fileName) {

		XmlParseResult result = XmlParseResult.SUCCESS;

		try {
			result = parseXmlFromFileThrowException(fileName);
		} catch (Exception e) {
			gameLogger.severe(e.getClass().getSimpleName());
			result = XmlParseResult.INVALID_XML;
		}

		return result;
	}

	/**
	 * @param xmlUri
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public XmlParseResult parseXmlFromStringThrowException (String xmlUri)throws SAXException, IOException {

	    InputSource is = new InputSource(new StringReader(xmlUri));
		doc = dBuilder.parse(is);

		return parsingXML();
	}

	/**
	 * @param fileName
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public XmlParseResult parseXmlFromFileThrowException(String fileName) throws SAXException, IOException {

		File fXmlFile = new File(fileName);
		doc = dBuilder.parse(fXmlFile);

		return parsingXML();
	}

	private XmlParseResult parsingXML() {

		XmlParseResult parseResult;

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		//NodeList
		nList = doc.getChildNodes();
		String messageType = getMessageType();
		if(messageType == null)	//incase there is no <message type=" xxx" >
			return XmlParseResult.UNKNOWN_MESSAGE;

		if(parserType.equals(XmlParserType.SERVER))
			parseResult = processMessage(messageType);
		else
			parseResult = processMessageClient(messageType);

		return parseResult;

	}

	/**
	 * @return
	 */
	@Nullable
	public String getMessageType() {
		return getValueByTagAndAttribute("message", "type");
	}

	/**
	 * @param messageType
	 * @return
	 */
	public XmlParseResult processMessage(String messageType) {

		XmlParseResult result;

		if(messageType.equals(XmlMessageType.UPLOAD_MAP_DESIGN.name())) {
			xmlResponse = new XmlResponseUploadMap();
		}
		else if(messageType.equals(XmlMessageType.REQ_LOGIN.name())) {
			xmlResponse = new XmlResponseLogin();
		}
		else if(messageType.equals(XmlMessageType.ADD_USER.name())) {
			xmlResponse = new XmlResponseAddUser();
		}
		else if(messageType.equals(XmlMessageType.REQ_GAME_START.name())) {
			xmlResponse = new XmlResponseGameStart();
		}
		else if(messageType.equals(XmlMessageType.REQ_GAME_END.name())) {
			xmlResponse = new XmlResponseGameEnd();
		}
		else if(messageType.equals(XmlMessageType.SEND_COMMAND.name())) {
			xmlResponse = new XmlResponseCommand();
		}
		else if(messageType.equals(XmlMessageType.HEART_BEAT.name())) {
			xmlResponse = new XmlResponseHeartBeat();
		}
		else {
			return XmlParseResult.UNKNOWN_MESSAGE;
		}

		result = xmlResponse.doYourJob(doc);

		return result;

	}

	/**
	 * @param messageType
	 * @return
	 */
	public XmlParseResult processMessageClient(String messageType) {

		for (XmlMessageType msgType : XmlMessageType.values()) {
			if(messageType.equals(msgType.name())) {
				xmlResponse = new XmlResponseClient(msgType);
				return xmlResponse.doYourJob(doc);
			}
		}

		return XmlParseResult.UNKNOWN_MESSAGE;
	}


	/**
	 * @param messageType
	 * @param userId
	 * @return
	 */
	@Nullable
	public GameConfiguration processMessageReturnGameConfiguration(String messageType, String userId) {

		// If Game variable is included, only support UPLOAD_MAP_DESIGN
		if(messageType.equals(XmlMessageType.UPLOAD_MAP_DESIGN.name())) {
			xmlResponse = new XmlResponseUploadMap();
			((XmlResponseUploadMap) xmlResponse).setUserId(userId);
		}
		else {
			return null;
		}

		return ((XmlResponseUploadMap) xmlResponse).doYourJobReturnGameConfiguration(doc);
	}

	/**
	 * @param tagName
	 * @param attrName
	 * @return
	 */
	@Nullable
	public String getValueByTagAndAttribute(String tagName, String attrName) {

		String result = null;

		nList = doc.getElementsByTagName(tagName);
		int nodeLength = nList.getLength();		//number of tag

		for(int i=0; i<nodeLength; i++) {

			Node node = nList.item(i);

			if (node.hasAttributes()) {

				NamedNodeMap attributeMap = node.getAttributes();
				int attributeLength = attributeMap.getLength();

				for (int j=0; j<attributeLength; j++) {

					Node attNode = attributeMap.item(j);
					if(attNode.getNodeName().equals(attrName))
						result = attNode.getNodeValue();
				}
			}
		}

		return result;
	}


	/**
	 * @param userId
	 * @return
	 */
	@Nullable
	public GameConfiguration loadGameMapXml(String userId) {

		return parseXmlFromFileReturnGameConfiguration("gameMap.xml", userId);
	}

	/**
	 * @param gMode
	 * @param userId
	 * @return
	 */
	public GameConfiguration loadGameMapXml(GameMode gMode, String userId) {

		if(gMode.equals(GameMode.LOCAL))
			return parseXmlFromFileReturnGameConfiguration("localMap.xml", userId);
		else
			return parseXmlFromFileReturnGameConfiguration("gameMap.xml", userId);
	}


	/**
	 * @param fileName
	 * @param userId
	 * @return
	 */
	@Nullable
	public GameConfiguration parseXmlFromFileReturnGameConfiguration(String fileName, String userId) {

		try {
			File fXmlFile = new File(fileName);
			doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			//NodeList
			nList = doc.getChildNodes();
			String messageType = getMessageType();
			if(messageType == null)	//incase there is no <message type=" xxx" >
				return null;

			return processMessageReturnGameConfiguration(messageType, userId);

		} catch (Exception e) {
			gameLogger.severe("parseXmlFromString throw exception :" + e.getClass().getSimpleName());
		}

		return null;
	}
}