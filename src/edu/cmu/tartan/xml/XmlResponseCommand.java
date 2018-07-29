package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseCommand extends XmlResponse {

	private String commandStr;

	public XmlResponseCommand() {
		msgType = XmlMessageType.SEND_COMMAND;
	}
		
	
	public String getCmd() {
		return commandStr; 
	}
	

	@Override
	public XmlParseResult doYourJob(Document doc) {
		
		return parsingCommandInfo(doc);
	}
	

	public XmlParseResult parsingCommandInfo(Document doc) {
		

		NodeList nList;
		XmlParseResult result = XmlParseResult.SUCCESS; 
		
		nList = getNodeListOfGivenTag("command", doc);
		commandStr = getAttributeValueAtNthTag("text", nList, 0);	//id should be unique. 
		
		if(commandStr == null) {
			return XmlParseResult.INVALID_DATA;
		}
		
		return result;
	}
	
}
