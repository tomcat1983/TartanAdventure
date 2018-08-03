package edu.cmu.tartan.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
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

	private boolean isDesigner = false;

	private Socket socket = null;
	private ResponseMessage responseMessage;
	private IQueueHandler queue;

	private boolean isLoop;
	private boolean quitFromCli = false;

	/**
	 * @param responseMessage
	 * @param queue
	 * @param isDesigner
	 */
	public SocketClient(ResponseMessage responseMessage, IQueueHandler queue, boolean isDesigner) {
		isLoop = true;
		this.responseMessage = responseMessage;
		this.queue = queue;
		this.isDesigner = isDesigner;
	}

	@Override
	public void run() {
		connectToServer();
	}

	/**
	 *
	 */
	public void connectToServer() {
		String serverIp = Config.getServerIp();
		int serverPort;
		if (isDesigner) {
			serverPort = Config.getDesignerPort();
		} else {
			serverPort = Config.getUserPort();
		}

		try {
			socket = new Socket(serverIp, serverPort);
			gameLogger.log(Level.INFO, "Connected to server");

			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String message = "";

            while(isLoop) {

            	if ((message = reader.readLine()) == null
            			|| message.equals("null")) break;

				receiveMessage(message);
            }


        } catch (UnknownHostException e) {
        	gameLogger.log(Level.WARNING, "Server not found : {0}", e.getMessage());
        } catch (IOException e) {
        	gameLogger.log(Level.WARNING, e.getMessage());
        } finally {
        	stopSocket();
        }
	}

	/**
	 * @param timeout
	 * @return
	 */
	public boolean waitToConnection(int timeout) {
		while (timeout > 0 && socket == null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException exception) {
				gameLogger.info("Exception :" + exception.getMessage());
				Thread.currentThread().interrupt();
				break;
			}

			timeout -= 10;
		}

		return (socket != null && socket.isConnected());
	}

	/**
	 * @param message
	 * @return
	 */
	public boolean receiveMessage(String message) {

		boolean returnValue = false;

		gameLogger.log(Level.INFO, "Received message : {0}", message);
		XmlParser xmlParser;
		String messageType = null;
		XmlResponseClient xr = null;

		try {
			xmlParser = new XmlParser(XmlParserType.CLIENT);
			xmlParser.parseXmlFromString(message);
			messageType = xmlParser.getMessageType();
			xr = (XmlResponseClient) xmlParser.getXmlResponse();

			returnValue = true;

		} catch (ParserConfigurationException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
		}

		gameLogger.log(Level.INFO, "Received message type : {0}", messageType);

		switch(messageType) {
			case("REQ_LOGIN"):
				sendByResponseMessage(xr.getResultStr(), null);
				break;
			case("ADD_USER"):
				sendByResponseMessage(xr.getResultStr(), null);
				break;
			case("REQ_GAME_START"):
				sendByResponseMessage(xr.getResultStr(), null);
				break;
			case("GAME_END"):
				if (quitFromCli) {
					sendByResponseMessage(XmlResultString.OK, xr.getGameText());
					quitFromCli = false;
				} else {
					sendByQueue(xr.getGameText());
					sendByQueue("quit");
				}
				break;
			case("UPLOAD_MAP_DESIGN"):
				sendByResponseMessage(xr.getResultStr(), null);
				break;
			case("EVENT_MESSAGE"):
				sendByQueue(xr.getEventMsg());
				break;
			default:
				break;
		}
		return returnValue;
	}

	/**
	 * @param message
	 */
	public void sendByQueue(String message) {
		queue.produce(new SocketMessage(Thread.currentThread().getName(), message));
	}

	/**
	 * @param result
	 * @param message
	 */
	public void sendByResponseMessage(XmlResultString result, String message) {

		String responseMsg = "FAIL";

		if(XmlResultString.OK == result) {
			responseMsg = "SUCCESS";
		}

		synchronized (responseMessage) {
			if (message == null ) {
				responseMessage.setMessage(responseMsg);
			} else {
				responseMessage.setMessage(message);
			}
			responseMessage.notify();
		}
	}

	/**
	 * @param message
	 * @return
	 */
	public boolean sendMessage(String message) {

		gameLogger.log(Level.INFO, "Send to Server : {0}", message);

		if (socket == null || !socket.isConnected()) {
			gameLogger.log(Level.INFO, "Socket is not connected to the server yet.");
			return false;
		}
		try {
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			writer.println(message);
			return true;
		} catch (IOException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
		}
		return false;
	}

	/**
	 * @return
	 */
	public boolean stopSocket() {

		gameLogger.log(Level.INFO, "Close a client socket");

		boolean returnValue = false;
		isLoop = false;
		quitFromCli = false;

		try {
			Thread.sleep(1000);
			if (socket != null) socket.close();
			socket = null;
		} catch (IOException e) {
			gameLogger.warning("IOException : " + e.getMessage());
		} catch (InterruptedException e) {
			gameLogger.warning("InterruptedException");
			Thread.currentThread().interrupt();
		}
		return returnValue;
	}

	/**
	 * @param value
	 */
	public void setQuitFromCli(boolean value) {
		this.quitFromCli = value;
	}

}
