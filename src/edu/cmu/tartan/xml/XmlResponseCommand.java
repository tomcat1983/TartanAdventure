package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseCommand extends XmlResponse {

	private String commandStr;
	private String id;

	public XmlResponseCommand() {
		msgType = XmlMessageType.SEND_COMMAND;
	}
		
	
	public String getCmd() {
		return commandStr; 
	}
	
	public String getId() {
		return id; 
	}

	@Override
	public XmlParseResult doYourJob(Document doc) {
		
		return parsingCommandInfo(doc);
	}
	

	public XmlParseResult parsingCommandInfo(Document doc) {
		

		NodeList nList;
		XmlParseResult result = XmlParseResult.SUCCESS; 
		
		//	<command user_id="takakak" text="go south" />
		nList = getNodeListOfGivenTag("command", doc);
		commandStr = getAttributeValueAtNthTag("text", nList, 0);	//text should be unique. 
		id = getAttributeValueAtNthTag("user_id", nList, 0);		//id should be unique. 

		if(commandStr == null || id == null ) {
			return XmlParseResult.INVALID_DATA;
		}
		
		return result;
	}
	
}
