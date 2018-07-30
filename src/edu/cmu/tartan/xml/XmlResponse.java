package edu.cmu.tartan.xml;

import java.util.logging.Logger;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public abstract class XmlResponse {
	
	protected XmlMessageType msgType;
	protected Logger gameLogger = Logger.getGlobal();

	
	//every child have different response 
	public abstract XmlParseResult doYourJob(Document doc);
	
	public XmlMessageType getMsgType() {
		return msgType;
	}
	

	public NodeList getNodeListOfGivenTag(String tagName, Document doc) {
		
		return doc.getElementsByTagName(tagName);
	}
	
	@Nullable
	public String getAttributeValueAtNthTag(String attrName, NodeList nList, int n) {

		String result = null;
		Node node = nList.item(n); 

		if (node.hasAttributes()) {

			NamedNodeMap attributeMap = node.getAttributes();
			int attributeLength = attributeMap.getLength();

			for (int j=0; j<attributeLength; j++) {
				Node attNode = attributeMap.item(j);
				if(attNode.getNodeName().equals(attrName))
					result = attNode.getNodeValue();
			}
		}
		
		return result;
	}
}
