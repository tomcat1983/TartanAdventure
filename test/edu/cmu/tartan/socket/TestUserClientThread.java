package edu.cmu.tartan.socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestUserClientThread {

	String threadName = "Thread1";
	
	
	@BeforeEach
	public void beforeTest() {
	}
	
	@Test
	public void testShouldReturnTrueWhenCalledSendMessageWithCorrectValue() {
		
		UserClientThread client;
		
//		Socket socket = mock(Socket.class);
//		IQueueHandler queue = mock(MessageQueue.class);
		
//		when(socket.getOutputStream()).thenReturn(mock(OutputStream.class));
//		when(socket.isConnected()).thenReturn(false);
		
//		client = new UserClientThread(socket, queue, threadName);
		
//		String message = "HELLO";
//		boolean returnValue = false;
//		returnValue = client.sendMessage(message);
//		assertEquals(true, returnValue);
		
	}
	
	@Disabled
	@Test
	public void testShouldReturnUserIdWhenCalledGetUserIdMethod() {
	}
	
	@Disabled
	@Test
	public void testShouldReturnThreadNameWhenCalledGetThreadNameMethod() {
	}
}
