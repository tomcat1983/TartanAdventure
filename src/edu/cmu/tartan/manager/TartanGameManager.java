package edu.cmu.tartan.manager;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import edu.cmu.tartan.ServerGame;
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
	
	private ISocketHandler socket;
	private ISocketHandler designerSocket;
	private IQueueHandler queue;
	private AccountManager accountManager;
	private XmlParser xmlParser;
	
	private HashMap<String, ServerGame> tartanGames;
	
	private boolean isLoop = true;
	private int loginUserCounter = 0;
	
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
		
		tartanGames = new HashMap<String, ServerGame>();
	}

	@Override
	public void run() {
		dequeue();
	}
	
	public void dequeue() {
		
		SocketMessage socketMessage = null;
		String message = null;
		String threadName = null;
		

        while(isLoop){
            socketMessage = queue.consume();
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
		String eventMessage = String.format("[%S] %S", userId, message);
		
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForEventMessage(userId, eventMessage);
		
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
	public boolean sendToOters(String userId, String message) {
		
		boolean returnValue = false;
		String eventMessage = String.format("[%S] %S", userId, message);
		
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForEventMessage(userId, eventMessage);
		
		for(String key : tartanGames.keySet()) {
			if(!userId.equals(key)) {
				returnValue = sendToClient(key, xmlMessage);
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
		
		returnValue = socket.sendToClient(userId, message);
		return returnValue;
	}
	
	
	/**
	 * Achieve a goal of the game
	 * @param userId	The user ID who won a game
	 * @return	
	 */
	public boolean achievedGoal(String userId) {
		
		boolean returnValue = false;
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = null;
		
		xmlMessage = xw.makeXmlForGameEnd(userId, "WIN");
		returnValue = sendToClient(userId, xmlMessage);
		
		for(String key : tartanGames.keySet()) {
			if(!userId.equals(key)) {
				xmlMessage = xw.makeXmlForGameEnd(userId, "LOSE");
				returnValue = sendToClient(key, xmlMessage);
			}
		}
		
//		xmlMessage = xw.makeXmlForGameEnd("LOSE");
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
	
	public boolean registerNewUser(String userId) {
		if (!tartanGames.containsKey(userId)) {
			ServerGame tartanGame = null;
			tartanGames.put(userId, tartanGame);
			return true;
		}
		return false;
	}
			
	public void processMessage(String threadName, String message) {
		
		gameLogger.info(String.format("[%s] %s", Thread.currentThread().getStackTrace()[1].getMethodName(),
				"Received message from a server socket"));

		String messageType = null;
		
		xmlParser.parseXmlFromString(message);
		messageType = xmlParser.getMessageType();
		XmlResponse xr = xmlParser.getXmlResponse();
		
		gameLogger.info(String.format("[%s] %s", Thread.currentThread().getStackTrace()[1].getMethodName(),
				"message type : " + messageType));
		
		switch(messageType) {
			case("REQ_LOGIN"):
				login(threadName, ((XmlResponseLogin) xr).getId(), ((XmlResponseLogin) xr).getPw(), ((XmlResponseLogin) xr).getRole());
				break;
			case("ADD_USER"):
				register(((XmlResponseAddUser) xr).getId(), ((XmlResponseAddUser) xr).getPw());
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
		
		if (returnValue) {
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
			loginUserCounter++;
			serverSocket.updateSocketState(userId, CommandResult.LOGIN_SUCCESS, threadName);
			xmlMessage = xw.makeXmlForLogin(XmlResultString.OK, XmlNgReason.OK);
		} else {
			serverSocket.updateSocketState(userId, CommandResult.LOGIN_FAIL, threadName);
			xmlMessage = xw.makeXmlForLogin(XmlResultString.NG, XmlNgReason.INVALID_INFO);
		}
		
		returnValue = sendToClient(userId, xmlMessage);
		
		return returnValue;
	}

	@Override
	public boolean register(String userId, String userPw) {
		boolean returnValue = false;
		returnValue = accountManager.registerUser(userId, userPw, "0");
		
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = null;
		
		if (returnValue) {
			xmlMessage = xw.makeXmlForAddUser(XmlResultString.OK, XmlNgReason.OK);
		} else {
			xmlMessage = xw.makeXmlForAddUser(XmlResultString.NG, XmlNgReason.INVALID_INFO);
		}
		
		returnValue = sendToClient(userId, xmlMessage);
		
		return returnValue;
	}

	@Deprecated
	@Override
	public boolean validateUserId(String userId) {
		return false;
	}

	@Deprecated
	@Override
	public boolean validateUserPw(String userPw) {
		return false;
	}

	@Override
	public boolean startGame(String userId) {
		
		XmlWriterServer xw;
		String xmlMessage = null;
		
		if (loginUserCounter < 2) {
			// TODO Send the result to client
			xw = new XmlWriterServer();
			xmlMessage = xw.makeXmlForEventMessage(userId, "No other player in this room. \n" + 
					"Please wait until another player logs in and retry after few minutes.");
			sendToClient(userId, xmlMessage);
			
			xmlMessage = xw.makeXmlForGameStart(XmlResultString.NG, XmlNgReason.NO_PLAYERS);
			sendToClient(userId, xmlMessage);
			return false;
		}
		
		for (String key : tartanGames.keySet()) {
			
			tartanGames.get(key).start();
		}
		
		xw = new XmlWriterServer();
		xmlMessage = null;
		
		boolean returnValue = false;
		
		socket.updateSocketState(userId, CommandResult.START_GAME_SUCCESS, null);
		xmlMessage = xw.makeXmlForGameStart(XmlResultString.OK, XmlNgReason.OK);
		
		returnValue = socket.sendToAll(xmlMessage);
		
		return returnValue;
	}

	@Override
	public boolean endGame(String threadName, String userId) {
		
		boolean returnValue = false;
		
		for (String key : tartanGames.keySet()) {
			if(userId.equals(key)) {
				returnValue = tartanGames.get(key).controlGame("exit");
				tartanGames.remove(key);
				socket.updateSocketState(userId, CommandResult.END_GAME_SUCCESS, threadName);
			}
			loginUserCounter--;
			if (loginUserCounter < 1) {
				socket.updateSocketState(userId, CommandResult.END_GAME_ALL_USER, threadName);
				
			}
		}
		
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = null;
		
		if (returnValue) {
			String eventMessage = String.format("[%S] %S", userId, "Exit game");
			xmlMessage = xw.makeXmlForEventMessage(userId, eventMessage);
			returnValue = socket.sendToAll(xmlMessage);
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
	
	public boolean sendCommand(String userId, String command) {
		
		boolean returnValue = false;
		// TDDO How to send
		
		
		return returnValue;
	}
}
