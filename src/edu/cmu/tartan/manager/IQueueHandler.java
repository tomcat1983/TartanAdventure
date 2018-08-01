package edu.cmu.tartan.manager;

public interface IQueueHandler {
	
	public void produce(SocketMessage message);
	public SocketMessage consume();
	public int clearQueue();

}
