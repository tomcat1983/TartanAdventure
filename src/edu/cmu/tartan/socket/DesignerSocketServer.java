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
	private IQueueHandler queue;

	public DesignerSocketServer(IQueueHandler queue) {
		this.queue = queue;
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
				DesignerClientThread clientHandler = new DesignerClientThread(socket, queue, threadName);
				Thread thread = new Thread(clientHandler, threadName);
				thread.start();
				
				clientThreadList.add(clientHandler);
			}

		} catch (IOException e) {
			gameLogger.warning("IOException : " + e.getMessage());
		}
	}

	@Override
	public boolean stopSocket() {
		
		gameLogger.warning("Close a server designer socket");

		boolean returnValue = false;
		isLoop = false;
		
		try {
			if (serverSocket != null)
				serverSocket.close();
			returnValue = true;
		} catch (IOException e) {
			gameLogger.warning("IOException : " + e.getMessage());
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
			gameLogger.warning("IOException : " + e.getMessage());
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
	public boolean sendToAll(String message) {
		boolean returnValue = false;

		for (String userId : clientThreadMap.keySet()) {
			returnValue = clientThreadMap.get(userId).sendMessage(message);
		}

		return returnValue;
	}

	@Override
	public boolean addClient(String userId, String threadName) {
		for(DesignerClientThread client : clientThreadList) {
			if (threadName.equals(client.getThreadName())) {
				clientThreadMap.put(userId, client);
			}
		}
		return clientThreadMap.containsKey(userId);
	}

	@Override
	public boolean removeClient(String userId) {
		if (clientThreadMap.containsKey(userId)) {
			clientThreadList.remove(clientThreadMap.get(userId));
			clientThreadMap.remove(userId);
		}
		return !clientThreadMap.containsKey(userId);
	}
	
	public boolean removeClientFromList(String threadName) {
		for(DesignerClientThread client : clientThreadList) {
			if (threadName.equals(client.getThreadName())) {
				return clientThreadList.remove(client);
			}
		}
		socketCounter--;
		return false;
	}
	
	@Override
	public void updateSocketState(String userId, CommandResult result, String threadName) {
		switch (result) {
			case LOGIN_SUCCESS:
				login(true, userId, threadName);
				break;
			case LOGIN_FAIL:
				login(false, userId, threadName);
				break;
			case UPLOAD_SUCCESS:
				break;
			case UPLOAD_FAIL:
				break;
			default:
				break;
		}
	}

	public boolean login(boolean isSuccess, String userId, String threadName) {

		boolean returnValue = false;

		if (isSuccess) {
			returnValue = addClient(userId, threadName);
		}
		
		gameLogger.info("Added a client to a map : " + returnValue);
		
		return returnValue;
	}

	@Deprecated
	@Override
	public boolean sendToClientByThreadName(String threadName, String message) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
