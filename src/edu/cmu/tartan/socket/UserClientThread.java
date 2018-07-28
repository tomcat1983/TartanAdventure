package edu.cmu.tartan.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.manager.IQueueHandler;

public class UserClientThread implements Runnable, ISocketMessage {
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	private Socket clientSocket;
	private IQueueHandler messageQueue;
	
	private boolean isLogin = false;
	private String userId = "";
	private boolean isLoop = true;
	

	public UserClientThread(Socket clientSocket, IQueueHandler messageQueue) {
		this.clientSocket = clientSocket;
		this.messageQueue = messageQueue;
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
			 gameInterface.println("Server IOException: " + e.getMessage());
		}
		return false;
	}

	@Override
	public void receiveMessage() {
		
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

				messageQueue.produce(message);
			}

			stopSocket();

		} catch (IOException e) {
			gameInterface.println("Server IOException: " + e.getMessage());
		}
	}
	
	
	private String getUserIdFromXml(String message) {
		// TODO : Process message
		String id = null;
		return id;
	}
	
	public String getUserId() {
		return userId;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gameInterface.println("Closing connection");
		
		return returnValue;
	}

}
