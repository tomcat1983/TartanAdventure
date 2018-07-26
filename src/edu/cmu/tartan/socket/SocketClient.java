package edu.cmu.tartan.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.cmu.tartan.GameInterface;

public class SocketClient implements Runnable {
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();
	
	private String serverIp = "127.0.0.1";
	int serverPort = 10015;
	
	Socket socket = null;
	
	public SocketClient(String serverIp, int serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	@Override
	public void run() {
		connectToServer();

	}
	
	public boolean connectToServer() {
		
		try {
			socket = new Socket(serverIp, serverPort);
			 
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
            String message = "";
 
            while((message = reader.readLine()) != null) {
            	
            	//TODO Check a null state
				if (message.equals("null")) break;
				
				receiveMessage(message);
 
            }
 
            socket.close();
 
        } catch (UnknownHostException e) {
 
        	gameInterface.println("Server not found: " + e.getMessage());
 
        } catch (IOException e) {
 
        	gameInterface.println("IOException : " + e.getMessage());
        }
		
		return true;
	}
	
	public boolean receiveMessage(String message) {
		return false;
	}
	
	public boolean sendMessage(String message) {
		try {
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			writer.println(message);
			return true;
		} catch (IOException e) {
			 gameInterface.println("Server IOException: " + e.getMessage());
		}
		return false;
	}

}
