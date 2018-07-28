package edu.cmu.tartan.xml;

import org.w3c.dom.Document;

public class XmlResponseAddUser extends XmlResponse {

	public XmlResponseAddUser() {
		msgType = XmlMessageType.ADD_USER;
	}
	
	@Override
	public String makeResponseXmlString() {
	
		return responseXml; 
	}

	@Override
	public XmlParseResult doYourJob(Document doc) {
		// TODO Auto-generated method stub
		return XmlParseResult.SUCCESS;
	}
	

}
