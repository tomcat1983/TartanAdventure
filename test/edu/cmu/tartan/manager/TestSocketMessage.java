package edu.cmu.tartan.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSocketMessage {
	
	SocketMessage socketMessage;
	String threadName = "Thread1";
	String message = "SUCCESS";
	
	@BeforeEach
	public void beforeTest() {
		socketMessage = new SocketMessage(threadName, message);
	}
	
	@Test
	public void testShouldBeEqualAsThreadNameOfConstructor() {
		assertEquals(threadName, socketMessage.getThreadName());
	}
	
	@Test
	public void testShouldBeEqualAsMessageOfConstructor() {
		assertEquals(message, socketMessage.getMessage());
	}

}
