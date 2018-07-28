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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.TestRoom;

public class TestGameContext {

	private PlayerInterpreter interpreter;
	private Room room1;
	private Player player;
	private PlayerExecutionEngine playerExecutionEngine;
	private ActionExecutionUnit actionExecutionUnit;
	private GameContext context;

	@BeforeEach
	void beforeEach() {
		interpreter = new PlayerInterpreter();
		room1 = new Room(TestRoom.FORK_ROOM_DESCRIPTION, TestRoom.FORK);
		player = new Player(room1, Player.DEFAULT_USER_NAME);
		context = new GameContext(Player.DEFAULT_USER_NAME);
		playerExecutionEngine = new PlayerExecutionEngine(player);
		actionExecutionUnit = new ActionExecutionUnit(null, null);
		
		Item.getInstance("pot", Player.DEFAULT_USER_NAME).setVisible(true);
		Item.getInstance("key", Player.DEFAULT_USER_NAME).setVisible(true);
	}
	
	@Test
	void testCanSerialization() {
		player.grabItem(Item.getInstance("pot", Player.DEFAULT_USER_NAME));
		player.grabItem(Item.getInstance("key", Player.DEFAULT_USER_NAME));
		
		// Now we configure the goal based on picking up items
        ArrayList<String> goalItems = new ArrayList<>();
        goalItems.add("brick");
        goalItems.add("key");
        goalItems.add("gold");
        
		context.setPlayer(player);
        context.addGoal(new GameCollectGoal(goalItems, player));		
		context.setGameDescription("UnitTestGame");
		context.setGameName("TartanGame");
		context.setPlayerGameGoal();
		
		ArrayList<Item> testItems = new ArrayList<>();
		testItems.add(Item.getInstance("brick", Player.DEFAULT_USER_NAME));
		testItems.add(Item.getInstance("coffee", Player.DEFAULT_USER_NAME));		
	    assertTrue(room1.putItems(testItems));
	    
		try {
			FileOutputStream fos = new FileOutputStream("GameContext.serial");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(context);
			out.close();

			FileInputStream fis = new FileInputStream("GameContext.serial");
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);
			final GameContext restoredGameContext = (GameContext) in.readObject();
			assertEquals(restoredGameContext, context);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

}
