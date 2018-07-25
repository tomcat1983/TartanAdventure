package edu.cmu.tartan.manager;

import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.socket.SocketServer;

public class TartanGameServer {

	/*
	public static void main(String[] args) {
		
		IQueueHandler messageQueue = new MessageQueue();
		
		ISocketHandler socketServer = new SocketServer(messageQueue);
		Thread socketServerThread = new Thread((Runnable)socketServer);
		socketServerThread.start();
		
		
		TartanGameManager tartanGameManager = new TartanGameManager(socketServer, messageQueue);
		Thread gameManagerThread = new Thread(tartanGameManager);
		gameManagerThread.start();
	}
	*/
}
