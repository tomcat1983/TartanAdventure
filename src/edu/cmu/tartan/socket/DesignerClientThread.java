package edu.cmu.tartan.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
	
	private boolean isLogin = false;
	private String designerId = "";
	private boolean isLoop = true;
	

	public DesignerClientThread(Socket clientSocket, IQueueHandler queue) {
		this.clientSocket = clientSocket;
		this.queue = queue;
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

			writer.println(message);
			return true;
		} catch (IOException e) {
			gameLogger.warning("IOException: " + e.getMessage());
		}
		return false;
	}

	@Override
	public void receiveMessage() {
		
		SocketMessage socketMessage = null;
		
		try {
			InputStream input = clientSocket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			String message = "";

			while (isLoop) {
				
				if((message = reader.readLine()) == null) break;
				
				//TODO Check a null state
				if (message.equals("null") 
						|| message.equals("quit")
						|| message.equals("exit")) break;
				
				if (isLogin) {
					getUserIdFromXml(message);
				}
				
				socketMessage = new SocketMessage(Thread.currentThread().getName(), message);

				queue.produce(socketMessage);
			}

			stopSocket();

		} catch (IOException e) {
			gameLogger.warning("IOException: " + e.getMessage());
		}
	}
	
	private String getUserIdFromXml(String message) {
		// TODO : Process message
		String id = null;
		return id;
	}
	
	public String getUserId() {
		return designerId;
	}
	
	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public boolean stopSocket() {
		boolean returnValue = false;
		isLoop = false;
		
		try {
			clientSocket.close();
			returnValue = true;
		} catch (IOException e) {
			gameLogger.warning("IOException: " + e.getMessage());
		}
		
		gameLogger.info("Closing designer connection");
		
		return returnValue;
	}
}
