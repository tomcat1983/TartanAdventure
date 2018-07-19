package edu.cmu.tartan.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.cmu.tartan.GameInterface;

enum XmlParseResult {
	SUCCESS, INVALID_XML, INVALID_DATA
}

public class XmlParser {

	private final DocumentBuilderFactory dbFactory;
	private final DocumentBuilder dBuilder;
	private Document doc;
	
	public NodeList nList;
	
	/**
	 * Game interface for game message and log
	 */
	protected GameInterface gameInterface = GameInterface.getInterface();

	public XmlParser() throws ParserConfigurationException{

		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		doc = null; 
		nList = null; 
	}

	public XmlParseResult parseXmlFromString(String xmlUri) {

		XmlParseResult result = XmlParseResult.SUCCESS;
		
		try {
			parseXmlFromStringThrowException(xmlUri);
		} catch (Exception e) {
			gameInterface.severe("parseXmlFromString throw exception :" + e.getClass().getSimpleName());
			result = XmlParseResult.INVALID_XML;
		}
		
		return result;	
	}
	
	public void parseXmlFromStringThrowException (String xmlUri)throws SAXException, IOException {

	    InputSource is = new InputSource(new StringReader(xmlUri));
		
		doc = dBuilder.parse(is);
		
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		
		gameInterface.info("From String, Root element :" + doc.getDocumentElement().getNodeName());
		//NodeList 
		nList = doc.getChildNodes();
	}


	public XmlParseResult parseXmlFromFile(String fileName){
		
		XmlParseResult result = XmlParseResult.SUCCESS;

		try {
			parseXmlFromFileThrowException(fileName);
		} catch (Exception e) {
			gameInterface.severe("parseXmlFromString throw exception :" + e.getClass().getSimpleName());
			result = XmlParseResult.INVALID_XML;
		}

		return result;		
	}
	
	
	public void parseXmlFromFileThrowException(String fileName) throws SAXException, IOException{

		File fXmlFile = new File(fileName);
		doc = dBuilder.parse(fXmlFile);
		
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		gameInterface.info("From File, Root element :" + doc.getDocumentElement().getNodeName());
		//NodeList 
		nList = doc.getChildNodes();
	}
	
	//must check null return value 
	public String getValueByTagAndAttribute(String tagName, String attrName) {
		
		String result = null;
		
		nList = doc.getElementsByTagName(tagName);
		int nodeLength = nList.getLength();		//number of tag
		StringBuilder sb = new StringBuilder(200);

		for(int i=0; i<nodeLength; i++){
			
			Node node = nList.item(i); 
			
			if (node.hasAttributes()){

				NamedNodeMap attributeMap = node.getAttributes();
				int attributeLength = attributeMap.getLength();
				
				for (int j=0; j<attributeLength; j++){

					Node attNode = attributeMap.item(j);
					if(attNode.getNodeName().equals(attrName))
						result = attNode.getNodeValue();
				}
			}
		}

		gameInterface.info(sb.toString());
		return result;
	}
	

	//for debugging, might be unused 
	@SuppressWarnings("unused")
	protected void printNodeInfo(Node node) {

		String value = "child node";
		short childType = getChildType(node);

		if (childType==Node.TEXT_NODE){
			value = node.getTextContent();
		}

		StringBuilder sb = new StringBuilder(200);
		sb.append("node name:")
		.append(node.getNodeName())
		.append(", node value:").append(value);


		if (node.hasAttributes()){

			NamedNodeMap attributeMap = node.getAttributes();
			int attributeLength = attributeMap.getLength();

			for (int i=0; i<attributeLength; i++){

				Node attNode = attributeMap.item(i);
				sb.append('\n')
				.append("attr name:").append(attNode.getNodeName())
				.append(", attr value:").append(attNode.getNodeValue());

			}

		}

		gameInterface.info(sb.toString());

		if (node.hasChildNodes()){

			NodeList nodeList = node.getChildNodes(); 
			int nodeLength = nodeList.getLength();

			for(int i=0; i<nodeLength; i++){

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

		for (int i=0; i<length; i++){

			Node childNode = nodeList.item(i);
			if (childNode.getNodeType()!=Node.TEXT_NODE)

				return childNode.getNodeType();
		}

		return Node.TEXT_NODE;
	}
}