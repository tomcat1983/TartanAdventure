package edu.cmu.tartan.manager;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import edu.cmu.tartan.account.AccountManager;
import edu.cmu.tartan.socket.CommandResult;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.xml.*;

public class TartanGameManager implements Runnable, IUserCommand{
	
	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();
	
	private ISocketHandler socket;
	private IQueueHandler queue;
	private AccountManager accountManager;
	private XmlParser xmlParser;
	
	private HashMap<String, IGameControlMessage> tartanGames;
	
	private boolean isLoop = true;
	private int loginUserCounter = 0;
	
	public TartanGameManager (ISocketHandler socket, IQueueHandler queue) {
		this.socket = socket;
		this.queue = queue;
		accountManager = new AccountManager();
		try {
			xmlParser = new XmlParser();
		} catch (ParserConfigurationException e) {
			gameLogger.warning("ParserConfigurationException : " + e.getMessage());
		}
		
		tartanGames = new HashMap<String, IGameControlMessage>();
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
	
	public boolean sendToAll(String message) {
		
		boolean returnValue = false;
		
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = xw.makeXmlForEventMessage(message);
		
		returnValue = socket.sendToAll(xmlMessage);
		
		return returnValue;
	}
	
	public boolean sendToClient(String userId, String message) {
		boolean returnValue = false;
		
		returnValue = socket.sendToClient(userId, message);
		return returnValue;
	}
	
	
	/**
	 * Achieve a goal of the game
	 * @param userId	a game user ID
	 * @return	
	 */
	public boolean achievedGoal(String userId) {
		return false;
	}
	
	/**
	 * Game message notify to one user  
	 * @param userId	a game user ID
	 * @param message	a game message
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
			IGameControlMessage gameControlMessage = null;
			tartanGames.put(userId, gameControlMessage);
			return true;
		}
		return false;
	}
			
	public void processMessage(String threadName, String message) {

		String messageType = null;
		
		xmlParser.parseXmlFromString(message);
		messageType = xmlParser.getMessageType();
		XmlResponse xr = xmlParser.getXmlResponse();
		
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
				//TODO Send the result to client				
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
		
		if (returnValue) {
			loginUserCounter++;
			socket.updateSocketState(userId, CommandResult.LOGIN_SUCCESS, threadName);
			xmlMessage = xw.makeXmlForLogin(XmlResultString.OK, XmlNgReason.OK);
		} else {
			socket.updateSocketState(userId, CommandResult.LOGIN_FAIL, threadName);
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
		
		if (loginUserCounter < 2) {
			// TODO Send the result to client
			return false;
		}
		
		boolean returnValue = false;
		
		for (String key : tartanGames.keySet()) {
			
			// TODO How to start game?
			returnValue = tartanGames.get(key).controlGame("start");
			if (!returnValue) {
				break;
			}
		}
		
		XmlWriterServer xw = new XmlWriterServer();
		String xmlMessage = null;
		
		if (returnValue) {
			socket.updateSocketState(userId, CommandResult.START_GAME_SUCCESS, null);
			xmlMessage = xw.makeXmlForAddUser(XmlResultString.OK, XmlNgReason.OK);
		} else {
			xmlMessage = xw.makeXmlForAddUser(XmlResultString.NG, XmlNgReason.INVALID_INFO);
		}
		
		returnValue = sendToAll(xmlMessage);
		
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
		
		return returnValue;
	}

	@Override
	public boolean uploadMap(String mapFile) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean sendCommand(String userId, String command) {
		
		boolean returnValue = false;
		
		for (String key : tartanGames.keySet()) {
			if (userId.equals(key)) {
				returnValue = tartanGames.get(key).controlGame(command);
			}
		}
		return returnValue;
	}

	
}
