package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseCommand extends XmlResponse {

	private String commandStr;
	private String id;

	/**
	 *
	 */
	public XmlResponseCommand() {
		msgType = XmlMessageType.SEND_COMMAND;
	}

	/**
	 * @return
	 */
	public String getCmd() {
		return commandStr;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.tartan.xml.XmlResponse#doYourJob(org.w3c.dom.Document)
	 */
	@Override
	public XmlParseResult doYourJob(Document doc) {

		return parsingCommandInfo(doc);
	}


	/**
	 * @param doc
	 * @return
	 */
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
