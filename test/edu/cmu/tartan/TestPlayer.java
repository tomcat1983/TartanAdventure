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

import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.TestRoom;

class TestPlayer {

	private Room room1;
	private Player player;

	@Test
	void testCanPlayerSerialization() {
		room1 = new Room(TestRoom.FORK_ROOM_DESCRIPTION, TestRoom.FORK);
		player = new Player(room1, Player.DEFAULT_USER_NAME);

		player.grabItem(Item.getInstance("lock"));
		player.grabItem(Item.getInstance("gold"));

		ArrayList<Item> testItems = new ArrayList<>();
		testItems.add(Item.getInstance("brick"));
		testItems.add(Item.getInstance("key"));		
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
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
}
