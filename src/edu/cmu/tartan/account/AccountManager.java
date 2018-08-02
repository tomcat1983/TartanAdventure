package edu.cmu.tartan.account;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cmu.tartan.config.Config;
import edu.cmu.tartan.db.DbAccessor;
import edu.cmu.tartan.xml.XmlLoginRole;

public class AccountManager implements IAccountHandler {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	DbAccessor dbAccessor;
	String dbName;

	/**
	 *
	 */
	public AccountManager() {
		if (Config.getDbName() != null) dbName = Config.getDbName();
		dbAccessor = new DbAccessor(dbName);
	}

	/**
	 * @param mode
	 */
	public AccountManager(String mode) {
		if (Config.getDbName() != null) dbName = Config.getDbName();
		dbAccessor = new DbAccessor(dbName);
		if ("server".equals(mode)) {
			initialize();
		}
	}

	/**
	 *
	 */
	private void initialize() {
		dbAccessor.createNewDatabase();
		dbAccessor.createNewTable();
		if (dbAccessor.hasUserId("designer") == 0) {
			dbAccessor.insert("designer", "fa876f642212ea67a08f0a56fbcf672e4e2c1f51a2d7d6aba6ee84a8bfca2340", XmlLoginRole.DESIGNER.name());
		}
		
		if (dbAccessor.hasUserId("developer") == 0) {
			dbAccessor.insert("developer", "fa876f642212ea67a08f0a56fbcf672e4e2c1f51a2d7d6aba6ee84a8bfca2340", XmlLoginRole.PLAYER.name());
		}
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

		gameLogger.log(Level.INFO, "Does not matched from DB info. User ID : {0}", userId);
		return false;
	}

	/**
	 * @param userId
	 * @return
	 */
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

	/**
	 * @param userPw
	 * @return
	 */
	public ReturnType validatePassword(String userPw) {
		// 1. More than 8 characters && smaller than 16
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
