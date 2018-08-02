package edu.cmu.tartan.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cmu.tartan.manager.IQueueHandler;
import edu.cmu.tartan.manager.SocketMessage;

public class UserClientThread implements Runnable, ISocketMessage {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	private Socket clientSocket;
	private IQueueHandler queue;

	private boolean isLoop = true;
	private String userId = "";
	private String threadName = "";


	/**
	 * @param clientSocket
	 * @param queue
	 * @param threadName
	 */
	public UserClientThread(Socket clientSocket, IQueueHandler queue, String threadName) {
		this.clientSocket = clientSocket;
		this.queue = queue;
		this.threadName = threadName;
	}

	@Override
	public void run() {
		receiveMessage();
	}

	@Override
	public boolean sendMessage(String message) {
		try {
			OutputStream output = clientSocket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			if(clientSocket.isConnected()) {
				writer.println(message);
			}
			return true;
		} catch (IOException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
		}
		return false;
	}

	@Override
	public void receiveMessage() {

		SocketMessage socketMessage = null;

		try {
			InputStream input = clientSocket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			String message = null;

			while (isLoop) {

				if((message = reader.readLine()) == null
						|| message.equals("null")) break;

				gameLogger.log(Level.INFO, "[Server] Received message : {0}", message);

				socketMessage = new SocketMessage(threadName, message);

				queue.produce(socketMessage);
			}

			stopSocket();

		} catch (IOException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
		}
	}

	/**
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 *
	 */
	public void stopSocket() {

		isLoop = false;

		try {
			Thread.sleep(2000);
			if (clientSocket != null) clientSocket.close();
			clientSocket = null;
		} catch (IOException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
		} catch (InterruptedException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
			Thread.currentThread().interrupt();
		}

		gameLogger.log(Level.INFO, "Closing user connection");
	}


}