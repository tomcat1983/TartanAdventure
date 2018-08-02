package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseLogin extends XmlResponse {

	private String idStr;
	private String pwStr;
	private XmlLoginRole role;

	public XmlResponseLogin() {
		msgType = XmlMessageType.REQ_LOGIN;
	}


	/**
	 * @return
	 */
	public String getId() {
		return idStr;
	}

	/**
	 * @return
	 */
	public String getPw() {
		return pwStr;
	}

	/**
	 * @return
	 */
	public XmlLoginRole getRole() {
		return role;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.tartan.xml.XmlResponse#doYourJob(org.w3c.dom.Document)
	 */
	@Override
	public XmlParseResult doYourJob(Document doc) {

		return parsingLoginInfo(doc);
	}


	/**
	 * @param doc
	 * @return
	 */
	public XmlParseResult parsingLoginInfo(Document doc) {

		NodeList nList;
		XmlParseResult result = XmlParseResult.SUCCESS;

		nList = getNodeListOfGivenTag("login_info", doc);
		idStr = getAttributeValueAtNthTag("id", nList, 0);	//id should be unique.
		pwStr = getAttributeValueAtNthTag("pw", nList, 0);	//pw should be unique. encrypted.
		role = XmlLoginRole.valueOf(getAttributeValueAtNthTag("role", nList, 0).toUpperCase());	//role should be unique.

		if(idStr == null || pwStr == null || role == null) {
			return XmlParseResult.INVALID_DATA;
		}

		return result;
	}

}
