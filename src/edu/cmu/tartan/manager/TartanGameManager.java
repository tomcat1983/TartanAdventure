package edu.cmu.tartan.manager;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import edu.cmu.tartan.account.AccountManager;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.socket.SocketServer;
import edu.cmu.tartan.xml.XmlParser;

public class TartanGameManager implements Runnable, IUserCommand{
	
	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();
	
	private ISocketHandler socket;
	private IQueueHandler messageQueue;
	private AccountManager accountManager;
	private XmlParser xmlParser;
	
	private HashMap<String, IGameControlMessage> tartanGames;
	
	private boolean isLoop = true;
	private int loginUserCounter = 0;
	
	public TartanGameManager (ISocketHandler socket, IQueueHandler messageQueue) {
		this.socket = socket;
		this.messageQueue = messageQueue;
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
		String message = null;

        while(isLoop){
            message = messageQueue.consume();
            
            if (message != null && !message.isEmpty()) {
            	processMessage(message);
            }
        }
		
	}
	
	public boolean sendToAll(String userId, String message) {
		
		boolean returnValue = false;
		
		returnValue = socket.sendToAll(userId, message);
		
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
	public boolean updateGameState(String userId, String message) {
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
	 * Exit game
	 * @param userId	a game user ID
	 * @param message	a game message
	 * @return
	 */
	public boolean updateGameExit(String userId, String message) {
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
			
	public void processMessage(String message) {
		//gameLogger.info("RCV : " + message);
		String messageType = null;
		
		xmlParser.parseXmlFromString(message);
		messageType = xmlParser.getMessageType();
		
		switch(messageType) {
			case("REQ_LOGIN"):
				//login(xmlParser.getUserId(), xmlParser.getUserPw, xmlParser.getUserType);
				break;
			case("ADD_USER"):
				break;
		}
	}

	@Override
	public boolean login(String userId, String userPw) {
		boolean returnValue = false;
		returnValue = accountManager.loginUser(userId, userPw, "0");
		
		if (returnValue) {
			returnValue = registerNewUser(userId);
		}
		
		if (returnValue) {
			loginUserCounter++;
		}
		return returnValue;
	}

	@Override
	public boolean register(String userId, String userPw) {
		boolean returnValue = false;
		returnValue = accountManager.registerUser(userId, userPw, "0");
		return returnValue;
	}

	@Deprecated
	@Override
	public boolean validateUserId(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Deprecated
	@Override
	public boolean validateUserPw(String userPw) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startGame(String message) {
		
		if (loginUserCounter < 2) return false;
		
		boolean returnValue = false;
		
		for (String key : tartanGames.keySet()) {
			returnValue = tartanGames.get(key).controlGame("start");
			if (!returnValue) {
				break;
			}
		}
		
		((SocketServer)socket).setIsPlaying(returnValue);
		
		return returnValue;
	}

	@Override
	public boolean endGame(String userId) {
		
		boolean returnValue = false;
		
		for (String key : tartanGames.keySet()) {
			if(userId.equals(key)) {
				returnValue = tartanGames.get(key).controlGame("exit");
				tartanGames.remove(key);
			}
			loginUserCounter--;
			if (loginUserCounter < 1) {
				((SocketServer)socket).setIsPlaying(false);
				
			}
		}
		
		return returnValue;
	}

	@Override
	public boolean uploadMap(String mapFile) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
