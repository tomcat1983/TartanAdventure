package edu.cmu.tartan.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import edu.cmu.tartan.config.Config;
import edu.cmu.tartan.manager.IQueueHandler;
import edu.cmu.tartan.manager.ResponseMessage;
import edu.cmu.tartan.manager.SocketMessage;
import edu.cmu.tartan.xml.XmlParser;
import edu.cmu.tartan.xml.XmlParserType;
import edu.cmu.tartan.xml.XmlResponseClient;
import edu.cmu.tartan.xml.XmlResultString;

public class SocketClient implements Runnable {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();
	
	private String serverIp = "127.0.0.1";
	int serverPort = 10015;
	
	private Socket socket = null;
	private ResponseMessage responseMessage;
	private IQueueHandler queue;
	
	private boolean isLoop;
	
	public SocketClient(ResponseMessage responseMessage, IQueueHandler queue) {
		isLoop = true;
		this.responseMessage = responseMessage;
		this.queue = queue;
	}

	@Override
	public void run() {
		connectToServer();
	}
	
	public boolean connectToServer() {
		serverIp = Config.getServerIp();
		serverPort = Config.getServerPort();
		
		try {
			socket = new Socket(serverIp, serverPort);
			gameLogger.info("Connected to server");
			 
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
            String message = "";
 
            while(isLoop) {
            	
            	if ((message = reader.readLine()) == null) break;
            	//TODO Check a null state
				if (message.equals("null")
						|| message.equals("exit")
						|| message.equals("quit")) break;
				
				receiveMessage(message);
 
            }
 
            stopSocket();
 
        } catch (UnknownHostException e) {
 
        	gameLogger.severe("Server not found: " + e.getMessage());
        	return false;
 
        } catch (IOException e) {
 
        	gameLogger.severe("IOException : " + e.getMessage());
        	return false;
        }

		return true;
	}
	
	public boolean waitToConnection(int timeout) {
		while (timeout > 0 && socket == null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException exception) {
				gameLogger.info("Exception :" + exception.getMessage());
				break;
			}
			
			timeout -= 10;
		}
		
		return (socket != null);
	}
	
	public boolean receiveMessage(String message) {
		XmlParser xmlParser;
		String messageType = null;
		String eventMessage = null;
		XmlResponseClient xr = null;
		try {
			xmlParser = new XmlParser(XmlParserType.CLIENT);
			xmlParser.parseXmlFromString(message);
			messageType = xmlParser.getMessageType();
			xr = (XmlResponseClient) xmlParser.getXmlResponse();
			
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
			return false;
		}
		
		eventMessage = xr.getEventMsg();
		
		
		if ("EVENT_MESSAGE".equals(messageType)) {
			
			queue.produce(new SocketMessage(Thread.currentThread().getName(), eventMessage));
			return true;
		}
		
		String returnValue = "false";
		if(XmlResultString.OK == xr.getResultStr()) {
			returnValue = "true";
		}
		
		try {
			synchronized (responseMessage) {
				responseMessage.setMessage(returnValue);
				responseMessage.notify();
			}
		} catch (IllegalMonitorStateException e) {
			gameLogger.severe("IllegalMonitorStateException : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean sendMessage(String message) {
		if (!socket.isConnected()) return false;
		try {
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			writer.println(message);
			return true;
		} catch (IOException e) {
			gameLogger.severe("Server IOException: " + e.getMessage());
		}
		return false;
	}
	
	public boolean stopSocket() {
		boolean returnValue = false;
		isLoop = false;
		
		try {
			socket.close();
		} catch (IOException e) {
			gameLogger.severe("Server IOException: " + e.getMessage());
		}
		return returnValue;
	}

}
