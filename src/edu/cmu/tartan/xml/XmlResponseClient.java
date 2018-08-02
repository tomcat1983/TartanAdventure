package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseClient extends XmlResponse {

	private XmlResultString resultStr;	//OK or NG
	private XmlNgReason ngReason;
	private static final String NG_REASON_STR = "ng_reason";
	private static final String RESULT_STR = "result";

	private String eventMsg;
	private String id;
	private String gameResult;
	private String gameText;


	/**
	 * @param msgType
	 */
	public XmlResponseClient(XmlMessageType msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return
	 */
	public String getGameText() {
		return gameText;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getGameResult() {
		return gameResult;
	}

	/**
	 * @return
	 */
	public XmlResultString getResultStr() {
		return resultStr;
	}

	/**
	 * @return
	 */
	public XmlNgReason getNgReason() {
		return ngReason;
	}

	/**
	 * @return
	 */
	public String getEventMsg() {
		return eventMsg;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.tartan.xml.XmlResponse#doYourJob(org.w3c.dom.Document)
	 */
	@Override
	public XmlParseResult doYourJob(Document doc) {

		if(msgType.equals(XmlMessageType.UPLOAD_MAP_DESIGN) ) {
			return parsingResultForMap(doc);
		}
		else if(msgType.equals(XmlMessageType.REQ_LOGIN)) {
			return parsingResultForLogin(doc);
		}
		else if(msgType.equals(XmlMessageType.ADD_USER)) {
			return parsingResultForAddUser(doc);
		}
		else if(msgType.equals(XmlMessageType.REQ_GAME_START)) {
			return parsingResultForReqGameStart(doc);
		}
		else if(msgType.equals(XmlMessageType.EVENT_MESSAGE)) {
			return parsingResultForEventMessage(doc);
		}
		else if(msgType.equals(XmlMessageType.GAME_END)) {
			return parsingResultForGameEnd(doc);
		}

		return XmlParseResult.UNKNOWN_MESSAGE;

	}

	private XmlParseResult parsingResultForGameEnd(Document doc) {

		//<common_info user_id="userId" />
		//<game_result result="win" text="cong"/>
		NodeList nList = getNodeListOfGivenTag("common_info", doc);
		id = getAttributeValueAtNthTag("user_id", nList, 0);	//id should be unique

		nList = getNodeListOfGivenTag("game_result", doc);
		gameResult = getAttributeValueAtNthTag(RESULT_STR, nList, 0);	//result should be unique
		gameText = getAttributeValueAtNthTag("text", nList, 0);

		if(id == null || gameResult == null )
			return XmlParseResult.INVALID_DATA;
		else
			return XmlParseResult.SUCCESS;
	}

	private XmlParseResult parsingResultForEventMessage(Document doc) {

		NodeList nList;

		//	<common_info user_id="userId" />
		//	<display text="Hello Client! \n This is a message from server." />
		nList = getNodeListOfGivenTag("common_info", doc);
		id = getAttributeValueAtNthTag("user_id", nList, 0);	//id should be unique

		nList = getNodeListOfGivenTag("display", doc);
		eventMsg = getAttributeValueAtNthTag("text", nList, 0);	//text should be unique

		if(id == null || eventMsg == null )
			return XmlParseResult.INVALID_DATA;
		else
			return XmlParseResult.SUCCESS;
	}

	private XmlParseResult parsingResultForReqGameStart(Document doc) {
		NodeList nList;

		//<game_start result="OK" ng_reason="OK" />
		nList = getNodeListOfGivenTag("game_start", doc);
		parsingResultAndNgReason(RESULT_STR, NG_REASON_STR, nList);

		if(resultStr == null || ngReason == null)
			return XmlParseResult.INVALID_DATA;
		else
			return XmlParseResult.SUCCESS;
	}

	private XmlParseResult parsingResultForAddUser(Document doc) {
		NodeList nList;

		//<user_info add_result="OK" ng_reason="-"  />
		nList = getNodeListOfGivenTag("user_info", doc);
		parsingResultAndNgReason("add_result", NG_REASON_STR, nList);

		if(resultStr == null || ngReason == null)
			return XmlParseResult.INVALID_DATA;
		else
			return XmlParseResult.SUCCESS;
	}


	private XmlParseResult parsingResultForLogin(Document doc) {
		NodeList nList;

		//<login_info ng_reason="OK" result="OK" role="player"/>
		nList = getNodeListOfGivenTag("login_info", doc);
		parsingResultAndNgReason(RESULT_STR, NG_REASON_STR, nList);

		if(resultStr == null || ngReason == null)
			return XmlParseResult.INVALID_DATA;
		else
			return XmlParseResult.SUCCESS;
	}

	private XmlParseResult parsingResultForMap(Document doc) {
		NodeList nList;

		//<game_info result="OK" ng_reason="-" />
		nList = getNodeListOfGivenTag("game_info", doc);
		parsingResultAndNgReason(RESULT_STR, NG_REASON_STR, nList);

		if(resultStr == null || ngReason == null )
			return XmlParseResult.INVALID_DATA;
		else
			return XmlParseResult.SUCCESS;
	}


	private void parsingResultAndNgReason(String resultAttrName, String ngReasonAttrName, NodeList nList) {
		resultStr = XmlResultString.valueOf(getAttributeValueAtNthTag(resultAttrName, nList, 0));
		ngReason = XmlNgReason.valueOf(getAttributeValueAtNthTag(ngReasonAttrName, nList, 0));
	}
}
