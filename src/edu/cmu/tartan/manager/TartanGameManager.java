package edu.cmu.tartan.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.account.AccountManager;
import edu.cmu.tartan.socket.CommandResult;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.xml.XmlLoginRole;
import edu.cmu.tartan.xml.XmlNgReason;
import edu.cmu.tartan.xml.XmlParser;
import edu.cmu.tartan.xml.XmlResponse;
import edu.cmu.tartan.xml.XmlResponseAddUser;
import edu.cmu.tartan.xml.XmlResponseCommand;
import edu.cmu.tartan.xml.XmlResponseGameEnd;
import edu.cmu.tartan.xml.XmlResponseGameStart;
import edu.cmu.tartan.xml.XmlResponseLogin;
import edu.cmu.tartan.xml.XmlResponseUploadMap;
import edu.cmu.tartan.xml.XmlResultString;
import edu.cmu.tartan.xml.XmlWriterServer;

public class TartanGameManager implements Runnable, IUserCommand{

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	/**
	 * Game interface for game message
	 */
	protected static final GameInterface gameInterface = GameInterface.getInterface();

	private ISocketHandler socket;
	private ISocketHandler designerSocket;
	private IQueueHandler queue;
	private AccountManager accountManager;
	private XmlParser xmlParser;

	private HashMap<String, TartanGameThread> tartanGames;

	private boolean isLoop = true;
	private int loginUserCounter = 0;

	/**
	 * @param socket
	 * @param designerSocket
	 * @param queue
	 */
	public TartanGameManager (ISocketHandler socket, ISocketHandler designerSocket, IQueueHandler queue) {
		this.socket = socket;
		this.designerSocket = designerSocket;
		this.queue = queue;
		accountManager = new AccountManager("server");
		try {
			xmlParser = new XmlParser();
		} catch (ParserConfigurationException e) {
			gameLogger.warning("ParserConfigurationException : " + e.getMessage());
		}

		tartanGames = new HashMap<>();
	}

	@Override
	public void run() {
		dequeue();
	}

	/**
	 *
	 */
	public void dequeue() {

		SocketMessage socketMessage = null;
		String message = null;
		String threadName = null;

        while(isLoop){
            socketMessage = queue.consume();
            if (socketMessage == null) return;
            threadName = socketMessage.getThreadName();
            message = socketMessage.getMessage();

            if (message != null && !message.isEmpty()) {
            	processMessage(threadName, message);
            }
        }
	}

	/**
	 * Send a message to all client
	 * Called by a game interface
	 * @param userId	The user ID who sending the message
	 * @param message	The game message
	 * @return
	 */
	public boolean sendToAll(String userId, String message) {

		boolean returnValue = false;

		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForEventMessage(userId, message);

		returnValue = socket.sendToAll(xmlMessage);

		return returnValue;
	}

	/**
	 * Send a message to all client but me
	 * Called by a game interface
	 * @param userId	The user ID who sending the message
	 * @param message	The game message
	 * @return
	 */
	public boolean sendToOthers(String userId, String message) {

		boolean returnValue = false;

		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForEventMessage(userId, message);

		for(String key : tartanGames.keySet()) {
			if(!userId.equals(key)) {
				returnValue = socket.sendToClient(key, xmlMessage);
			}
		}

		return returnValue;
	}

	/**
	 * Send a message to one client
	 * @param userId
	 * @param message
	 * @return
	 */
	public boolean sendToClient(String userId, String message) {
		boolean returnValue = false;

		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForEventMessage(userId, message);

		returnValue = socket.sendToClient(userId, xmlMessage);
		return returnValue;
	}


	/**
	 * Win the game
	 * @param userId	The user ID who won a game
	 * @return
	 */
	public boolean winTheGame(String userId, String message) {

		boolean returnValue = false;
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForGameEnd(userId, "WIN", message);
		returnValue = socket.sendToClient(userId, xmlMessage);

		sendToOthers(userId,  "You lose the game");

		for(String key : tartanGames.keySet()) {
			if(!userId.equals(key)) {
				gameInterface.putCommand(key, "terminate");
			}
		}

		return returnValue;
	}

	/**
	 * Lose the game
	 * @param userId	The user ID who won a game
	 * @return
	 */
	public boolean loseTheGame(String userId, String message) {

		boolean returnValue = false;
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForGameEnd(userId, "LOSE", message);
		returnValue = socket.sendToClient(userId, xmlMessage);
		sendToOthers(userId, userId + " exit the game");
		
		socket.updateSocketState(userId, CommandResult.END_GAME_SUCCESS, "");

		return returnValue;
	}

	/**
	 * Game message notify to one user
	 * @param userId	The game user ID
	 * @param message	The game message
	 * @return
	 */
	@Override
	public boolean updateGameState(String userId, String command) {
		return false;
	}

	/**
	 * Game message notify to all user
	 * @param message	a game message
	 * @return
	 */
	public boolean updateGameState(String message) {
		return false;
	}

	/**
	 * Help message
	 * @param userId	a game user ID
	 * @param message	a game message
	 * @return
	 */
	public boolean updateHelpMessage(String userId, String message) {
		return false;
	}


	/**
	 * Exit message
	 * @param userId	a game user ID
	 * @param message	a game message
	 * @return
	 */
	public boolean updateExitMessage(String userId, String message) {
		return false;
	}

	/**
	 * @param userId
	 * @return
	 */
	public boolean registerNewUser(String userId) {
		if (!tartanGames.containsKey(userId)) {
			TartanGameThread tartanGame = new TartanGameThread(userId);
			tartanGames.put(userId, tartanGame);
			return true;
		}
		return false;
	}

	/**
	 * @param threadName
	 * @param message
	 */
	public void processMessage(String threadName, String message) {

		String messageType = null;
		gameLogger.log(Level.INFO, "Received message : {0}", message);
		try {
			xmlParser = new XmlParser();
		} catch (ParserConfigurationException e) {
			gameLogger.log(Level.WARNING, "ParserConfigurationException  : {0}", e.getMessage());
		}
		xmlParser.parseXmlFromString(message);
		messageType = xmlParser.getMessageType();
		XmlResponse xr = xmlParser.getXmlResponse();

		gameLogger.log(Level.INFO, "Received message type : {0}", messageType);

		switch(messageType) {
			case("REQ_LOGIN"):
				login(threadName, ((XmlResponseLogin) xr).getId(), ((XmlResponseLogin) xr).getPw(), ((XmlResponseLogin) xr).getRole());
				break;
			case("ADD_USER"):
				register(threadName, ((XmlResponseAddUser) xr).getId(), ((XmlResponseAddUser) xr).getPw());
				break;
			case("REQ_GAME_START"):
				startGame(((XmlResponseGameStart) xr).getId());
				break;
			case("REQ_GAME_END"):
				endGame(threadName, ((XmlResponseGameEnd)xr).getId());
				break;
			case("UPLOAD_MAP_DESIGN"):
				// Map is auto saved
				uploadMap(((XmlResponseUploadMap)xr).getUserId());
				break;
			case("SEND_COMMAND"):
				sendCommand(((XmlResponseCommand)xr).getId(), ((XmlResponseCommand)xr).getCmd());
				break;
			default:
				break;
		}
	}

	@Override
	public boolean login(String threadName, String userId, String userPw, XmlLoginRole role) {
		boolean returnValue = false;
		returnValue = accountManager.loginUser(userId, userPw, role.name());

		if (returnValue && XmlLoginRole.PLAYER == role) {
			returnValue = registerNewUser(userId);
		}

		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = null;
		ISocketHandler serverSocket;

		if (XmlLoginRole.PLAYER == role) {
			serverSocket = socket;
		} else {
			serverSocket = designerSocket;
		}

		if (returnValue) {
			if (XmlLoginRole.PLAYER == role) loginUserCounter++;
			serverSocket.updateSocketState(userId, CommandResult.LOGIN_SUCCESS, threadName);
			xmlMessage = xw.makeXmlForLogin(XmlResultString.OK, XmlNgReason.OK);
		} else {
			serverSocket.updateSocketState(userId, CommandResult.LOGIN_FAIL, threadName);
			xmlMessage = xw.makeXmlForLogin(XmlResultString.NG, XmlNgReason.INVALID_INFO);
			serverSocket.sendToClientByThreadName(threadName, xmlMessage);
			return returnValue;
		}

		returnValue = serverSocket.sendToClient(userId, xmlMessage);

		if (loginUserCounter > 1) {
			sendToOthers(userId, userId + " participated in");
		}

		return returnValue;
	}

	@Override
	public boolean register(String threadName, String userId, String userPw) {
		boolean returnValue = false;
		returnValue = accountManager.registerUser(userId, userPw, XmlLoginRole.PLAYER.name());

		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = null;

		if (returnValue) {
			xmlMessage = xw.makeXmlForAddUser(XmlResultString.OK, XmlNgReason.OK);
		} else {
			xmlMessage = xw.makeXmlForAddUser(XmlResultString.NG, XmlNgReason.INVALID_INFO);
		}

		returnValue = socket.sendToClientByThreadName(threadName, xmlMessage);

		return returnValue;
	}

	@Override
	public boolean startGame(String userId) {

		XmlWriterServer xw;
		String xmlMessage = null;

		if (loginUserCounter < 1) {
			xw = new XmlWriterServer();

			xmlMessage = xw.makeXmlForGameStart(XmlResultString.NG, XmlNgReason.NO_PLAYERS);
			socket.sendToClient(userId, xmlMessage);
			return false;
		}

		boolean returnValue = false;

		socket.updateSocketState(userId, CommandResult.START_GAME_SUCCESS, null);

		xw = new XmlWriterServer();
		xmlMessage = xw.makeXmlForGameStart(XmlResultString.OK, XmlNgReason.OK);

		returnValue = socket.sendToClient(userId, xmlMessage);

		for (Map.Entry<String, TartanGameThread> entry : tartanGames.entrySet()) {
			TartanGameThread tartanGame = entry.getValue();
			Thread thread = new Thread(tartanGame);
			thread.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				gameLogger.log(Level.WARNING, e.getMessage());
				Thread.currentThread().interrupt();
			}

		}

		return returnValue;
	}

	@Override
	public boolean endGame(String threadName, String userId) {

		boolean returnValue = false;

		if (tartanGames.containsKey(userId)) {
			gameInterface.putCommand(userId, "terminate");
			tartanGames.remove(userId);
			loginUserCounter--;
		}

		if (userId != null && !userId.isEmpty()) {
			sendToOthers(userId, userId + " exits the game");
		} else {
			XmlWriterServer xw = new XmlWriterServer();
			String xmlMessage = xw.makeXmlForGameEnd(userId, "LOSE", "");
			returnValue = socket.sendToClientByThreadName(threadName, xmlMessage);
		}

		if (userId == null || userId.isEmpty()) {
			socket.updateSocketState(userId, CommandResult.END_GAME_SUCCESS, threadName);
		}

		if (loginUserCounter < 1) {
			socket.updateSocketState(userId, CommandResult.END_GAME_ALL_USER, threadName);
		}

		return returnValue;
	}

	@Override
	public boolean uploadMap(String userId) {
		boolean returnValue = false;
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForGameUpload(XmlResultString.OK, XmlNgReason.OK);
		returnValue = designerSocket.sendToClient(userId, xmlMessage);

		return returnValue;
	}

	/**
	 * @param userId
	 * @param command
	 * @return
	 */
	public boolean sendCommand(String userId, String command) {

		boolean returnValue = false;

		gameInterface.putCommand(userId, command);
		return returnValue;
	}
	
	public void stopGameManager() {
		isLoop = false;
		socket.stopSocket();
		designerSocket.stopSocket();
	}
}
