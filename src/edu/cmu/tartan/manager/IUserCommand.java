package edu.cmu.tartan.manager;

import edu.cmu.tartan.xml.XmlLoginRole;

public interface IUserCommand {
	
	public boolean login(String name, String userId, String userPw, XmlLoginRole role);
	
	public boolean register(String userId, String userPw);
	
	public boolean validateUserId(String userId);
	
	public boolean validateUserPw(String userPw);
	
	public boolean startGame(String message);
	
	public boolean endGame(String name, String message);

	public boolean updateGameState(String message);
	
	public boolean uploadMap(String mapFile);

}
