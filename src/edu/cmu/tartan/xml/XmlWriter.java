package edu.cmu.tartan.xml;

import java.io.Closeable;
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
	Element lastElement; 
	Element parentElement; 

	public void startWritingXml(XmlMessageType msgType, String sender, String receiver) throws ParserConfigurationException {

		dbFactory =  DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		docWriting = dBuilder.newDocument();

		rootElement("message");
		setAttributeToElement("type", msgType.name());
		setAttributeToElement("sender", sender);
		setAttributeToElement("receiver", receiver);
	}

	public void rootElement(String elementName) {

		Element rootElement = docWriting.createElement(elementName);
		docWriting.appendChild(rootElement);
		lastElement = rootElement;
		parentElement = rootElement; 
	}


	public void addChildElement(String elementName) {

		Element childElement = docWriting.createElement(elementName);
		lastElement.appendChild(childElement);
		parentElement = lastElement; 
		lastElement = childElement;
	}

	public void addBrotherElement(String elementName) {

		Element brotherElement = docWriting.createElement(elementName);
		parentElement.appendChild(brotherElement);
		lastElement = brotherElement;
	}


	public void setAttributeToElement(String attrName, String value) {

		Attr attr = docWriting.createAttribute(attrName);
		attr.setValue(value);
		lastElement.setAttributeNode(attr);
	}


	public String convertDocumentToString() {

		return convertDocumentToString(docWriting); 
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
		try (	InputStream is = new FileInputStream(source);
				OutputStream os = new FileOutputStream(dest); ) {
			byte[] buffer = new byte[1024];
			int length;

			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			close(is);
			close(os);
		}
		catch (Exception e) {
			gameInterface.severe("Exception occur when overWriteFile from " + sourceFileName + " to " + destFileName);
		}
	}

	public static void close(Closeable c) {
		if (c == null) return; 
		try {
			c.close();
		} catch (IOException e) {
			gameInterface.severe("IOException occur when closing io stream");
		}
	}


}
