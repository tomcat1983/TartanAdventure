package edu.cmu.tartan.socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.cmu.tartan.manager.IQueueHandler;


@ExtendWith(MockitoExtension.class)
public class TestSocketServer {
	
	SocketServer socketServer;

	@Mock
	IQueueHandler queue;
	
	@BeforeEach
	public void beforeTest() {
		socketServer = new SocketServer(queue);
	}
	
	@Disabled
	@Test
	public void testShouldReturnTrueWhenCalledStopSocket() {
		boolean returnValue = false;
		returnValue = socketServer.stopSocket();
		assertEquals(true, returnValue);
	}
	
	@Disabled
	@Test
	public void testShouldBeEqualAsSetValue() {
		boolean isPlaying = true;
		socketServer.setIsPlaying(isPlaying);
//		assertEquals(isPlaying, socketServer.getIsPlaying());
	}

}
