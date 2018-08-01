package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.TestRoom;

class TestPlayer {

	private Room room1;
	private Player player;
	private Player player2;

	public static final String TJ = "TJ";
	public static final String KT = "KT";

	
	public void makeSamePlayer() {
		room1 = new Room(TestRoom.FORK_ROOM_DESCRIPTION, TestRoom.FORK);
		player = new Player(room1, TJ);
		player2 = new Player(room1, TJ);
	}
	
	public void makeDifferentPlayer() {
		room1 = new Room(TestRoom.FORK_ROOM_DESCRIPTION, TestRoom.FORK);
		player = new Player(room1, TJ);
		player2 = new Player(room1, KT);
	}
	
	@Test
	void testCanPlayerSerialization() {
		room1 = new Room(TestRoom.FORK_ROOM_DESCRIPTION, TestRoom.FORK);
		player = new Player(room1, Player.DEFAULT_USER_NAME);

		ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
		
		assertFalse(player.grabItem(null));
		assertFalse(player.hasItem(null));
		assertFalse(player.addGoal(null));
		assertFalse(player.pickup(null));
		assertFalse(player.dropItem(null));
		assertTrue(player.drop(null)==null);
		assertTrue(player.putItemInItem(Item.getInstance("key", Player.DEFAULT_USER_NAME), mbox));
		assertFalse(player.putItemInItem(mbox, Item.getInstance("key", Player.DEFAULT_USER_NAME)));
		assertTrue(player.grabItem(Item.getInstance("lock", Player.DEFAULT_USER_NAME)));
		assertTrue(player.grabItem(Item.getInstance("gold", Player.DEFAULT_USER_NAME)));
		assertTrue(player.addGoal(new GamePointsGoal(100, player)));
		assertTrue(player.setUserName("KK"));
		assertTrue(player.score(10));
		assertFalse(player.score(-1));
		assertTrue(player.addPossiblePoints(100));
		assertFalse(player.addPossiblePoints(0));
		ArrayList<Item> testItems = new ArrayList<>();
		testItems.add(Item.getInstance("brick", Player.DEFAULT_USER_NAME));
		testItems.add(Item.getInstance("key", Player.DEFAULT_USER_NAME));		
	    assertTrue(room1.putItems(testItems));
	    
		try {
			FileOutputStream fos = new FileOutputStream("Player.serial");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(player);
			out.close();

			FileInputStream fis = new FileInputStream("Player.serial");
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);
			final Player restoredPlayer = (Player) in.readObject();
			assertEquals(restoredPlayer, player);
			assertTrue(restoredPlayer.getUserName().equals(player.getUserName()));
			assertTrue(restoredPlayer.getPossiblePoints()==player.getPossiblePoints());
			assertTrue(restoredPlayer.getRoomsVisited().equals(player.getRoomsVisited()));
			assertTrue(restoredPlayer.getCollectedItems().equals(player.getCollectedItems()));
			assertTrue(restoredPlayer.getScore()==player.getScore());
			assertTrue(restoredPlayer.getGoals().get(0).describe().equals(player.getGoals().get(0).describe()));
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	@Test
	public void testNotEqualsAndHashCode() {
		makeDifferentPlayer();
		
        assertFalse(player.equals(player2) && player2.equals(player));
        assertFalse(player.hashCode() == player2.hashCode());
        assertEquals(-1, player.compareTo(player2));
        
        player2.setUserName(null);
        assertFalse(player.hashCode() == player2.hashCode());
	}

	@Test
	public void testNotEqualsCompareTo() {
		makeDifferentPlayer();
		
        assertEquals(-1, player.compareTo(player2));
	}
	
	@Test
	public void testEqualsAndHashCode() {
		makeSamePlayer();
		
        assertTrue(player.equals(player2) && player2.equals(player));
        assertTrue(player.hashCode() == player2.hashCode());
        assertEquals(0, player.compareTo(player2));
	}

	@Test
	public void testEqualsCompareTo() {
		makeSamePlayer();
		
        assertEquals(0, player.compareTo(player2));
	}

}
