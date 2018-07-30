package edu.cmu.tartan.manager;

import java.util.logging.Logger;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.account.AccountManager;
import edu.cmu.tartan.account.ReturnType;
import edu.cmu.tartan.socket.SocketClient;
import edu.cmu.tartan.xml.XmlLoginRole;
import edu.cmu.tartan.xml.XmlMessageType;
import edu.cmu.tartan.xml.XmlWriterClient;


public class TartanGameManagerClient implements Runnable, IUserCommand{
	
	/**
	 * Game interface for game message
	 */
	protected static final GameInterface gameInterface = GameInterface.getInterface();
	
	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();
	
	private SocketClient socket;
	private IQueueHandler messageQueue;
	private ResponseMessage responseMessage;
	private AccountManager accountManager;
	
	private String userId = null;
	private String userPw = null;
	
	private boolean isLoop = true;
	
	public TartanGameManagerClient(SocketClient socket, ResponseMessage responseMessage, IQueueHandler messageQueue) {
		this.socket = socket;
		this.responseMessage = responseMessage;
		this.messageQueue = messageQueue;
		accountManager = new AccountManager();
	}
	
	public boolean sendMessage(String message) {
		// TODO Make a message for TCP communication
		socket.sendMessage(message);
		return false;
	}
	
	@Override
	public boolean login(String threadName, String userId, String userPw, XmlLoginRole role) {
		
		String message = null;
		
		XmlWriterClient xw = new XmlWriterClient(); 
		message = xw.makeXmlForLogin(userId, userPw, role);
		
		sendMessage(message);
		
		waitResponseMessage();
		
		if ("SUCCESS".equals((responseMessage).getMessage())) {
			this.userId = userId;
			this.userPw = userPw;
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean register(String userId, String userPw) {
		
		String message = null;
		
		XmlWriterClient xw = new XmlWriterClient(); 
		message = xw.makeXmlForAddUser(userId, userPw);
		
		sendMessage(message);
		
		waitResponseMessage();
		
		if ("SUCCESS".equals((responseMessage).getMessage())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean validateUserId(String userId) {
		ReturnType returnValue = accountManager.validateId(userId);
		if (ReturnType.SUCCESS == returnValue) {
			this.userId = userId;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean validateUserPw(String userPw) {
		ReturnType returnValue = accountManager.validatePassword(userPw);
		if (ReturnType.SUCCESS == returnValue) {
			this.userPw = userPw;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean startGame(String userId) {
		
		String message = null;
		
		XmlWriterClient xw = new XmlWriterClient(); 
		message = xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_START, userId);
		
		sendMessage(message);
		
		waitResponseMessage();
		
		if ("SUCCESS".equals((responseMessage).getMessage())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean endGame(String threadName, String userId) {
		
		String message = null;
		
		XmlWriterClient xw = new XmlWriterClient(); 
		message = xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_END, userId);
		
		sendMessage(message);
		
		
		// TODO Sequence of an end game
		socket.stopSocket();
		
		isLoop = false;
		messageQueue.produce(new SocketMessage(Thread.currentThread().getName(), userId));
		int returnValue = messageQueue.clearQueue();
		
		if (returnValue == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateGameState(String userId, String command) {
		
		boolean returnValue = false;
		
		String message = null;
		
		XmlWriterClient xw = new XmlWriterClient(); 
		message = xw.makeXmlForCommand(userId, command);
		
		returnValue = sendMessage(message);
		
		return returnValue;
	}
	
	@Override
	public boolean uploadMap(String mapFile) {
		
		String message = null;
		
		XmlWriterClient xw = new XmlWriterClient(); 
		message = xw.makeXmlForUploadMap(mapFile);
		
		sendMessage(message);
		
		waitResponseMessage();
		
		if ("SUCCESS".equals((responseMessage).getMessage())) {
			return true;
		}
		return false;
	}
	
	public void waitResponseMessage() {
		
		synchronized (responseMessage) {
			try {
				responseMessage.wait(10 * 1000);
			} catch(InterruptedException e){
                gameLogger.severe("InterruptedException : " + e.getMessage());
                Thread.currentThread().interrupt();
            }
		}
	}

	@Override
	public void run() {
		dequeue();
	}
	
	public void dequeue() {
		SocketMessage socketMessage = null;
		String message = null;

        while(isLoop){
        	socketMessage = messageQueue.consume();
            message = socketMessage.getMessage();
            
            if (message != null && !message.isEmpty()) {
            	gameInterface.println(message);
            	
            }
        }
	}
}
