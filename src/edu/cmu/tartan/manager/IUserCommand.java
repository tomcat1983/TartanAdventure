package edu.cmu.tartan.manager;

import edu.cmu.tartan.xml.XmlLoginRole;

public interface IUserCommand {

	/**
	 * @param threadName
	 * @param userId
	 * @param userPw
	 * @param role
	 * @return
	 */
	public boolean login(String threadName, String userId, String userPw, XmlLoginRole role);

	/**
	 * @param threadName
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public boolean register(String threadName, String userId, String userPw);

	/**
	 * @param userId
	 * @return
	 */
	public boolean startGame(String userId);

	/**
	 * @param threadName
	 * @param userId
	 * @return
	 */
	public boolean endGame(String threadName, String userId);

	/**
	 * @param userId
	 * @param command
	 * @return
	 */
	public boolean updateGameState(String userId, String command);

	/**
	 * @param mapFile
	 * @return
	 */
	public boolean uploadMap(String mapFile);

}
