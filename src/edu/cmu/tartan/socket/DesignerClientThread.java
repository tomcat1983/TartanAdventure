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

public class DesignerClientThread implements Runnable, ISocketMessage {
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	private Socket clientSocket;
	private IQueueHandler messageQueue;
	
	private boolean isLogin = false;
	private String designerId = "";
	

	public DesignerClientThread(Socket clientSocket, IQueueHandler messageQueue) {
		this.clientSocket = clientSocket;
		this.messageQueue = messageQueue;
	}

	@Override
	public void run() {
		receiveMessage();
	}

	public boolean sendMessage(String message) {
		try {
			OutputStream output = clientSocket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			writer.println(message);
			return true;
		} catch (IOException e) {
			gameInterface.println("Server exception: " + e.getMessage());
		}
		return false;
	}

	@Override
	public void receiveMessage() {
		
		try {
			InputStream input = clientSocket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			String message = "";

			while ((message = reader.readLine()) != null) {
				
				
				//TODO Check a null state
				if (message.equals("null")) break;
				
				if (isLogin) {
					getUserIdFromXml(message);
				}

				messageQueue.produce(message);
				sendMessage(message);

			}

			gameInterface.println("Closing connection");
			clientSocket.close();

		} catch (IOException e) {
			gameInterface.println("Server exception: " + e.getMessage());
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

}
