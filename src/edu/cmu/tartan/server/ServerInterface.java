package edu.cmu.tartan.server;

import edu.cmu.tartan.socket.ISocketHandler;

public class ServerInterface {

	/**
	 * Server socket
	 */
	ISocketHandler socket;
	
	public ServerInterface(ISocketHandler socket) {
		this.socket = socket;
	}

}
