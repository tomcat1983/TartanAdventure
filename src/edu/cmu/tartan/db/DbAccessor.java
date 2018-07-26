package edu.cmu.tartan.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.cmu.tartan.GameInterface;

public class DbAccessor {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	private String url;
	private String dbLocation = "/Users/zhyuny/Downloads/sqlite/db/";
	private String dbName;

	public DbAccessor(String dbName) {

		this.dbName = dbName;
		url = "jdbc:sqlite:" + dbLocation + dbName;

	}

	/**
	 * Connect to the test.db database
	 *
	 * @return the Connection object
	 */
	private Connection connect() {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			gameInterface.println(e.getMessage());
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
			gameInterface.println("Database already exists");
			return false;
		}

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				gameInterface.println("The driver name is " + meta.getDriverName());
				gameInterface.println("A new database has been created.");
			}
			return true;

		} catch (SQLException e) {
			gameInterface.println("SQLException : " + e.getMessage());
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
			gameInterface.println("SQLException" + e.getMessage());
		}
		return false;
	}

	/**
	 * Insert a new row into the warehouses table
	 *
	 * @param name
	 * @param capacity
	 */
	public int insert(String userId, String userPw, String userType) {
		
		String sql = "INSERT INTO T_USER_INFO(user_id,user_pw, user_type) VALUES(?,?,?)";

		int returnValue = 0;

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			pstmt.setString(3, userType);
			returnValue = pstmt.executeUpdate();
		} catch (SQLException e) {
			gameInterface.println(e.getMessage());
		}

		return returnValue;
	}

	/**
	 * select all rows in the warehouses table
	 */
	public String selectByUserId(String query, String userId) {
		String sql = query + "'" + userId + "'";

		String userPw = null;

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			
			while(rs.next()) {
				userPw = rs.getString("user_pw");
			}
		} catch (SQLException e) {
			gameInterface.println(e.getMessage());
		}

		return userPw;
	}

	public String getPassword(String userId) {
		
		String query = "SELECT user_id, user_pw, user_type FROM T_USER_INFO where user_id = ";
		String userPw = selectByUserId(query, userId);

		if (userPw == null)
			return null;

		return userPw;
	}
	
	public int delete(String userId) {
		
		String sql = "DELETE FROM T_USER_INFO where user_id= ?";
		
		int returnValue = 0;

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			// set the corresponding param
            pstmt.setString(1, userId);
            // execute the delete statement
            returnValue = pstmt.executeUpdate();
            
		} catch (SQLException e) {
			gameInterface.println(e.getMessage());
		}
		
		return returnValue;
	}

}
