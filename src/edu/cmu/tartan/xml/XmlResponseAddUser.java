package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseAddUser extends XmlResponse {

	private String idStr;
	private String pwStr;


	/**
	 *
	 */
	public XmlResponseAddUser() {
		msgType = XmlMessageType.ADD_USER;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.tartan.xml.XmlResponse#doYourJob(org.w3c.dom.Document)
	 */
	@Override
	public XmlParseResult doYourJob(Document doc) {

		return parsingUserInfo(doc);
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
	 * @param doc
	 * @return
	 */
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
