package edu.cmu.tartan.socket;

public interface ISocketHandler {
	
	public void startSocket();
	public boolean stopSocket();
	
	public boolean addClient(String userId);
	public boolean removeClient(String userId);
	
	public boolean sendToClient(String userId, String message);
	public boolean sendToAll(String userId, String message);
	
	public void updateClientState(String userId, String message);
	
}
