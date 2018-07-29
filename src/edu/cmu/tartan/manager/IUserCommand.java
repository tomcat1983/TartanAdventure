package edu.cmu.tartan.manager;

import edu.cmu.tartan.xml.XmlLoginRole;

public interface IUserCommand {
	
	public boolean login(String threadName, String userId, String userPw, XmlLoginRole role);
	
	public boolean register(String userId, String userPw);
	
	public boolean validateUserId(String userId);
	
	public boolean validateUserPw(String userPw);
	
	public boolean startGame(String userId);
	
	public boolean endGame(String threadName, String userId);

	public boolean updateGameState(String userId, String command);
	
	public boolean uploadMap(String mapFile);

}
