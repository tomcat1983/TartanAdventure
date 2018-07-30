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
import java.util.logging.Logger;

import edu.cmu.tartan.config.Config;
import edu.cmu.tartan.manager.IQueueHandler;

public class SocketServer implements Runnable, ISocketHandler {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	static final int MAX_USER_CONNECTION = 5;
	static final int MAX_DESIGNER_CONNECTION = 5;

	private int serverPort = 10015;
	private int socketCounter = 0;
	private int maxSocket = MAX_USER_CONNECTION;

	private boolean isLoop = true;
	private boolean isPlaying = false;

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

		serverPort = Config.getServerPort();

		try {
			serverSocket = new ServerSocket(serverPort);

			gameLogger.info("Server Started");
			gameLogger.info("Server is listening on port " + serverPort);
			gameLogger.info("Waiting for client");

			while (isLoop) {
				Socket socket = serverSocket.accept();

				if (socketCounter > maxSocket || isPlaying) {
					sendMessage(socket, "I’m sorry. The game server is busy. Please retry to connect later.");
					socket.close();
				}

				gameLogger.info("New client connected");
				socketCounter++;

				InetAddress inetAddress = socket.getInetAddress();
				int clientPort = socket.getPort();
				String clientIp = inetAddress.getHostAddress();

				gameLogger.info("Client Port : " + clientPort);
				gameLogger.info("Client IP : " + clientIp);

				String threadName = String.format("User %d", socketCounter);
				UserClientThread clientHandler = new UserClientThread(socket, messageQueue, threadName);
				Thread thread = new Thread(clientHandler, threadName);
				thread.start();

				clientThreadList.add(clientHandler);
			}

		} catch (IOException e) {
			gameLogger.info("Server IOException: " + e.getMessage());
		}
	}

	@Override
	public boolean stopSocket() {

		boolean returnValue = false;
		isLoop = false;

		try {
			if (serverSocket != null)
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
		for(UserClientThread client : clientThreadList) {
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
		for(UserClientThread client : clientThreadList) {
			if (threadName.equals(client.getThreadName())) {
				return clientThreadList.remove(client);
			}
		}
		socketCounter--;
		return false;
	}

	@Override
	public void updateClientState(String userId, CommandResult result, String threadName) {

		switch (result) {
			case LOGIN_SUCCESS:
				login(true, userId, threadName);
				break;
			case LOGIN_FAIL:
				login(false, userId, threadName);
				break;
			case REGISTER_SUCCESS:
				break;
			case REGISTER_FAIL:
				break;
			case START_GAME_SUCCESS:
				startGame(true, userId);
				break;
			case END_GAME_SUCCESS:
				endGame(true, userId, threadName);
				break;
			case END_GAME_ALL_USER:
				setIsPlaying(false);
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
		return returnValue;
	}

	public void startGame(boolean isSuccess, String userId) {
		isPlaying = true;
	}

	public boolean endGame(boolean isSuccess, String userId, String threadName) {
		boolean returnValue = false;

		if (isSuccess) {
			if (userId != null && !userId.isEmpty()) {
				returnValue = removeClient(userId);
			} else {
				returnValue = removeClientFromList(threadName);
			}
		}

		return returnValue;
	}

	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public boolean getIsPlaying() {
		return isPlaying;
	}
}
