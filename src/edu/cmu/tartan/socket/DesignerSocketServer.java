package edu.cmu.tartan.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cmu.tartan.config.Config;
import edu.cmu.tartan.manager.IQueueHandler;

public class DesignerSocketServer implements Runnable, ISocketHandler {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	static final int MAX_DESIGNER_CONNECTION = 1;

	private int socketCounter = 0;
	private boolean isLoop = true;

	private List<DesignerClientThread> clientThreadList = new ArrayList<>();
	private HashMap<String, DesignerClientThread> clientThreadMap = new HashMap<>();

	private ServerSocket serverSocket;
	private IQueueHandler queue;

	/**
	 * @param queue
	 */
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

		int serverPort = Config.getDesignerPort();

		try {
			serverSocket = new ServerSocket(serverPort);
			
			gameLogger.log(Level.INFO, "Server is listening on port {0}", serverPort);

			while (isLoop) {
				Socket socket = serverSocket.accept();

				if (socketCounter > MAX_DESIGNER_CONNECTION) {
					socket.close();
				}

				gameLogger.log(Level.INFO, "New designer client connected");
				socketCounter++;

				String threadName = String.format("Designer %d", socketCounter);
				DesignerClientThread clientHandler = new DesignerClientThread(socket, queue, threadName);
				Thread thread = new Thread(clientHandler, threadName);
				thread.start();

				clientThreadList.add(clientHandler);
			}

		} catch (IOException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
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
			gameLogger.log(Level.WARNING, e.getMessage());
		}
		socketCounter = 0;

		return returnValue;
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

		for (Entry<String, DesignerClientThread> entry : clientThreadMap.entrySet()) {
			returnValue = entry.getValue().sendMessage(message);
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

	/**
	 * @param threadName
	 * @return
	 */
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

	/**
	 * @param isSuccess
	 * @param userId
	 * @param threadName
	 * @return
	 */
	public boolean login(boolean isSuccess, String userId, String threadName) {

		boolean returnValue = false;

		if (isSuccess) {
			returnValue = addClient(userId, threadName);
		}

		gameLogger.log(Level.INFO, "Added a client to a map : {0}", returnValue);

		return returnValue;
	}

	@Override
	public boolean sendToClientByThreadName(String threadName, String message) {
		boolean returnValue = false;

		for(DesignerClientThread client : clientThreadList) {
			if (threadName.equals(client.getThreadName())) {
				returnValue = client.sendMessage(message);
				break;
			}
		}

		return returnValue;
	}

	@Override
	public boolean sendToOthers(String userId, String message) {
		return false;
	}

}
