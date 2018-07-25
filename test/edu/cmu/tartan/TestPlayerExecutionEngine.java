package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemButton;
import edu.cmu.tartan.item.ItemClayPot;
import edu.cmu.tartan.item.ItemDocument;
import edu.cmu.tartan.item.ItemKey;
import edu.cmu.tartan.item.ItemSafe;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.TestRoom;

class TestPlayerExecutionEngine {

	private PlayerInterpreter interpreter;
	private Room room1;
	private Player player;
	private PlayerExecutionEngine playerExecutionEngine;
	private ActionExecutionUnit actionExecutionUnit;

	@BeforeEach
	void beforeEach() {
		interpreter = new PlayerInterpreter();
		room1 = new Room(TestRoom.FORK_ROOM_DESCRIPTION, TestRoom.FORK);
		player = new Player(room1);
		playerExecutionEngine = new PlayerExecutionEngine(player);
		actionExecutionUnit = new ActionExecutionUnit(null, null);
	}
	
	@AfterEach
	public void testItemallVisible() {
		Item.getInstance("pot").isVisible();
		Item.getInstance("key").isVisible();
	}

	@Test
	void testWhenexecuteActionCallWithDestoryWithDirectObjectAnd() {
		ItemKey keyItem = (ItemKey) Item.getInstance("key");
    	room1.putItem(keyItem);
    	// Destroy key
    	Action action = interpreter.interpretString("destroy key", actionExecutionUnit);
		assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		
		// Destroy non-exist item
    	action = interpreter.interpretString("destroy pot", actionExecutionUnit);
		assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
		// Destroy real item
		ItemClayPot itemClayPot = (ItemClayPot) Item.getInstance("pot");
		room1.putItem(itemClayPot);
		action = interpreter.interpretString("destroy pot", actionExecutionUnit);
		assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionAtDropItem() {
    	ItemKey key = (ItemKey) Item.getInstance("key");
    	ItemButton button = (ItemButton) Item.getInstance("Button");
    	room1.putItem(key);
    	room1.putItem(button);

		// drop key
		Action action = interpreter.interpretString("drop key", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	// pickup key
    	action = interpreter.interpretString("pickup key", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
		// drop key
		action = interpreter.interpretString("drop key", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}

	@Test
	void testWhenexecuteActionCallWithDirectObjectActionPickupItemAndItemSafe() {
        ItemSafe safe = (ItemSafe)Item.getInstance("safe");
        safe.setInspectMessage("This safe appears to require a 4 digit PIN number.");
        safe.setPIN(9292);

        ItemDocument document = (ItemDocument) Item.getInstance("document");
        document.setInspectMessage("The document is encrypted with a cipher. The cryptographers at the CIA will need to decrypt it.");
        safe.install(document);
        document.setVisible(false);

    	room1.putItem(safe);
    	//room1.putItem(document);
        
    	// pickup key
    	Action action = interpreter.interpretString("pickup document", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	document.setVisible(true);
    	action = interpreter.interpretString("pickup document", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionPickupItemAndDropItem() {
    	ItemKey key = (ItemKey) Item.getInstance("key");
    	ItemButton button = (ItemButton) Item.getInstance("Button");
    	room1.putItem(key);
    	room1.putItem(button);

    	// pickup key
    	Action action = interpreter.interpretString("pickup pot", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	action = interpreter.interpretString("pickup button", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	action = interpreter.interpretString("pickup key", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	action = interpreter.interpretString("pickup key", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
		// drop key
		action = interpreter.interpretString("drop key", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}

}
