package edu.cmu.tartan.account;

enum ReturnType {
	SUCCESS,
	INVALID_ID,
	INVALID_PW,
	NONEXISTENCE_ID,
	MISMATCHED_PW
}

public class AccountManager implements IAccountHandler {
	
	public AccountManager() {
		
	}

	@Override
	public int registerUser(String userId, String password) {
		validateId(userId);
		return 0;
	}

	@Override
	public int loginUser(String userId, String password) {
		validatePassword(password);
		return 0;
	}
	
	private ReturnType validateId(String userId) {
		return ReturnType.INVALID_ID;
	}
	
	private ReturnType validatePassword(String password) {
		return ReturnType.INVALID_PW;
	}

}
