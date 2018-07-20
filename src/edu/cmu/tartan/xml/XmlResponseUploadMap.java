package edu.cmu.tartan.xml;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlResponseUploadMap extends XmlResponse{

	XmlParseResult xmlParseResult; 

	XmlResponseUploadMap(){
		xmlParseResult = XmlParseResult.SUCCESS; 
	}
	
	@Override
	public void makeResponseXmlString() {
		
		gameInterface.info("makeResponseXmlString");
		 
		try {
			 
			Element messageElement = startWritingXml();
			Element gameInfo = addChildElement(messageElement, "game_info");
			
			if(xmlParseResult.equals( XmlParseResult.SUCCESS )) {
				setAttributeToElement(gameInfo, "result", "OK");
				setAttributeToElement(gameInfo, "ng_reason", "-");
			}
			else {
				setAttributeToElement(gameInfo, "result", "NG");
				setAttributeToElement(gameInfo, "ng_reason", xmlParseResult.name());
			}
			
		} catch (ParserConfigurationException e) {
			gameInterface.severe("ParserConfigurationException");
		} 
		
		responseXmlToClient = convertDocumentToString(docReply);
		gameInterface.info(responseXmlToClient);
	}

	@Override
	public XmlParseResult doYourJob(Document doc) {
		
		XmlParseResult result; 
	
		result = parsingRoomInfo(doc);
		if(result.equals(XmlParseResult.SUCCESS))
			result = parsingGoalInfo(doc);
		
		xmlParseResult = result; 
		
		makeResponseXmlString(); 
		
		return result;
	}
	
	public XmlParseResult parsingRoomInfo(Document doc) {
		
		int i;
		int nodeLength;
		int roomCnt;
		NodeList nList;
		
		nList = getNodeListOfGivenTag("room_info", doc);
		roomCnt = Integer.parseInt(getAttributeValueAtNthTag("room_cnt", nList, 0));	//room_cnt should be unique. 
	
		nList = getNodeListOfGivenTag("room", doc);
		nodeLength = nList.getLength();		//number of tag

		if(nodeLength != roomCnt) {
			return XmlParseResult.INVALID_DATA;
		}
		
		for(i=0; i<roomCnt; i++) {
			
			/*
			 * TODO : data to object
			gameInterface.info(getAttributeValueAtNthTag("index", nList, i));
			gameInterface.info(getAttributeValueAtNthTag("type", nList, i));
			gameInterface.info(getAttributeValueAtNthTag("north", nList, i));
			gameInterface.info(getAttributeValueAtNthTag("south", nList, i));
			gameInterface.info(getAttributeValueAtNthTag("west", nList, i));
			gameInterface.info(getAttributeValueAtNthTag("east", nList, i));
			gameInterface.info(getAttributeValueAtNthTag("item_list", nList, i));
			*/
		}
		
		return XmlParseResult.SUCCESS;
	}
	
	public XmlParseResult parsingGoalInfo(Document doc) {

		int i;
		int nodeLength;
		int goalCnt;
		NodeList nList;
		
		nList = getNodeListOfGivenTag("goal_info", doc);
		goalCnt = Integer.parseInt(getAttributeValueAtNthTag("goal_cnt", nList, 0));	//room_cnt should be unique. 
	
		nList = getNodeListOfGivenTag("goal", doc);
		nodeLength = nList.getLength();		//number of tag

		if(nodeLength != goalCnt) {
			return XmlParseResult.INVALID_DATA;
		}
		
		for(i=0; i<goalCnt; i++) {
			
			/*
			 * TODO : data to object 
			gameInterface.info(getAttributeValueAtNthTag("index", nList, i));
			gameInterface.info(getAttributeValueAtNthTag("type", nList, i));
			gameInterface.info(getAttributeValueAtNthTag("object", nList, i));
			
			*/
		}
		
		return XmlParseResult.SUCCESS;		
	}
	

}
