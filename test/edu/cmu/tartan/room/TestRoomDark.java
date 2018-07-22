package edu.cmu.tartan.room;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestRoomDark {
	public static final String DARK_ROOM_DESC1 = "You are in a dark room. You can go South to West to the beginning and you can go South";
	public static final String DARK_ROOM_DESC2 = "You are in a dark room. You can go East to North to the beginning and you can go North";
	public static final String DARK_ROOM_SHORT_DESC1 = "room1";
	public static final String DARK_ROOM_SHORT_DESC2 = "room2";
	public static final String DARK_DESC = "You cannot see";
	public static final String DARK_SHORT_DESC = "blind!";
	
	private RoomDark room1;
    private RoomDark room2;

    private void makeRoomDark() {
    	room1 = new RoomDark(DARK_ROOM_DESC1, DARK_ROOM_SHORT_DESC1, DARK_DESC, "blind!");
    	room2 = new RoomDark(DARK_ROOM_DESC2, DARK_ROOM_SHORT_DESC2, DARK_DESC, "blind!");
    }
    
	@Test
	void testWhenConstructorRoomDark() {
		makeRoomDark();
		assertTrue(room1.equals(room2));
	}

}
