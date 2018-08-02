package edu.cmu.tartan.manager;

public class SocketMessage {
	private String threadName;
	private String message;

	/**
	 * @param threadName
	 * @param message
	 */
	public SocketMessage(String threadName, String message) {
		this.threadName = threadName;
		this.message = message;
	}

	/**
	 * @return
	 */
	public String getThreadName () {
		return threadName;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

}
