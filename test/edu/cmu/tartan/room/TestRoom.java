package edu.cmu.tartan.room;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.PlayerExecutionEngine;
import edu.cmu.tartan.PlayerInterpreter;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemKey;
import edu.cmu.tartan.item.ItemShovel;

public class TestRoom {

	public static final String FORK_ROOM_DESCRIPTION = "There is a fork";
	public static final String PARK_ROOM_DESCRIPTION = "There is a park";
	public static final String FORK = "Fork";
	public static final String PARK = "Park";
	public static final String TR_MESSAGE = "GoGoGo East";
	
	public static final int TR_TIME = 1000;
	
	private Room room1;
	private Room room2;
	
	@AfterEach
	public void testItemallVisible() {
		Item.getInstance("brick", Player.DEFAULT_USER_NAME).setVisible(true);
		Item.getInstance("key", Player.DEFAULT_USER_NAME).setVisible(true);
		Item.getInstance("lock", Player.DEFAULT_USER_NAME).setVisible(true);
	    Item.getInstance("gold", Player.DEFAULT_USER_NAME).setVisible(true);
	}
	
	public void makeSameRoom() {
		room1 = new Room(FORK_ROOM_DESCRIPTION, FORK);
        room2 = new Room(FORK_ROOM_DESCRIPTION, FORK);
	}
	
	public void makeDifferentRoom() {
		room1 = new Room(FORK_ROOM_DESCRIPTION, FORK);
        room2 = new Room(PARK_ROOM_DESCRIPTION, PARK);
	}
	
	@Test
	void testCanRoomSerialization() {
		room1 = new Room(TestRoom.FORK_ROOM_DESCRIPTION, TestRoom.FORK);
		Player player = new Player(room1, Player.DEFAULT_USER_NAME);

		ArrayList<Item> testItems = new ArrayList<>();
		testItems.add(Item.getInstance("brick", Player.DEFAULT_USER_NAME));
		testItems.add(Item.getInstance("key", Player.DEFAULT_USER_NAME));
		testItems.add(Item.getInstance("lock", Player.DEFAULT_USER_NAME));
	    testItems.add(Item.getInstance("gold", Player.DEFAULT_USER_NAME));

	    assertTrue(room1.putItems(testItems));
	    
		try {
			FileOutputStream fos = new FileOutputStream("Room.serial");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(room1);
			out.close();

			FileInputStream fis = new FileInputStream("Room.serial");
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);
			final Room restoredRoom = (Room) in.readObject();
			assertEquals(restoredRoom, room1);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}				
	}

	@Test
	public void testTrueWhenCallsetPlayerAndgetPlayer() {
		makeSameRoom();
		Player player = new Player(room1, Player.DEFAULT_USER_NAME);
	    		
	    room1.setPlayer(player);
		assertNotNull(room1.getPlayer());
	}

	
	@Test
	public void testTrueFalseFalseWhenCallvisibleItems() {
		makeSameRoom();
		ArrayList<Item> testItems = new ArrayList<>();
		testItems.add(Item.getInstance("brick", Player.DEFAULT_USER_NAME));
		testItems.add(Item.getInstance("key", Player.DEFAULT_USER_NAME));
		testItems.add(Item.getInstance("lock", Player.DEFAULT_USER_NAME));
	    testItems.add(Item.getInstance("gold", Player.DEFAULT_USER_NAME));
	    
	    String result = room1.visibleItems();		
	    assertTrue("".equals(result));
		
	    assertTrue(room1.putItems(testItems));
		result = room1.visibleItems();

		assertFalse("".equals(result));
		assertFalse("".equals(room1.toString()));
	}

	@Test
	public void testTrueWhenCallToputItemsAndgetItems() {
		makeSameRoom();
		ArrayList<Item> testItems = new ArrayList<>();
		testItems.add(Item.getInstance("brick", Player.DEFAULT_USER_NAME));
		testItems.add(Item.getInstance("key", Player.DEFAULT_USER_NAME));
		testItems.add(Item.getInstance("lock", Player.DEFAULT_USER_NAME));
	    testItems.add(Item.getInstance("gold", Player.DEFAULT_USER_NAME));
				
	    assertTrue(room1.putItems(testItems));
	    ArrayList<Item> getItemsList = new ArrayList<>();
		getItemsList = room1.getItems();
		
		assertEquals(testItems, getItemsList);
	}
	
	@Test
	public void testFalseWhenCallToputItemAndWithhasItem() {
		makeSameRoom();
		ItemKey keyItem = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
		keyItem.setVisible(false);
		ItemKey keytItem2 = null;
				
		assertTrue(room1.putItem(keyItem));
		if(room1.hasItem(keyItem)) {
			keytItem2 = (ItemKey)room1.remove(keyItem);
		}
		assertFalse(keyItem.equals(keytItem2));
	}
	
	@Test
	public void testNullWhenCallToremoveWithhasItem() {
		makeSameRoom();
		ItemKey keyItem = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
		ItemKey keytItem2 = null;
		if(!room1.hasItem(keyItem)) {
			keytItem2 = (ItemKey)room1.remove(keyItem);
		}
		assertNull(keytItem2);
	}
	
	@Test
	public void testTrueWhenCallToputItemAndremoveWithhasItem() {
		makeSameRoom();
		ItemKey keyItem = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
		ItemKey keytItem2 = null;
		
		keyItem.setVisible(true);
				
		assertTrue(room1.putItem(keyItem));
		if(room1.hasItem(keyItem)) {
			keytItem2 = (ItemKey)room1.remove(keyItem);
		}
		assertEquals(keyItem, keytItem2);
	}
	
	@Test
	public void testTruesWhenCallTosetAdjacentRoomTransitionMessageWithDelay() {
		makeSameRoom();
		
		room1.setAdjacentRoomTransitionMessageWithDelay(TR_MESSAGE, Action.ACTION_GO_EAST, TR_TIME);
		
		assertTrue(room1.transitionDelay()==TR_TIME);
		
		Map<Action, String>messageMap = room1.transitionMessages();
		assertTrue(messageMap.get(Action.ACTION_GO_EAST).equals(TR_MESSAGE));
	}
	
	@Test
	public void testFalseTrueWhenCallTocanMoveToRoomInDirectionWithsetAdjacentRoom() {
		makeSameRoom();
		
		assertFalse(room1.canMoveToRoomInDirection(Action.ACTION_GO_EAST));
		
		assertTrue(room1.setAdjacentRoom(Action.ACTION_GO_EAST, room2));
		
		assertTrue(room1.canMoveToRoomInDirection(Action.ACTION_GO_EAST));
		
		assertFalse(room1.setAdjacentRoom(Action.ACTION_GO_EAST, room2));
	}
	
	@Test
	public void testTrueWhenCallTogetDirectionForRoomWithoutsetAdjacentRoom() {
		makeSameRoom();
				
		assertTrue(room1.getDirectionForRoom(room2)==Action.ACTION_UNKNOWN);
	}

	
	@Test
	public void testTrueWhenCallTogetDirectionForRoomWithsetAdjacentRoom() {
		makeSameRoom();
		
		room1.setAdjacentRoom(Action.ACTION_GO_EAST, room2);
		
		assertTrue(room1.getDirectionForRoom(room2)==Action.ACTION_GO_EAST);
	}
	
	@Test
	public void testNullWhenCallTogetRoomForDirectionWithoutsetAdjacentRoom() {
		makeSameRoom();
				
		assertNull(room1.getRoomForDirection(Action.ACTION_GO_EAST));
	}

	
	@Test
	public void testTrueWhenCallTogetRoomForDirectionWithsetAdjacentRoom() {
		makeSameRoom();
		
		room1.setAdjacentRoom(Action.ACTION_GO_EAST, room2);
		
		assertTrue(room1.getRoomForDirection(Action.ACTION_GO_EAST).equals(room2));
	}
	
	@Test
	public void testFalseWhenCallWithoutsetAdjacentRoom() {
		makeSameRoom();
				
		assertFalse(room1.isAdjacentToRoom(room2));
	}

	
	@Test
	public void testTrueWhenCallWithsetAdjacentRoom() {
		makeSameRoom();
		
		room1.setAdjacentRoom(Action.ACTION_GO_EAST, room2);
		
		assertTrue(room1.isAdjacentToRoom(room2));
	}
	
	@Test
	public void testEqualsWhenshortDescriptionCall() {
		makeSameRoom();
		
		assertEquals(FORK, room1.shortDescription());
	}
	
	@Test
	public void testEqualsWhenDescriptionCall() {
		makeSameRoom();
		
		assertEquals(FORK_ROOM_DESCRIPTION, room1.description());
	}
	
	@Test
	public void testNotEqualsAndHashCode() {
		makeDifferentRoom();
		
        assertFalse(room1.equals(room2) && room2.equals(room1));
        assertFalse(room1.hashCode() == room2.hashCode());
	}
	
	@Test
	public void testEqualsAndHashCode() {
		makeSameRoom();
		
        assertTrue(room1.equals(room2) && room2.equals(room1));
        assertTrue(room1.hashCode() == room2.hashCode());
        assertEquals(0, room1.compareTo(room2));
	}

	@Test
	public void testEqualsCompareTo() {
		makeSameRoom();
        
        assertEquals(0, room1.compareTo(room2));
	}
	
	@Test
	public void testNotEqualsCompareTo() {
		makeDifferentRoom();
        
        assertEquals(-1, room1.compareTo(room2));
	}
	
	@Test
	public void testWhenConstructor() {
		Room room = new Room();
		assertEquals(Room.DEFAULT_DESC, room.description());
		assertEquals(Room.DEFAULT_SHORT_DESC, room.shortDescription());
	}
}
