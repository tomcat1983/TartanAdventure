package edu.cmu.tartan.xml;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseAddUser extends XmlResponse {

	private String idStr;
	private String pwStr;
	
	private XmlResultString addResult; 
	private XmlNgReason reason; 	
	
	public XmlResponseAddUser() {
		msgType = XmlMessageType.ADD_USER;
	}
	
	@Override
	public String makeResponseXmlString() {
	
		//<user_info add_result="OK" ng_reason="-"  />
		XmlWriter xmlWriter; 

		try {
			
			xmlWriter = new XmlWriter(); 
			xmlWriter.startWritingXml(msgType, "server", "client");
			xmlWriter.addChildElement("user_info");
			
			xmlWriter.setAttributeToElement("add_result", addResult.name());
			xmlWriter.setAttributeToElement("ng_reason", reason.name());

			responseXml = xmlWriter.convertDocumentToString();
			
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		} 
		
		gameLogger.info(responseXml);
		
		return responseXml;  
	}

	@Override
	public XmlParseResult doYourJob(Document doc) {
		
		return parsingUserInfo(doc);
	}

	public void setAddUserResult (XmlResultString loginResult, XmlNgReason reason) {
		this.addResult = loginResult; 
		this.reason = reason; 
	}
	
	
	public String getId() {
		return idStr; 
	}
	
	public String getPw() {
		return pwStr; 
	}	

	public XmlParseResult parsingUserInfo(Document doc) {
		

		NodeList nList;
		XmlParseResult result = XmlParseResult.SUCCESS; 
		
		//<user_info id="takhh" pw="awefaweg14ro4aw3"/>	<!-- password is encrypted -->
		nList = getNodeListOfGivenTag("user_info", doc);
		idStr = getAttributeValueAtNthTag("id", nList, 0);	//id should be unique. 
		pwStr = getAttributeValueAtNthTag("pw", nList, 0);	//pw should be unique. encrypted.  

		if(idStr == null || pwStr == null) {
			return XmlParseResult.INVALID_DATA;
		}
		
		return result;
	}
}
