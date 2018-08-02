package edu.cmu.tartan.account;

public interface IAccountHandler {

	/**
	 * @param userId
	 * @param userPw
	 * @param userType
	 * @return
	 */
	public boolean registerUser(String userId, String userPw, String userType);
	/**
	 * @param userId
	 * @param userPw
	 * @param userType
	 * @return
	 */
	public boolean loginUser(String userId, String userPw, String userType);

}
