package edu.cmu.tartan.manager;

import java.util.logging.Logger;

import edu.cmu.tartan.account.AccountManager;
import edu.cmu.tartan.account.ReturnType;
import edu.cmu.tartan.socket.SocketClient;
import edu.cmu.tartan.xml.XmlLoginRole;


public class TartanGameManagerClient implements Runnable, IUserCommand{
	
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
	
	public boolean sendMessage(String userId, String message) {
		// TODO Make a message for TCP communication
		socket.sendMessage(message);
		return false;
	}
	
	@Override
	public boolean login(String name, String userId, String userPw, XmlLoginRole role) {
		sendMessage(userId, userPw);
		waitResponseMessage();
		
		if ("true".equals((responseMessage).getMessage())) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean register(String userId, String userPw) {
		waitResponseMessage();
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
	public boolean startGame(String message) {
		waitResponseMessage();
		return false;
	}
	
	@Override
	public boolean endGame(String threadName, String message) {
		
		// TODO Send To server
		
		socket.stopSocket();
		
		isLoop = false;
		messageQueue.produce(new SocketMessage(Thread.currentThread().getName(), message));
		int returnValue = messageQueue.clearQueue();
		
		if (returnValue == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateGameState(String message) {
		waitResponseMessage();
		return false;
	}
	
	@Override
	public boolean uploadMap(String mapFile) {
		readMapFile(mapFile);
		waitResponseMessage();
		return false;
	}
	
	private String readMapFile(String mapFileName) {
		return null;
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
		String threadName = null;

        while(isLoop){
        	socketMessage = messageQueue.consume();
            threadName = socketMessage.getThreadName();
            message = socketMessage.getMessage();
            
            if (message != null && !message.isEmpty()) {
            	//TODO Send to CLI
            	
            }
        }
		
	}

	

}
