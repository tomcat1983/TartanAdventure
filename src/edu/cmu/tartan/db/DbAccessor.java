package edu.cmu.tartan.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import edu.cmu.tartan.config.Config;

public class DbAccessor {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	private String url;
	private String dbLocation = "./";
	private String dbName = "TartanAdventure.db";

	public DbAccessor() {
		if (Config.getDbName() != null) dbName = Config.getDbName();
		url = "jdbc:sqlite:" + dbLocation + dbName;
	}

	/**
	 * Connect to the database
	 *
	 * @return the Connection object
	 */
	private Connection connect() {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			gameLogger.warning("SQLException : " + e.getMessage());
		}
		return conn;
	}

	/**
	 * Connect to a sample database
	 *
	 */
	public boolean createNewDatabase() {

		File file = new File(dbLocation + dbName);
		
		if (file.isFile()) {
			gameLogger.info("Database already exists");
			return false;
		}

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				gameLogger.info("A new database has been created.");
			}
			return true;

		} catch (SQLException e) {
			gameLogger.warning("SQLException : " + e.getMessage());
		}
		return false;
	}

	/**
	 * Connect to a sample database
	 *
	 * @param fileName the database file name
	 */
	public boolean createNewTable() {

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS T_USER_INFO (\n"
				+ "	user_id varchar(20) NOT NULL PRIMARY KEY,\n"
				+ "	user_pw text NOT NULL,\n"
				+ "	user_type int NOT NULL\n"
				+ ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			gameLogger.warning("SQLException : " + e.getMessage());
		}
		return false;
	}

	/**
	 * Insert a new row into the warehouses table
	 *
	 * @param name
	 * @param capacity
	 */
	public boolean insert(String userId, String userPw, String userType) {
		
		String sql = "INSERT INTO T_USER_INFO(user_id, user_pw, user_type) VALUES(?,?,?)";

		boolean returnValue = false;
		
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			pstmt.setString(3, userType);
			pstmt.executeUpdate();
			returnValue = true;
		} catch (SQLException e) {
			gameLogger.warning("SQLException : " + e.getMessage());
		}

		return returnValue;
	}

	/**
	 * select all rows in the warehouses table
	 */
	public String selectByUserId(String query, String userId, String param) {
		String sql = query;

		String userPw = null;

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1,  userId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				userPw = rs.getString(param);
			}
		} catch (SQLException e) {
			gameLogger.warning("SQLException : " + e.getMessage());
		}

		return userPw;
	}

	public String getPassword(String userId) {
		
		String query = "SELECT user_id, user_pw, user_type FROM T_USER_INFO where user_id = ?";
		String userPw = selectByUserId(query, userId, "user_pw");

		if (userPw == null)
			return null;

		return userPw;
	}
	
	public String getUserRole(String userId) {
		
		String query = "SELECT user_id, user_pw, user_type FROM T_USER_INFO where user_id = ?";
		String userPw = selectByUserId(query, userId, "user_type");

		if (userPw == null)
			return null;

		return userPw;
	}
	
	public boolean delete(String userId) {
		
		String sql = "DELETE FROM T_USER_INFO where user_id= ?";
		
		boolean returnValue = false;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			// set the corresponding param
            pstmt.setString(1, userId);
            // execute the delete statement
            pstmt.executeUpdate();
            returnValue = true;
            
		} catch (SQLException e) {
			gameLogger.warning("SQLException : " + e.getMessage());
		}
		
		return returnValue;
	}
	
	public int hasUserId(String userId) {
		
		String sql = "SELECT COUNT(user_id) FROM T_USER_INFO WHERE user_id=?";

		int returnValue = 0;
		
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1,  userId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				returnValue = Integer.parseInt(rs.getString("COUNT(user_id)"));
			}
		} catch (SQLException e) {
			gameLogger.warning("SQLException : " + e.getMessage());
		}

		return returnValue;
	}

}
