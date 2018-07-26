package edu.cmu.tartan.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.manager.IQueueHandler;

public class SocketServer implements Runnable, ISocketHandler {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	static final int MAX_USER_CONNECTION = 5;
	static final int MAX_DESIGNER_CONNECTION = 1;
	
	private int serverPort = 10015;
//	private int designerServerPort = 10016;
	
	private int userSocketCounter = 0;
//	private int designerSocketCounter = 0;
	
	private boolean isLoop = true;
//	private boolean isRunning = false;
	
	private List<UserClientThread> clientThreadList = new ArrayList<UserClientThread>();
	private HashMap<String, UserClientThread> clientThreadMap = new HashMap<>();
	
	private ServerSocket serverSocket;
	private IQueueHandler messageQueue;

	public SocketServer(IQueueHandler messageQueue) {
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

			gameInterface.println("Server Started");
			gameInterface.println("Server is listening on port " + serverPort);
			gameInterface.println("Waiting for client");

			while (isLoop) {
				Socket socket = serverSocket.accept();

				if (userSocketCounter > MAX_USER_CONNECTION) {
					gameInterface.println("Too many client");
					sendMessage(socket, "Server too busy. Try later.");
					socket.close();
				}

				gameInterface.println("New client connected");
				userSocketCounter++;
				
				InetAddress inetAddress = socket.getInetAddress();
				int clientPort = socket.getPort();
				String clientIp = inetAddress.getHostAddress();

				gameInterface.println("Client Port : " + clientPort);
				gameInterface.println("Client IP : " + clientIp);

				UserClientThread clientHandler = new UserClientThread(socket, messageQueue);
				Thread thread = new Thread(clientHandler);
				thread.start();
				
				clientThreadList.add(clientHandler);
			}

		} catch (IOException e) {
			gameInterface.println("Server IOException: " + e.getMessage());
		}
	}

	@Override
	public boolean stopSocket() {
		
		boolean returnValue = false;
		
		try {
			serverSocket.close();
			returnValue = true;
		} catch (IOException e) {
			gameInterface.println("Server IOException: " + e.getMessage());
		}
		
		isLoop = false;
		userSocketCounter = 0;
		
		return returnValue;
	}
	
	private boolean sendMessage(Socket clientSocket, String message) {
		try {
			OutputStream output = clientSocket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			
			writer.println("Server: " + message);
			
			return true;
		} catch (IOException e) {
			gameInterface.println("Server IOException: " + e.getMessage());
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
	public boolean addClient(String userId) {
		for(UserClientThread clientThread : clientThreadList) {
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
		for(UserClientThread clientThread : clientThreadList) {
			if (userId.equals(clientThread.getUserId())) {
				return clientThreadList.remove(clientThread);
			}
		}
		userSocketCounter--;
		return false;
	}
	
	@Override
	public void updateClientState(String userId, String message) {
		
	}
}
