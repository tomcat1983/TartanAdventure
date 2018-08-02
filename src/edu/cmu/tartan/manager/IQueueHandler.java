package edu.cmu.tartan.manager;

public interface IQueueHandler {

	/**
	 * @param message
	 */
	public void produce(SocketMessage message);
	/**
	 * @return
	 */
	public SocketMessage consume();
	/**
	 * @return
	 */
	public int clearQueue();

}
