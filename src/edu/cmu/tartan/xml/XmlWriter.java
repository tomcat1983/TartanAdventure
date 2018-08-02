package edu.cmu.tartan.xml;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Logger;

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


public class XmlWriter {

	protected static final Logger gameLogger = Logger.getGlobal();

	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	Document docWriting;
	Element lastElement;
	Element parentElement;

	/**
	 * @param msgType
	 * @param sender
	 * @param receiver
	 * @throws ParserConfigurationException
	 */
	public void startWritingXml(XmlMessageType msgType, String sender, String receiver) throws ParserConfigurationException {

		dbFactory =  DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		docWriting = dBuilder.newDocument();

		rootElement("message");
		setAttributeToElement("type", msgType.name());
		setAttributeToElement("sender", sender);
		setAttributeToElement("receiver", receiver);
	}

	/**
	 * @param elementName
	 */
	public void rootElement(String elementName) {

		Element rootElement = docWriting.createElement(elementName);
		docWriting.appendChild(rootElement);
		lastElement = rootElement;
		parentElement = rootElement;
	}


	/**
	 * @param elementName
	 */
	public void addChildElement(String elementName) {

		Element childElement = docWriting.createElement(elementName);
		lastElement.appendChild(childElement);
		parentElement = lastElement;
		lastElement = childElement;
	}

	/**
	 * @param elementName
	 */
	public void addBrotherElement(String elementName) {

		Element brotherElement = docWriting.createElement(elementName);
		parentElement.appendChild(brotherElement);
		lastElement = brotherElement;
	}


	/**
	 * @param attrName
	 * @param value
	 */
	public void setAttributeToElement(String attrName, String value) {

		Attr attr = docWriting.createAttribute(attrName);
		attr.setValue(value);
		lastElement.setAttributeNode(attr);
	}


	/**
	 * @return
	 */
	public String convertDocumentToString() {

		return convertDocumentToString(docWriting);
	}

	/**
	 * @param doc
	 * @return
	 */
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
			gameLogger.severe("TransformerConfigurationException");
		} catch (TransformerException e) {
			gameLogger.severe("TransformerException");
		}

		return result;
	}

	/**
	 * @param fileName
	 * @param xmlString
	 */
	public static void saveXmlStringToFile(String fileName, String xmlString) {

		try (Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(fileName), "UTF8"))) {

			out.append(xmlString);
			out.flush();
		} catch (FileNotFoundException e) {
			gameLogger.severe("FileNotFoundException");
		} catch (UnsupportedEncodingException e1) {
			gameLogger.severe("UnsupportedEncodingException");

		} catch (IOException e1) {
			gameLogger.severe("IOException");

		}
	}

	/**
	 * @param destFileName
	 * @param sourceFileName
	 * @return
	 */
	public static boolean overWriteFile(String destFileName, String sourceFileName) {

		boolean result = true;

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
		catch (IOException e) {
			gameLogger.severe("IOException");
			result = false;
		}
		catch (Exception e) {
			gameLogger.severe("Exception occur when overWriteFile from " + sourceFileName + " to " + destFileName);
			result = false;
		}

		return result;
	}

	/**
	 * @param c
	 */
	public static void close(Closeable c) {
		if (c == null) return;
		try {
			c.close();
		} catch (IOException e) {
			gameLogger.severe("IOException occur when closing io stream");
		}
	}


}
