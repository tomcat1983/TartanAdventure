package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author youngwoo.jin
 *
 */
public class XmlResponseGameStart extends XmlResponse {

	private String idStr;


	/**
	 *
	 */
	public XmlResponseGameStart() {
		msgType = XmlMessageType.REQ_GAME_START;
	}


	/**
	 * @return
	 */
	public String getId() {
		return idStr;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.tartan.xml.XmlResponse#doYourJob(org.w3c.dom.Document)
	 */
	@Override
	public XmlParseResult doYourJob(Document doc) {

		return parsingUserInfo(doc);
	}

	/**
	 * @param doc
	 * @return
	 */
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
