package edu.cmu.tartan.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import edu.cmu.tartan.manager.IQueueHandler;

public class DesignerSocketServer implements Runnable, ISocketHandler {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	static final int MAX_DESIGNER_CONNECTION = 1;
	
	private int serverPort = 10016;
	
	private int socketCounter = 0;
	
	private boolean isLoop = true;
	
	private List<DesignerClientThread> clientThreadList = new ArrayList<DesignerClientThread>();
	private HashMap<String, DesignerClientThread> clientThreadMap = new HashMap<>();
	
	private ServerSocket serverSocket;
	private IQueueHandler messageQueue;

	public DesignerSocketServer(IQueueHandler messageQueue) {
		this.messageQueue = messageQueue;
		serverSocket = null;
	}
	
	@Override
	public void run() {
		startSocket();
	}

	@Override
	public void startSocket() {

		try {
			serverSocket = new ServerSocket(serverPort);

			while (isLoop) {
				Socket socket = serverSocket.accept();

				if (socketCounter > MAX_DESIGNER_CONNECTION) {
					sendMessage(socket, "I’m sorry. The game server is busy. Please retry to connect later.");
					socket.close();
				}

				gameLogger.info("New client connected");
				socketCounter++;
				
				String threadName = String.format("Designer %d", socketCounter);
				DesignerClientThread clientHandler = new DesignerClientThread(socket, messageQueue, threadName);
				Thread thread = new Thread(clientHandler, threadName);
				thread.start();
				
				clientThreadList.add(clientHandler);
			}

		} catch (IOException e) {
			gameLogger.warning("IOException: " + e.getMessage());
		}
	}

	@Override
	public boolean stopSocket() {
		
		boolean returnValue = false;
		isLoop = false;
		
		try {
			serverSocket.close();
			returnValue = true;
		} catch (IOException e) {
			gameLogger.warning("IOException: " + e.getMessage());
		}
		socketCounter = 0;
		
		return returnValue;
	}
	
	private boolean sendMessage(Socket clientSocket, String message) {
		try {
			OutputStream output = clientSocket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			
			writer.println(message);
			
			return true;
		} catch (IOException e) {
			gameLogger.warning("IOException: " + e.getMessage());
		}
		return false;
	}
	
	@Override
	public boolean sendToClient(String userId, String message) {
		boolean returnValue = false;
		if (clientThreadMap.containsKey(userId)) {
			returnValue = clientThreadMap.get(userId).sendMessage(message);
		}
		return returnValue;
	}
	
	@Override
	public boolean sendToAll(String userId, String message) {
		boolean returnValue = false;
		for (String clientId : clientThreadMap.keySet()) {
			if (!userId.equals(clientId)) {
				returnValue = clientThreadMap.get(clientId).sendMessage(message);
			}
		}
		
		return returnValue;
	}

	@Override
	public boolean addClient(String userId, String threadName) {
		for(DesignerClientThread clientThread : clientThreadList) {
			if (userId.equals(clientThread.getUserId())) {
				clientThreadMap.put(userId, clientThread);
			}
		}
		return clientThreadMap.containsKey(userId);
	}

	@Override
	public boolean removeClient(String userId) {
		if (clientThreadMap.containsKey(userId)) {
			clientThreadMap.remove(userId);
		}
		return !clientThreadMap.containsKey(userId);
	}
	
	public boolean removeClientFromList(String userId) {
		for(DesignerClientThread clientThread : clientThreadList) {
			if (userId.equals(clientThread.getUserId())) {
				return clientThreadList.remove(clientThread);
			}
		}
		socketCounter--;
		return false;
	}
	
	@Override
	public void updateClientState(String userId, CommandResult result, String threadName) {
		switch (result) {
		case LOGIN_SUCCESS:
			break;
		case LOGIN_FAIL:
			break;
		default:
			break;
		}
	}
}
