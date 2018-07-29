package edu.cmu.tartan.manager;

public interface IUserCommand {
	
	public boolean login(String userId, String userPw);
	
	public boolean register(String userId, String userPw);
	
	public boolean validateUserId(String userId);
	
	public boolean validateUserPw(String userPw);
	
	public boolean startGame(String message);
	
	public boolean endGame(String message);

	public boolean updateGameState(String message);
	
	public boolean uploadMap(String mapFile);

}
