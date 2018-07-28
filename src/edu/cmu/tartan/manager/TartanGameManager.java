package edu.cmu.tartan.manager;

import java.util.HashMap;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.socket.ISocketHandler;

public class TartanGameManager implements Runnable{
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();
	
	private ISocketHandler socket;
	private IQueueHandler messageQueue;
	
	private HashMap<String, IGameControlMessage> tartanGames;
	
	private boolean isLoop = true;
	
	public TartanGameManager (ISocketHandler socket, IQueueHandler messageQueue) {
		this.socket = socket;
		this.messageQueue = messageQueue;
		
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
	
	public void processMessage(String message) {
		gameInterface.println("RCV : " + message);
	}
	
	private void login(String userId) {
		
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
		
		isLoop = false;
		return false;
	}

	
}
