package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseGameStart extends XmlResponse {

	private String idStr;

	
	public XmlResponseGameStart() {
		msgType = XmlMessageType.REQ_GAME_START;
	}
	

	public String getId() {
		return idStr; 
	}
	

	@Override
	public XmlParseResult doYourJob(Document doc) {
		
		return parsingUserInfo(doc);
	}
	

	public XmlParseResult parsingUserInfo(Document doc) {

		NodeList nList;
		XmlParseResult result = XmlParseResult.SUCCESS; 
		
		//	<common_info user_id="takhh" />	
		nList = getNodeListOfGivenTag("common_info", doc);
		idStr = getAttributeValueAtNthTag("user_id", nList, 0);	//id should be unique. 

		if(idStr == null ) {
			return XmlParseResult.INVALID_DATA;
		}
		
		return result;
	}
	
}
