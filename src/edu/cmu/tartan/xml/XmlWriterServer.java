package edu.cmu.tartan.xml;


import javax.xml.parsers.ParserConfigurationException;

public class XmlWriterServer extends XmlWriter {

	private static final String PARSER_EXCEPTION = "ParserConfigurationException";
	private static final String SERVER_STR = "server";
	private static final String CLIENT_STR = "client";
	private static final String RESULT_STR = "result";
	private static final String NG_REASON_STR = "ng_reason";

	/**
	 * @param id
	 * @param gameResult
	 * @param text
	 * @return
	 */
	public String makeXmlForGameEnd(String id, String gameResult, String text) {
		String xmlString = null;

		try {
			//<common_info user_id="userId" />
			//<game_result result="lose" text="you dead"/>
			startWritingXml(XmlMessageType.GAME_END, SERVER_STR, CLIENT_STR);

			addChildElement("common_info");
			setAttributeToElement("user_id", id);

			addBrotherElement("game_result");
			setAttributeToElement(RESULT_STR, gameResult);
			setAttributeToElement("text", text);

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param loginResult
	 * @param reason
	 * @return
	 */
	public String makeXmlForLogin(XmlResultString loginResult, XmlNgReason reason) {
		String xmlString = null;

		try {
			//<login_info login_result="OK" ng_reason="-" role="player"/>
			startWritingXml(XmlMessageType.REQ_LOGIN, SERVER_STR, CLIENT_STR);
			addChildElement("login_info");

			setAttributeToElement(RESULT_STR, loginResult.name());
			setAttributeToElement(NG_REASON_STR, reason.name());

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param addResult
	 * @param reason
	 * @return
	 */
	public String makeXmlForAddUser(XmlResultString addResult, XmlNgReason reason) {
		String xmlString = null;

		try {
			//<login_info login_result="OK" ng_reason="-" role="player"/>
			startWritingXml(XmlMessageType.ADD_USER, SERVER_STR, CLIENT_STR);
			addChildElement("user_info");

			setAttributeToElement("add_result", addResult.name());
			setAttributeToElement(NG_REASON_STR, reason.name());

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param startResult
	 * @param reason
	 * @return
	 */
	public String makeXmlForGameStart(XmlResultString startResult, XmlNgReason reason) {
		String xmlString = null;

		try {
			//	<game_start result="OK" ng_reason="OK" />
			startWritingXml(XmlMessageType.REQ_GAME_START, SERVER_STR, CLIENT_STR);
			addChildElement("game_start");

			setAttributeToElement(RESULT_STR, startResult.name());
			setAttributeToElement(NG_REASON_STR, reason.name());

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param parseResult
	 * @param reason
	 * @return
	 */
	public String makeXmlForGameUpload(XmlResultString parseResult, XmlNgReason reason) {
		String xmlString = null;

		try {
			//	<game_info result="OK" ng_reason="OK" />
			startWritingXml(XmlMessageType.UPLOAD_MAP_DESIGN, SERVER_STR, CLIENT_STR);
			addChildElement("game_info");

			setAttributeToElement(RESULT_STR, parseResult.name());
			setAttributeToElement(NG_REASON_STR, reason.name());

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}

	/**
	 * @param id
	 * @param message
	 * @return
	 */
	public String makeXmlForEventMessage(String id, String message) {
		String xmlString = null;

		try {
			//<common_info user_id="userId" />
			//<display text="Hello Client! \n This is a message from server." />
			startWritingXml(XmlMessageType.EVENT_MESSAGE, SERVER_STR, CLIENT_STR);

			addChildElement("common_info");
			setAttributeToElement("user_id", id);

			addBrotherElement("display");
			setAttributeToElement("text", message);

			xmlString = convertDocumentToString();

		} catch (ParserConfigurationException e) {
			gameLogger.severe(PARSER_EXCEPTION);
		}

		gameLogger.info(xmlString);

		return xmlString;
	}
}
