package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemBrick;
import edu.cmu.tartan.item.ItemButton;
import edu.cmu.tartan.item.ItemClayPot;
import edu.cmu.tartan.item.ItemDocument;
import edu.cmu.tartan.item.ItemDynamite;
import edu.cmu.tartan.item.ItemFolder;
import edu.cmu.tartan.item.ItemFood;
import edu.cmu.tartan.item.ItemKey;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.item.ItemMicrowave;
import edu.cmu.tartan.item.ItemSafe;
import edu.cmu.tartan.item.ItemShovel;
import edu.cmu.tartan.item.ItemVendingMachine;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;
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
    	
    	action = interpreter.interpretString("pickup safe", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionPickupItemAndDropItem() {
    	ItemKey key = (ItemKey) Item.getInstance("key");
    	ItemButton button = (ItemButton) Item.getInstance("Button");
    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit");
    	room1.putItem(key);
    	room1.putItem(button);
    	room1.putItem(mbox);
    	
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
    	
    	player.grabItem(mbox);
		action = interpreter.interpretString("drop pit", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	
    	RoomRequiredItem room2 = new RoomRequiredItem("You are in the room that required food", "Required",
                "pit", "Warning you need key", mbox);
    	player = new Player(room2);
    	playerExecutionEngine = new PlayerExecutionEngine(player);
    	action = interpreter.interpretString("drop pit", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionInspect() {
    	ItemKey key = (ItemKey) Item.getInstance("key");
    	room1.putItem(key);

    	// Inspect
    	Action action = interpreter.interpretString("inspect pot", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("inspect key", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionThrow() {
    	ItemBrick brick = (ItemBrick) Item.getInstance("brick");
    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit");
    	player.grabItem(brick);
    	player.grabItem(mbox);

    	// Throw
    	Action action = interpreter.interpretString("throw brick", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("throw key", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("throw pit", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionShake() {
		ItemVendingMachine vm = (ItemVendingMachine) Item.getInstance("machine");
		room1.putItem(vm);
		ItemKey key = (ItemKey) Item.getInstance("key");
    	player.grabItem(key);
    	// Shake
    	Action action = interpreter.interpretString("shake machine", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("shake key", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("shake pit", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionEnable() {
		ItemMicrowave micro = (ItemMicrowave) Item.getInstance("microwave");
		room1.putItem(micro);
		ItemKey key = (ItemKey) Item.getInstance("key");
    	player.grabItem(key);
    	// Enable
    	Action action = interpreter.interpretString("enable machine", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	action = interpreter.interpretString("enable key", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("enable microwave", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit");
    	ItemFood food = (ItemFood) Item.getInstance("food");
    	food.setMeltItem(mbox);
    	micro.install(food);
    	
    	action = interpreter.interpretString("enable microwave", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionPush() {
		ItemButton button = (ItemButton) Item.getInstance("button");
		room1.putItem(button);
		ItemKey key = (ItemKey) Item.getInstance("key");
    	player.grabItem(key);
    	// Enable
    	Action action = interpreter.interpretString("push machine", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	action = interpreter.interpretString("push key", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("push button", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
        String elevatorDescription = "Elevator";
        RoomElevator elevator = new RoomElevator(elevatorDescription, elevatorDescription);
        elevator.putItem(Item.getInstance("1"));
        elevator.putItem(Item.getInstance("2"));
        elevator.putItem(Item.getInstance("3"));
        elevator.putItem(Item.getInstance("4"));
        // configure the floors and buttons needed to reach them
        Room floor1 = new Room("floor1", elevatorDescription);
        Item b1 = Item.getInstance("1");
        b1.setRelatedRoom(elevator);
        floor1.putItem(b1);
        Room floor2 = new Room("floor2", elevatorDescription);
        Item b2 = Item.getInstance("2");
        b2.setRelatedRoom(elevator);
        floor2.putItem(b2);
        Room floor3 = new Room("floor3", elevatorDescription);
        Item b3 = Item.getInstance("3");
        b3.setRelatedRoom(elevator);
        floor3.putItem(b3);
        // restricted floors cannot be reached
        Room floor4 = new Room("floor4", elevatorDescription);
        Item b4 = Item.getInstance("4");
        b4.setRelatedRoom(elevator);
        floor4.putItem(b4);
        ArrayList<Room> list = new ArrayList<>();
        list.add(floor1);
        list.add(floor2);
        list.add(floor3);
        list.add(floor4);
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("floor1");
        descriptions.add("floor2");
        descriptions.add("floor3");
        descriptions.add("floor4");
        elevator.setFloors(descriptions, list, Action.ACTION_GO_EAST, 1);
        ArrayList<Integer> restrictedFloors = new ArrayList<>();
        restrictedFloors.add(2);
        elevator.setRestrictedFloors(restrictedFloors);
        player = new Player(elevator);
        playerExecutionEngine = new PlayerExecutionEngine(player);
    	
    	action = interpreter.interpretString("push 1", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	action = interpreter.interpretString("push 2", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	action = interpreter.interpretString("push 3", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	action = interpreter.interpretString("push 4", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionDig() {
		ItemShovel shovel = (ItemShovel) Item.getInstance("shovel");
		room1.putItem(shovel);
		player.grabItem(shovel);
		// Dig
    	Action action = interpreter.interpretString("dig shovel", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	RoomExcavatable romm2 = new RoomExcavatable("Shovel","digdig","^~~~^");
    	player = new Player(romm2);
    	player.grabItem(shovel);
    	playerExecutionEngine = new PlayerExecutionEngine(player);
    	action = interpreter.interpretString("dig shovel", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));	
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionEat() {
		ItemFood food = (ItemFood) Item.getInstance("food");
    	Action action = interpreter.interpretString("eat food", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

		room1.putItem(food);
    	action = interpreter.interpretString("eat food", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit");
    	RoomExcavatable romm2 = new RoomExcavatable("Shovel","digdig","^~~~^");
    	player = new Player(romm2);
		player.grabItem(food);
		player.grabItem(mbox);
		playerExecutionEngine = new PlayerExecutionEngine(player);
    	action = interpreter.interpretString("eat food", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	action = interpreter.interpretString("eat pit", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
//    	ItemShovel shovel = (ItemShovel) Item.getInstance("shovel");
//    	player.grabItem(shovel);
//    	action = interpreter.interpretString("eat shovel", actionExecutionUnit);
//    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}

	@Test
	void testWhenexecuteActionCallWithDirectObjectActionOpen() {
        ItemSafe safe = (ItemSafe)Item.getInstance("safe");
        safe.setInspectMessage("This safe appears to require a 4 digit PIN number.");
        safe.setPIN(9292);

        ItemDocument document = (ItemDocument) Item.getInstance("document");
        document.setInspectMessage("The document is encrypted with a cipher. The cryptographers at the CIA will need to decrypt it.");
        safe.install(document);
        document.setVisible(false);

        ItemFolder folder = (ItemFolder)Item.getInstance("folder");
        folder.setOpenMessage("Good for you");
    	room1.putItem(safe);
    	room1.putItem(folder);
    	
    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit");
    	player.grabItem(mbox);
    	//room1.putItem(document);
        
    	// open
//    	Action action = interpreter.interpretString("open safe", actionExecutionUnit);
//    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	Action action = interpreter.interpretString("open folder", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("open pit", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	document.setVisible(true);
    	action = interpreter.interpretString("open document", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	action = interpreter.interpretString("open key", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}

	@Test
	void testWhenexecuteActionCallWithDirectObjectActionExplode() {
        ItemDynamite dynamite = (ItemDynamite)Item.getInstance("dynamite");
        ItemDocument document = (ItemDocument) Item.getInstance("document");
        document.setInspectMessage("The document is encrypted with a cipher. The cryptographers at the CIA will need to decrypt it.");

    	room1.putItem(dynamite);
    	room1.putItem(document);
    	
    	Action action = interpreter.interpretString("detonate document", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("detonate key", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	action = interpreter.interpretString("detonate dynamite", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	action = interpreter.interpretString("east room2", actionExecutionUnit);
    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
    	
    	Room room2 = new Room();
    	dynamite.setRelatedRoom(room2);
    	room1.setAdjacentRoom(action, room2);
    	action = interpreter.interpretString("detonate dynamite", actionExecutionUnit);
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}
}
