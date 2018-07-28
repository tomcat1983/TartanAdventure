package edu.cmu.tartan.room;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemFlashlight;

public class TestRoomDark {
	public static final String DARK_ROOM_DESC1 = "You are in a dark room. You can go South to West to the beginning and you can go South";
	public static final String DARK_ROOM_DESC2 = "You are in a dark room. You can go East to North to the beginning and you can go North";
	public static final String DARK_ROOM_SHORT_DESC1 = "room1";
	public static final String DARK_ROOM_SHORT_DESC2 = "room2";
	public static final String DARK_DESC = "You cannot see";
	public static final String DARK_SHORT_DESC = "blind!";
	
	private RoomDark room1;
    private RoomDark room2;

    private void makeSameRoomDark() {
    	room1 = new RoomDark(DARK_ROOM_DESC1, DARK_ROOM_SHORT_DESC1, DARK_DESC, DARK_SHORT_DESC);
    	room2 = new RoomDark(DARK_ROOM_DESC1, DARK_ROOM_SHORT_DESC1, DARK_DESC, DARK_SHORT_DESC);
    }
    
    private void makeDifferntRoomDark() {
    	room1 = new RoomDark(DARK_ROOM_DESC1, DARK_ROOM_SHORT_DESC1, DARK_DESC, DARK_SHORT_DESC);
    	room2 = new RoomDark(DARK_ROOM_DESC2, DARK_ROOM_SHORT_DESC2, DARK_DESC, DARK_SHORT_DESC);
    }

    @Test
	void testWhensetDarkwithisDarkAnddescription() {
		makeSameRoomDark();
		ItemFlashlight item = (ItemFlashlight) Item.getInstance("flashlight", Player.DEFAULT_USER_NAME);
		Player player = new Player(room1, Player.DEFAULT_USER_NAME);

		room1.setDark(true);
		assertEquals(room1.description(), DARK_DESC);
		assertEquals(room1.description(), DARK_SHORT_DESC);
		
		room1.roomWasVisited = false;
		player.pickup(item);
		assertEquals(room1.description(), DARK_ROOM_DESC1 + "\n");
		assertEquals(room1.description(), DARK_ROOM_SHORT_DESC1);
		
		if(room1.isDark()) {
			room1.roomWasVisited = false;
			room1.setDark(false);
			assertEquals(room1.description(), DARK_ROOM_DESC1 + "\n");
			assertEquals(room1.description(), DARK_ROOM_SHORT_DESC1);
		}
		assertFalse(room1.isDark());
	}
    
    @Test
	void testWhensetDarkwithisDarkAndtoString() {
		makeSameRoomDark();
		ItemFlashlight item = (ItemFlashlight) Item.getInstance("flashlight", Player.DEFAULT_USER_NAME);
		Player player = new Player(room1, Player.DEFAULT_USER_NAME);

		room1.setDark(true);
		assertEquals(room1.toString(), DARK_DESC);
		
		player.pickup(item);
		assertEquals(room1.toString(), DARK_ROOM_DESC1);
		
		if(room1.isDark()) {
			room1.setDark(false);
			assertEquals(room1.toString(), DARK_ROOM_DESC1);
		}
		assertFalse(room1.isDark());
	}

	@Test
	void testWhenDeadMessage() {
		makeSameRoomDark();
		String death = "You're...die";
		room1.setDeathMessage(death);
		assertTrue(death.equals(room1.deathMessage()));
	}

    
	@Test
	void testWhenConstructorSameRoomDark() {
		makeSameRoomDark();
		assertTrue(room1.equals(room2));
	}
    
	@Test
	void testWhenConstructorRoomDark() {
		makeDifferntRoomDark();
		assertFalse(room1.equals(room2));
	}

}
