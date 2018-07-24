package edu.cmu.tartan.manager;

public interface IQueueHandler {
	
	public boolean produce(String message);
	public String consume();
	public int clearQueue();

}
