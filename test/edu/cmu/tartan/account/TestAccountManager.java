package edu.cmu.tartan.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAccountManager {
	
	AccountManager accountManager;

	@BeforeEach
	public void beforeTest() {
		accountManager = new AccountManager();
	}
	
	@Test
	public void testFalseWhenUserIdIsSmallerThenSix() {
		// Given
		String userId = "abcde";
		
		// When
		ReturnType returnType = accountManager.validateId(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_ID, returnType);
	}
	
	@Test
	public void testFalseWhenUserIdIsMoreThenSixteen() {
		// Given
		String userId = "abcdefghijk1234567890";
		
		// When
		ReturnType returnType = accountManager.validateId(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_ID, returnType);
	}
	
	@Test
	public void testFalseWhenUserIdContainSpecialCharacter() {
		// Given
		String userId = "abcde!0";
		
		// When
		ReturnType returnType = accountManager.validateId(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_ID, returnType);
	}
	
	@Test
	public void testFalseWhenUserIdBeginsWithNumber() {
		// Given
		String userId = "1abcdef";
		
		// When
		ReturnType returnType = accountManager.validateId(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_ID, returnType);
	}
	
	@Test
	public void testTrueWhenValidUserId() {
		// Given
		String userId = "abcde00";
		
		// When
		ReturnType returnType = accountManager.validateId(userId);
		
		// Then
		assertEquals(ReturnType.SUCCESS, returnType);
	}
	
	@Test
	public void testFalseWhenUserPwIsSmallerThenSix() {
		// Given
		String userId = "abcdeA";
		
		// When
		ReturnType returnType = accountManager.validatePassword(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_PW, returnType);
	}
	
	@Test
	public void testFalseWhenUserPwIsMoreThenSixteen() {
		// Given
		String userId = "abcdefghijklmnA12345678";
		
		// When
		ReturnType returnType = accountManager.validatePassword(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_PW, returnType);
	}
	
	@Test
	public void testFalseWhenUserPwContainSpecialCharacter() {
		// Given
		String userId = "abcde!0abC";
		
		// When
		ReturnType returnType = accountManager.validatePassword(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_PW, returnType);
	}
	
	@Test
	public void testFalseWhenUserPwBeginsWithNumber() {
		// Given
		String userId = "1abcdefG0";
		
		// When
		ReturnType returnType = accountManager.validatePassword(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_PW, returnType);
	}
	
	@Test
	public void testFalseWhenUserPwHaveNotCapital() {
		// Given
		String userId = "abcdefg00";
		
		// When
		ReturnType returnType = accountManager.validatePassword(userId);
		
		// Then
		assertEquals(ReturnType.INVALID_PW, returnType);
	}
	
	@Test
	public void testTrueWhenValidUserPw() {
		// Given
		String userId = "abcdeGHi00";
		
		// When
		ReturnType returnType = accountManager.validatePassword(userId);
		
		// Then
		assertEquals(ReturnType.SUCCESS, returnType);
	}
}
