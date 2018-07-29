package edu.cmu.tartan.manager;

public class SocketMessage {
	private String threadName;
	private String message;
	
	public SocketMessage(String threadName, String message) {
		this.threadName = threadName;
		this.message = message;
	}
	
	public String getThreadName () {
		return threadName;
	}
	
	public String getMessage() {
		return message;
	}

}
