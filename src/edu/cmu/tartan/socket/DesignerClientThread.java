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

public class DesignerClientThread implements Runnable, ISocketMessage {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	private Socket clientSocket;
	private IQueueHandler queue;

	private boolean isLoop = true;
	private String designerId = "";
	private String threadName;


	/**
	 * @param clientSocket
	 * @param queue
	 * @param threadName
	 */
	public DesignerClientThread(Socket clientSocket, IQueueHandler queue, String threadName) {
		this.clientSocket = clientSocket;
		this.queue = queue;
		this.threadName = threadName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		receiveMessage();
	}

	/* (non-Javadoc)
	 * @see edu.cmu.tartan.socket.ISocketMessage#sendMessage(java.lang.String)
	 */
	@Override
	public boolean sendMessage(String message) {
		try {
			OutputStream output = clientSocket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			writer.println(message);
			return true;
		} catch (IOException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.tartan.socket.ISocketMessage#receiveMessage()
	 */
	@Override
	public void receiveMessage() {

		SocketMessage socketMessage = null;

		try {
			InputStream input = clientSocket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			String message = null;

			while (isLoop) {

				if(((message = reader.readLine()) == null)
						|| message.equals("null")) break;

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
		return designerId;
	}

	/**
	 * @return
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * @return
	 */
	public boolean stopSocket() {
		boolean returnValue = false;
		isLoop = false;

		try {
			clientSocket.close();
			returnValue = true;
		} catch (IOException e) {
			gameLogger.log(Level.WARNING, e.getMessage());
		}

		gameLogger.log(Level.INFO, "Closing designer connection");

		return returnValue;
	}
}
