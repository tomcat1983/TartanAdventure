package edu.cmu.tartan.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.cmu.tartan.GameInterface;

public class XmlWriter {
	
	protected static GameInterface gameInterface = GameInterface.getInterface();

	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	Document docWriting;
	         
	public Element startWritingXml(XmlMessageType msgType, String sender, String receiver) throws ParserConfigurationException {
	
		dbFactory =  DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		docWriting = dBuilder.newDocument();
		
		Element messageElement = rootElement("message");
		setAttributeToElement(messageElement, "type", msgType.name());
		setAttributeToElement(messageElement, "sender", sender);
		setAttributeToElement(messageElement, "receiver", receiver);
		
		return messageElement; 
	}

	public Element rootElement(String elementName) {

		Element element = docWriting.createElement(elementName);
		docWriting.appendChild(element);
        return element; 
	}

	
	public Element addChildElement(Element parentElement, String elementName) {

		Element childElement = docWriting.createElement(elementName);
		parentElement.appendChild(childElement);
        return childElement; 
	}
	
	public void setAttributeToElement(Element element, String attrName, String value) {

		Attr attr = docWriting.createAttribute(attrName);
	    attr.setValue(value);
	    element.setAttributeNode(attr);
	}
	
	public String convertDocumentToString() {
		
		String result = null;
		
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer = factory.newTransformer();
			
			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(docWriting), new StreamResult(sw));

			result = sw.toString();
		} catch (TransformerConfigurationException e) {
			gameInterface.severe("TransformerConfigurationException");
		} catch (TransformerException e) {
			gameInterface.severe("TransformerException");
		}
		
		return result; 
	}
	
	public static String convertDocumentToString(Document doc) {
		
		String result = null;
		
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer = factory.newTransformer();
			
			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(sw));

			result = sw.toString();
		} catch (TransformerConfigurationException e) {
			gameInterface.severe("TransformerConfigurationException");
		} catch (TransformerException e) {
			gameInterface.severe("TransformerException");
		}
		
		return result; 
	}
	
	public static void saveXmlStringToFile(String fileName, String xmlString) {

		try (
			PrintWriter out = new PrintWriter(fileName)) {
			out.print(xmlString);
		} catch (FileNotFoundException e) {
			gameInterface.severe("FileNotFoundException");
		}
	}
	
	public static void overWriteFile(String destFileName, String sourceFileName) throws IOException {
       
		File source = new File(sourceFileName);
        File dest = new File(destFileName);

        //copy file conventional way using Stream
        InputStream is = null;
        OutputStream os = null;
        try {
			is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
        	is.close();
            os.close();
        }
	}
	
	
	
}
