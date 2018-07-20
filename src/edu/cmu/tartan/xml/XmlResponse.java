package edu.cmu.tartan.xml;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.cmu.tartan.GameInterface;

public abstract class XmlResponse {
	
	String responseXmlToClient; 
	XmlMessageType msgType;
	protected GameInterface gameInterface = GameInterface.getInterface();

	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	Document docReply;
	         
	
	//every child have different response 
	public abstract void makeResponseXmlString();
	public abstract XmlParseResult doYourJob(Document doc);
	
	public void setMsgType(XmlMessageType msgType) {
		
		this.msgType = msgType; 
	}
	
	public String getResponseXml() {
		return responseXmlToClient;
	}
	
	
//	<message type="UPLOAD_MAP_DESIGN" sender="server" receiver="client">

	
	public Element startWritingXml() throws ParserConfigurationException {
	
		dbFactory =  DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		docReply = dBuilder.newDocument();
		
		Element messageElement = rootElement("message");
		setAttributeToElement(messageElement, "type", msgType.name());
		setAttributeToElement(messageElement, "sender", "server");
		setAttributeToElement(messageElement, "type", "client");
		
		return messageElement; 
	}

	public Element rootElement(String elementName) {

		Element element = docReply.createElement(elementName);
		docReply.appendChild(element);
        return element; 
	}

	
	public Element addChildElement(Element parentElement, String elementName) {

		Element childElement = docReply.createElement(elementName);
		parentElement.appendChild(childElement);
        return childElement; 
	}
	
	public void setAttributeToElement(Element element, String attrName, String value) {

		Attr attr = docReply.createAttribute(attrName);
	    attr.setValue(value);
	    element.setAttributeNode(attr);
	}
	
	public String convertDocumentToString(Document doc) {
		
		String result = null;
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t;
		try {
			t = tf.newTransformer();
			StringWriter sw = new StringWriter();
			t.transform(new DOMSource(doc), new StreamResult(sw));

			result = sw.toString();
		} catch (TransformerConfigurationException e) {
			gameInterface.severe("TransformerConfigurationException");
		} catch (TransformerException e) {
			gameInterface.severe("TransformerException");
		}
		
		return result; 
	}

	public NodeList getNodeListOfGivenTag(String tagName, Document doc) {
		
		return doc.getElementsByTagName(tagName);
	}
	
	//must check null return value 
	public String getAttributeValueAtNthTag(String attrName, NodeList nList, int n) {

		String result = null;
		Node node = nList.item(n); 

		if (node.hasAttributes()){

			NamedNodeMap attributeMap = node.getAttributes();
			int attributeLength = attributeMap.getLength();

			for (int j=0; j<attributeLength; j++){
				Node attNode = attributeMap.item(j);
				if(attNode.getNodeName().equals(attrName))
					result = attNode.getNodeValue();
			}
		}
		
		return result;
	}
	
}
