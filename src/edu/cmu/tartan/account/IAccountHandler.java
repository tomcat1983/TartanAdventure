package edu.cmu.tartan.account;

public interface IAccountHandler {
	
	public boolean registerUser(String userId, String userPw, String userType);
	public boolean loginUser(String userId, String userPw, String userType);

}
