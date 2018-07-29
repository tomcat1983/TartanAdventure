package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestGamePlayMessage {

	@Test
	void testItShouldThrowIllegalAccessException() {
		assertThrows(IllegalAccessException.class,() -> {
			Class cls = Class.forName("edu.cmu.tartan.GamePlayMessage");
		    cls.newInstance();
    	});
	}
}
