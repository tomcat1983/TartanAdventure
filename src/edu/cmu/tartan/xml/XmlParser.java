package edu.cmu.tartan.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XmlParser {

	private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private final DocumentBuilder builder;


	public XmlParser() throws ParserConfigurationException{

		builder = factory.newDocumentBuilder();
	}

	public void parseDOM (String xmlUri){


		try {

			Document document = builder.parse(xmlUri);
			NodeList nodeList = document.getChildNodes();

			int nodeLength = nodeList.getLength();

			for(int i=0; i<nodeLength; i++){
				printNodeInfo (nodeList.item(i));
			}

		} catch (SAXException e) {

		} catch (IOException e) {

		}

	}


	public void parseDomFromFile(String fileName){
		try {
			parseDomFromFileThrowException(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void parseDomFromFileThrowException(String fileName) throws SAXException, IOException, ParserConfigurationException{

		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		//NodeList nList = doc.getElementsByTagName("staff");
		NodeList nList = doc.getChildNodes();

		System.out.println("----------------------------");

		int nodeLength = nList.getLength();

		for(int i=0; i<nodeLength; i++){
			printNodeInfo (nList.item(i));
		}
	}
	

	private void printNodeInfo(Node node) {

		String value = "child node";
		short childType = getChildType(node);

		if (childType==Node.TEXT_NODE){
			value = node.getTextContent();
		}

		StringBuilder sb = new StringBuilder(200);
		sb.append("node name:")
		.append(node.getNodeName())
		.append(", node value:").append(value);


		if (node.hasAttributes()){

			NamedNodeMap attributeMap = node.getAttributes();
			int attributeLength = attributeMap.getLength();

			for (int i=0; i<attributeLength; i++){

				Node attNode = attributeMap.item(i);
				sb.append('\n')
				.append("attr name:").append(attNode.getNodeName())
				.append(", attr value:").append(attNode.getNodeValue());

			}

		}

		System.out.println(sb);

		if (node.hasChildNodes()){

			NodeList nodeList = node.getChildNodes(); 
			int nodeLength = nodeList.getLength();

			for(int i=0; i<nodeLength; i++){

				Node childNode = nodeList.item(i);
				if (childNode.getNodeType()==Node.TEXT_NODE){
					continue;
				}

				printNodeInfo (nodeList.item(i));
			}
		}
	}

	private short getChildType(Node node) {

		NodeList nodeList = node.getChildNodes();
		int length = nodeList.getLength();

		for (int i=0; i<length; i++){

			Node childNode = nodeList.item(i);
			if (childNode.getNodeType()!=Node.TEXT_NODE)

				return childNode.getNodeType();
		}

		return Node.TEXT_NODE;
	}
}