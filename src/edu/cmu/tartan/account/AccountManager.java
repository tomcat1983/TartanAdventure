package edu.cmu.tartan.account;

import edu.cmu.tartan.db.DbAccessor;
import edu.cmu.tartan.xml.XmlLoginRole;

public class AccountManager implements IAccountHandler {
	
	DbAccessor dbAccessor;

	public AccountManager() {
		dbAccessor = new DbAccessor();
		initialize();
	}
	
	private void initialize() {
		dbAccessor.createNewDatabase();
		dbAccessor.createNewTable();
		dbAccessor.insert("designer", "abcd1234", XmlLoginRole.DESIGNER.name());
	}

	@Override
	public boolean registerUser(String userId, String userPw, String userType) {
		boolean returnValue = false;
		returnValue = dbAccessor.insert(userId, userPw, userType);
		return returnValue;
	}

	@Override
	public boolean loginUser(String userId, String userPw, String userRole) {
		String userPwInDB = dbAccessor.getPassword(userId);
		String userRoleInDB = dbAccessor.getUserRole(userId);
		
		if (userPw.equals(userPwInDB) && userRole.equals(userRoleInDB)) {
			return true;
		}
		return false;
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
