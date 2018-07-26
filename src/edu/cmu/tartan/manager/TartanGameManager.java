package edu.cmu.tartan.manager;

import java.util.HashMap;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.socket.ISocketHandler;

public class TartanGameManager implements Runnable{
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();
	
	private ISocketHandler socketServer;
	private IQueueHandler messageQueue;
	
	private HashMap<String, IGameControlMessage> tartanGames;
	
	
	public TartanGameManager (ISocketHandler socketServer, IQueueHandler messageQueue) {
		this.socketServer = socketServer;
		this.messageQueue = messageQueue;
		
		tartanGames = new HashMap<String, IGameControlMessage>();
	}

	@Override
	public void run() {
		dequeue();
	}
	
	
	public void dequeue() {
		String message = null;

        while(true){
            message = messageQueue.consume();
            
            if (message != null && !message.isEmpty()) {
            	processMessage(message);
            }
        }
		
	}
	
	public void processMessage(String message) {
		gameInterface.println("RCV : " + message);
	}
	
	private void login(String userId) {
		
	}
	
	public boolean sendToAll(String userId, String message) {
		
		boolean returnValue = false;
		
		returnValue = socketServer.sendToAll(userId, message);
		
		return returnValue;
	}
	
	public boolean achievedGoal(String userId) {
		return false;
	}
	
	public boolean updateGameState(String userId, String message) {
		return false;
	}
	
	public boolean registerNewGame(String userId, String message) {
		if (!tartanGames.containsKey(userId)) {
		}
		return false;
	}
	
	public boolean removeGame(String userId, String message) {
		if (tartanGames.containsKey(userId)) {
			
		}
		return false;
	}
	
	public boolean startGame() {
		
		return false;
	}
	
	public boolean endGame() {
		
		return false;
	}

}
