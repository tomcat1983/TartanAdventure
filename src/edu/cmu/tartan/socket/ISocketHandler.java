package edu.cmu.tartan.socket;

public interface ISocketHandler {
	
	public void startSocket();
	public boolean stopSocket();
	
	public boolean addClient(String userId, String threadName);
	public boolean removeClient(String userId);
	
	public boolean sendToClientByThreadName(String threadName, String message);
	public boolean sendToClient(String userId, String message);
	public boolean sendToAll(String message);
	public boolean sendToOthers(String userId, String message);
	
	public void updateSocketState(String userId, CommandResult result, String threadName);
	
}
