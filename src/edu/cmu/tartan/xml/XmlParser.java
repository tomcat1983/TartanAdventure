package edu.cmu.tartan.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.GameInterface;



public class XmlParser {

	private final DocumentBuilderFactory dbFactory;
	private final DocumentBuilder dBuilder;
	private Document doc;
	private NodeList nList;
	
	/**
	 * Game interface for game message and log
	 */
	protected GameInterface gameInterface = GameInterface.getInterface();

	public XmlParser() throws ParserConfigurationException {

		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		doc = null; 
		nList = null; 
	}

	public NodeList getNodeList() {
		return nList;
	}
	
	public XmlParseResult parseXmlFromString(String xmlUri) {

		XmlParseResult result = XmlParseResult.SUCCESS;
		
		try {
			result = parseXmlFromStringThrowException(xmlUri);
		} catch (Exception e) {
			gameInterface.severe(e.getClass().getSimpleName());
			result = XmlParseResult.INVALID_XML;
		}
		
		return result;	
	}
	

	public XmlParseResult parseXmlFromFile(String fileName) {
		
		XmlParseResult result = XmlParseResult.SUCCESS;

		try {
			result = parseXmlFromFileThrowException(fileName);
		} catch (Exception e) {
			gameInterface.severe(e.getClass().getSimpleName());
			result = XmlParseResult.INVALID_XML;
		}

		return result;		
	}
	
	public XmlParseResult parseXmlFromStringThrowException (String xmlUri)throws SAXException, IOException {

	    InputSource is = new InputSource(new StringReader(xmlUri));
		doc = dBuilder.parse(is);
		
		return parsingXML();
	}
	
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
		
		parseResult = processMessage(messageType);
		
		return parseResult; 
			
	}
	
	@Nullable
	public String getMessageType() {
		
		return getValueByTagAndAttribute("message", "type");

	}
	
	public XmlParseResult processMessage(String messageType) {
		
		XmlResponse xmlResponse; 
		XmlParseResult result; 
		
		if(messageType.equals(XmlMessageType.UPLOAD_MAP_DESIGN.name())) {
			xmlResponse = new XmlResponseUploadMap(); 
			xmlResponse.setMsgType(XmlMessageType.UPLOAD_MAP_DESIGN);
		}
		else if(messageType.equals(XmlMessageType.REQ_LOGIN.name())) {
			xmlResponse = new XmlResponseLogin(); 
			xmlResponse.setMsgType(XmlMessageType.REQ_LOGIN);
		}
		else if(messageType.equals(XmlMessageType.ADD_USER.name())) {
			xmlResponse = new XmlResponseAddUser();
			xmlResponse.setMsgType(XmlMessageType.REQ_LOGIN);
		}
		else {
			return XmlParseResult.UNKNOWN_MESSAGE;
		}
		
		result = xmlResponse.doYourJob(doc);
				
		return result;
			
	}
	
	@Nullable
	public GameConfiguration processMessageReturnGameConfiguration(String messageType) {
		
		XmlResponseUploadMap xmlResponse; 
		
		// If Game variable is included, only support UPLOAD_MAP_DESIGN 
		if(messageType.equals(XmlMessageType.UPLOAD_MAP_DESIGN.name())) {
			xmlResponse = new XmlResponseUploadMap();
			xmlResponse.setMsgType(XmlMessageType.UPLOAD_MAP_DESIGN);
		}
		else {
			return null;
		}
		
		return xmlResponse.doYourJobReturnGameConfiguration(doc);
	}
	
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
	

	//for debugging, might be unused 
	protected void printNodeInfo(Node node) {

		String value = "child node";
		short childType = getChildType(node);

		if (childType==Node.TEXT_NODE) {
			value = node.getTextContent();
		}

		StringBuilder sb = new StringBuilder(200);
		sb.append("node name:")
		.append(node.getNodeName())
		.append(", node value:").append(value);


		if (node.hasAttributes()) {

			NamedNodeMap attributeMap = node.getAttributes();
			int attributeLength = attributeMap.getLength();

			for (int i=0; i<attributeLength; i++) {

				Node attNode = attributeMap.item(i);
				sb.append('\n')
				.append("attr name:").append(attNode.getNodeName())
				.append(", attr value:").append(attNode.getNodeValue());

			}

		}

		if (node.hasChildNodes()) {

			NodeList nodeList = node.getChildNodes(); 
			int nodeLength = nodeList.getLength();

			for(int i=0; i<nodeLength; i++) {

				Node childNode = nodeList.item(i);
				if (childNode.getNodeType()==Node.TEXT_NODE){
					continue;
				}

				printNodeInfo (nodeList.item(i));
			}
		}
	}

	private short getChildType(Node node) {

		NodeList nodeList = node.getChildNodes();
		int length = nodeList.getLength();

		for (int i=0; i<length; i++) {

			Node childNode = nodeList.item(i);
			if (childNode.getNodeType()!=Node.TEXT_NODE)
				return childNode.getNodeType();
		}

		return Node.TEXT_NODE;
	}
	
	@Nullable
	public GameConfiguration loadGameMapXml() {
		
		return parseXmlFromFileReturnGameConfiguration("gameMap.xml");
	}
	
	@Nullable
	public GameConfiguration parseXmlFromFileReturnGameConfiguration(String fileName) {
		
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
			
			return processMessageReturnGameConfiguration(messageType); 
			
		} catch (Exception e) {
			gameInterface.severe("parseXmlFromString throw exception :" + e.getClass().getSimpleName());
		}

		return null;		
	}
}