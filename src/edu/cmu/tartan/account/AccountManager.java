package edu.cmu.tartan.account;

import edu.cmu.tartan.db.DbAccessor;

enum ReturnType {
	SUCCESS, INVALID_ID, INVALID_PW, NONEXISTENCE_ID, MISMATCHED_PW
}

public class AccountManager implements IAccountHandler {
	
	DbAccessor dbAccessor;
	String dbName = "TartanAdventure.db";

	public AccountManager() {
		dbAccessor = new DbAccessor(dbName);
	}

	@Override
	public int registerUser(String userId, String userPw, String userType) {
		dbAccessor.insert(userId, userPw, userType);
		return 0;
	}

	@Override
	public int loginUser(String userId, String userPw, String userType) {
		String returnValue = dbAccessor.getPassword(userId);
		
		if (userPw.equals(returnValue)) {
			return 1;
		}
		
		// TODO : Type compare
		if (userType.equals("")) {
			return 2;
		}
		return 0;
	}

	public ReturnType validateId(String userId) {

		// 1. More than 6 characters && Smaller than 16
		if (userId.length() < 6 || userId.length() > 16) {
			return ReturnType.INVALID_ID;
		}

		// 2. Only English and numbers are allowed
		if (!userId.matches("[a-z|A-Z|0-9]*")) {
			return ReturnType.INVALID_ID;
		}

		// 3. First letter is an alphabet
		if (!userId.matches("^[a-zA-Z]\\w*")) {
			return ReturnType.INVALID_ID;
		}

		return ReturnType.SUCCESS;
	}

	public ReturnType validatePassword(String userPw) {
		// 1. More than 8 characters
		if (userPw.length() < 8 || userPw.length() > 16) {
			return ReturnType.INVALID_PW;
		}

		// 2. Only English and numbers are allowed
		if (!userPw.matches("[a-z|A-Z|0-9]*")) {
			return ReturnType.INVALID_PW;
		}

		// 3. First letter is an alphabet
		if (!userPw.matches("^[a-zA-Z]\\w*")) {
			return ReturnType.INVALID_PW;
		}
		
		//4. Capital letters are more than one
		if (!userPw.matches(".*[A-Z].*")) {
			return ReturnType.INVALID_PW;
		}

		return ReturnType.SUCCESS;
	}

}
