package edu.cmu.tartan.socket;

public interface ISocketMessage {
	/**
	 * @param message
	 * @return
	 */
	public boolean sendMessage(String message);
	/**
	 *
	 */
	public void receiveMessage();

}
