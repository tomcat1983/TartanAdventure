package edu.cmu.tartan.xml;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseLogin extends XmlResponse {

	private String idStr;
	private String pwStr;
	private XmlLoginRole role;
	private XmlResultString loginResult; 
	private XmlNgReason reason; 	
	
	public XmlResponseLogin() {
		msgType = XmlMessageType.REQ_LOGIN;
	}
	
	@Override
	public String makeResponseXmlString() {
		 
		//<login_info login_result="OK" ng_reason="-" role="player"/>
		XmlWriter xmlWriter; 

		try {
			
			xmlWriter = new XmlWriter(); 
			xmlWriter.startWritingXml(msgType, "server", "client");
			xmlWriter.addChildElement("login_info");
			
			xmlWriter.setAttributeToElement("result", loginResult.name());
			xmlWriter.setAttributeToElement("ng_reason", reason.name());

			responseXml = xmlWriter.convertDocumentToString();
			
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		} 
		
		gameLogger.info(responseXml);
		
		return responseXml; 
	}
	
	public void setLoginResult (XmlResultString loginResult, XmlNgReason reason) {
		this.loginResult = loginResult; 
		this.reason = reason; 
	}
	
	
	public String getId() {
		return idStr; 
	}
	
	public String getPw() {
		return pwStr; 
	}
	
	public XmlLoginRole getRole() {
		return role; 
	}

	@Override
	public XmlParseResult doYourJob(Document doc) {
		
		return parsingLoginInfo(doc);
	}
	

	public XmlParseResult parsingLoginInfo(Document doc) {
		

		NodeList nList;
		XmlParseResult result = XmlParseResult.SUCCESS; 
		
		nList = getNodeListOfGivenTag("login_info", doc);
		idStr = getAttributeValueAtNthTag("id", nList, 0);	//id should be unique. 
		pwStr = getAttributeValueAtNthTag("pw", nList, 0);	//pw should be unique. encrypted.  
		role = XmlLoginRole.valueOf(getAttributeValueAtNthTag("role", nList, 0));	//role should be unique. 
		
		if(idStr == null || pwStr == null || role == null) {
			return XmlParseResult.INVALID_DATA;
		}
		
		return result;
	}
	
}
