package edu.cmu.tartan.account;

public interface IAccountHandler {
	
	public int registerUser(String userId, String password);
	public int loginUser(String userId, String password);

}
