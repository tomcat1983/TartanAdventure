package edu.cmu.tartan.manager;

public interface IQueueHandler {
	
	public boolean produce(SocketMessage message);
	public SocketMessage consume();
	public int clearQueue();

}
