package edu.cmu.tartan.xml;

/*
 * These enum class should have same string with real xml response. 
 */

enum XmlParseResult {
	SUCCESS, INVALID_XML, INVALID_DATA, UNKNOWN_MESSAGE
}

enum XmlMessageType {
	UPLOAD_MAP_DESIGN, REQ_LOGIN, ADD_USER
}