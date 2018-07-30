package edu.cmu.tartan.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDbAccessor {

	DbAccessor dbAccessor;
	String userId = "gildong";
	String userPw = "1234";
	String userType = "0";
	String url = "TartanAdventure.db";
	

	@BeforeEach
	public void beforeTest() {
		dbAccessor = new DbAccessor();
	}

//	@Disabled
	@Test
	public void testTrueWhenCreateNewDatabase() {

		// Given
		File file = new File(url);
		if (file.exists())
			file.delete();

		// When
		boolean returnValue = dbAccessor.createNewDatabase();

		// Then
		assertEquals(true, returnValue);
	}

//	@Disabled
	@Test
	public void testFalseWhenCreateNewDatabase() {

		// Given
		File file = new File(url);
		if (!file.exists()) {
			dbAccessor.createNewDatabase();
		}

		// When
		boolean returnValue = dbAccessor.createNewDatabase();

		// Then
		assertEquals(false, returnValue);
	}

//	@Disabled
	@Test
	public void testTrueWhenCreateNewTable() {
		// Given
		File file = new File(url);
		if (file.exists()) {
			file.delete();
		}
		dbAccessor.createNewDatabase();

		// When
		boolean returnValue = dbAccessor.createNewTable();

		// Then
		assertEquals(true, returnValue);
	}

	@Test
	public void testTrueWhenInsert() {

		// Given
		File file = new File(url);
		if (file.exists()) {
			file.delete();
		}
		dbAccessor.createNewDatabase();
		dbAccessor.createNewTable();

		// When
		boolean returnValue = dbAccessor.insert(userId, userPw, userType);

		// Then
		assertEquals(true, returnValue);
		
	}

	@Test
	public void testTrueWhenGetPassword() {

		// Given
		File file = new File(url);
		if (file.exists()) {
			file.delete();
		}
		dbAccessor.createNewDatabase();
		dbAccessor.createNewTable();
		dbAccessor.insert(userId, userPw, userType);

		// When
		String returnValue = dbAccessor.getPassword(userId);

		// Then
		assertEquals(userPw, returnValue);
	}

	@Test
	public void testTrueWhenDelete() {

		// Given
		File file = new File(url);
		if (file.exists()) {
			file.delete();
		}
		dbAccessor.createNewDatabase();
		dbAccessor.createNewTable();
		
		dbAccessor.insert(userId, userPw, userType);

		// When
		boolean returnValue = dbAccessor.delete(userId);
		
		// Then
		assertEquals(true, returnValue);
	}
	
	@Test
	public void testMoreThanZeroWhenCheckTheUserId() {

		// Given
		File file = new File(url);
		if (file.exists()) {
			file.delete();
		}
		dbAccessor.createNewDatabase();
		dbAccessor.createNewTable();
		
		dbAccessor.insert(userId, userPw, userType);

		// When
		int returnValue = dbAccessor.hasUserId(userId);
		
		// Then
		assertNotEquals(0, returnValue);
	}

}
