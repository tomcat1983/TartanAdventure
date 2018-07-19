package edu.cmu.tartan.room;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestRoom {

	@Test
	public void testEqualsAndHashCode() {
		Room room1 = new Room("There is a fork", "Fork");
        Room room2 = new Room("There is a fork", "Fork");
        
        assertTrue(room1.equals(room2) && room2.equals(room1));
        assertTrue(room1.hashCode() == room2.hashCode());      
	}
}
