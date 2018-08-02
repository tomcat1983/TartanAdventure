package edu.cmu.tartan.socket;

public interface ISocketHandler {

	/**
	 *
	 */
	public void startSocket();
	/**
	 * @return
	 */
	public boolean stopSocket();

	/**
	 * @param userId
	 * @param threadName
	 * @return
	 */
	public boolean addClient(String userId, String threadName);
	/**
	 * @param userId
	 * @return
	 */
	public boolean removeClient(String userId);

	/**
	 * @param threadName
	 * @param message
	 * @return
	 */
	public boolean sendToClientByThreadName(String threadName, String message);
	/**
	 * @param userId
	 * @param message
	 * @return
	 */
	public boolean sendToClient(String userId, String message);
	/**
	 * @param message
	 * @return
	 */
	public boolean sendToAll(String message);
	/**
	 * @param userId
	 * @param message
	 * @return
	 */
	public boolean sendToOthers(String userId, String message);

	/**
	 * @param userId
	 * @param result
	 * @param threadName
	 */
	public void updateSocketState(String userId, CommandResult result, String threadName);

}
