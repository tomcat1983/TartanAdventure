package edu.cmu.tartan.account;

public interface IAccountHandler {
	
	public int registerUser(String userId, String userPw, String userType);
	public int loginUser(String userId, String userPw, String userType);

}
