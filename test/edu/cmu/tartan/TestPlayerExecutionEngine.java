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
import edu.cmu.tartan.item.ItemCPU;
import edu.cmu.tartan.item.ItemClayPot;
import edu.cmu.tartan.item.ItemCoffee;
import edu.cmu.tartan.item.ItemComputer;
import edu.cmu.tartan.item.ItemDocument;
import edu.cmu.tartan.item.ItemDynamite;
import edu.cmu.tartan.item.ItemFlashlight;
import edu.cmu.tartan.item.ItemFolder;
import edu.cmu.tartan.item.ItemFood;
import edu.cmu.tartan.item.ItemKey;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.item.ItemMicrowave;
import edu.cmu.tartan.item.ItemSafe;
import edu.cmu.tartan.item.ItemShovel;
import edu.cmu.tartan.item.ItemVendingMachine;
import edu.cmu.tartan.item.StringForItems;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomDark;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomLockable;
import edu.cmu.tartan.room.RoomRequiredItem;
import edu.cmu.tartan.room.TestRoom;
import edu.cmu.tartan.room.TestRoomDark;

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
		player = new Player(room1, Player.DEFAULT_USER_NAME);
		playerExecutionEngine = new PlayerExecutionEngine(player);
		actionExecutionUnit = new ActionExecutionUnit(null, null);
		actionExecutionUnit.setUserId(Player.DEFAULT_USER_NAME);
		
		Item.getInstance("pot", Player.DEFAULT_USER_NAME).setVisible(true);
		Item.getInstance("key", Player.DEFAULT_USER_NAME).setVisible(true);
	}
	
	@Test
	void testWhenexecuteActionCallWithDestoryWithDirectObjectAnd() {
		ItemKey keyItem = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
    	room1.putItem(keyItem);
    	// Destroy key
    	Action action = interpreter.interpretString("destroy key", actionExecutionUnit);
		try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
			// Destroy non-exist item
	    	action = interpreter.interpretString("destroy pot", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
			// Destroy real item
			ItemClayPot itemClayPot = (ItemClayPot) Item.getInstance("pot", Player.DEFAULT_USER_NAME);
			room1.putItem(itemClayPot);
			action = interpreter.interpretString("destroy pot", actionExecutionUnit);
			assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionAndPlayerInterpreterCallWithWithDirectObjectWithWrongAction() {
		ItemKey keyItem = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
    	room1.putItem(keyItem);
 
		try {
	    	Action action = interpreter.interpretString("pickup keyaaa", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));	
			
	    	action = interpreter.interpretString("pickup key aaa", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));	    	

	    	action = interpreter.interpretString("pickupa key", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));	    	

			action = interpreter.interpretString("", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));	    	

			action = interpreter.interpretString(" ", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
			
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionAtDropItem() {
    	ItemKey key = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
    	ItemButton button = (ItemButton) Item.getInstance("Button", Player.DEFAULT_USER_NAME);
    	room1.putItem(key);
    	room1.putItem(button);

		// drop key
		Action action = interpreter.interpretString("drop key", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	// pickup key
	    	action = interpreter.interpretString("pickup key", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
			// drop key
			action = interpreter.interpretString("drop key", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testWhenexecuteActionCallWithDirectObjectActionPickupItemAndItemSafe() {
        ItemSafe safe = (ItemSafe)Item.getInstance("safe", Player.DEFAULT_USER_NAME);
        safe.setInspectMessage("This safe appears to require a 4 digit PIN number.");
        safe.setPIN(9292);

        ItemDocument document = (ItemDocument) Item.getInstance("document", Player.DEFAULT_USER_NAME);
        document.setInspectMessage("The document is encrypted with a cipher. The cryptographers at the CIA will need to decrypt it.");
        safe.install(document);
        document.setVisible(false);

    	room1.putItem(safe);
    	//room1.putItem(document);
        
    	// pickup key
    	Action action = interpreter.interpretString("pickup document", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	document.setVisible(true);
	    	action = interpreter.interpretString("pickup document", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("pickup safe", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionPickupItemAndDropItem() {
    	ItemKey key = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
    	ItemButton button = (ItemButton) Item.getInstance("Button", Player.DEFAULT_USER_NAME);
    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
    	room1.putItem(key);
    	room1.putItem(button);
    	room1.putItem(mbox);
    	
    	// pickup key
    	Action action = interpreter.interpretString("pickup pot", actionExecutionUnit);
    	try {
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
	    	player = new Player(room2, Player.DEFAULT_USER_NAME);
	    	playerExecutionEngine = new PlayerExecutionEngine(player);
	    	action = interpreter.interpretString("drop pit", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionInspect() {
    	// Inspect
    	Action action = interpreter.interpretString("inspect pot", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
			ItemKey key = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
			key.setInspectMessage("It's a key.");
	    	room1.putItem(key);

	    	action = interpreter.interpretString("inspect key", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionThrow() {
    	ItemBrick brick = (ItemBrick) Item.getInstance("brick", Player.DEFAULT_USER_NAME);
    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
    	player.grabItem(brick);
    	player.grabItem(mbox);

    	// Throw
    	Action action = interpreter.interpretString("throw brick", actionExecutionUnit);
    	try {
			assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("throw key", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("throw pit", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionShake() {
		ItemVendingMachine vm = (ItemVendingMachine) Item.getInstance("machine", Player.DEFAULT_USER_NAME);
		vm.setCount(0);		
		room1.putItem(vm);
		ItemKey key = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
    	player.grabItem(key);
    	// Shake
    	Action action = interpreter.interpretString("shake machine", actionExecutionUnit);
    	try {
			assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("shake key", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("shake pit", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionEnable() {
		ItemMicrowave micro = (ItemMicrowave) Item.getInstance("microwave", Player.DEFAULT_USER_NAME);
		room1.putItem(micro);
		ItemKey key = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
    	player.grabItem(key);
    	// Enable
    	Action action = interpreter.interpretString("enable machine", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("enable key", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("enable microwave", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
	    	ItemFood food = (ItemFood) Item.getInstance("food", Player.DEFAULT_USER_NAME);
	    	food.setMeltItem(mbox);
	    	micro.install(food);
	    	//player.grabItem(food);
	    	//action = interpreter.interpretString("put food in microwave", actionExecutionUnit);
	    	//assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("enable microwave", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionPush() {
		ItemButton button = (ItemButton) Item.getInstance("button", Player.DEFAULT_USER_NAME);
		room1.putItem(button);
		ItemKey key = (ItemKey) Item.getInstance("key", Player.DEFAULT_USER_NAME);
    	player.grabItem(key);
    	// Enable
    	Action action = interpreter.interpretString("push machine", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("push key", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("push button", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	        String elevatorDescription = "Elevator";
	        RoomElevator elevator = new RoomElevator(elevatorDescription, elevatorDescription);
	        elevator.putItem(Item.getInstance("1", Player.DEFAULT_USER_NAME));
	        elevator.putItem(Item.getInstance("2", Player.DEFAULT_USER_NAME));
	        elevator.putItem(Item.getInstance("3", Player.DEFAULT_USER_NAME));
	        elevator.putItem(Item.getInstance("4", Player.DEFAULT_USER_NAME));
	        // configure the floors and buttons needed to reach them
	        Room floor1 = new Room("floor1", elevatorDescription+"1");
	        Item b1 = Item.getInstance("1", Player.DEFAULT_USER_NAME);
	        b1.setRelatedRoom(elevator);
	        floor1.putItem(b1);
	        Room floor2 = new Room("floor2", elevatorDescription+"2");
	        Item b2 = Item.getInstance("2", Player.DEFAULT_USER_NAME);
	        b2.setRelatedRoom(elevator);
	        floor2.putItem(b2);
	        Room floor3 = new Room("floor3", elevatorDescription+"3");
	        Item b3 = Item.getInstance("3", Player.DEFAULT_USER_NAME);
	        b3.setRelatedRoom(elevator);
	        floor3.putItem(b3);
	        // restricted floors cannot be reached
	        Room floor4 = new Room("floor4", elevatorDescription+"4");
	        Item b4 = Item.getInstance("4", Player.DEFAULT_USER_NAME);
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
	        player = new Player(elevator, Player.DEFAULT_USER_NAME);
	        playerExecutionEngine = new PlayerExecutionEngine(player);
	    	
	    	action = interpreter.interpretString("push 1", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("push 2", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("push 3", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("push 4", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("push 4", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	assertTrue((floor1.compareTo(floor2))==-1);
	    	assertTrue((floor1.compareTo(floor1))==0);
	    	assertTrue((floor1.hashCode())==(floor1.hashCode()));
	    	assertTrue(floor1.hashCode()!=floor2.hashCode());
	    	assertTrue(floor1.equals(floor1));
	    	assertFalse(floor1.equals(floor2));

		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionDig() {
		ItemShovel shovel = (ItemShovel) Item.getInstance("shovel", Player.DEFAULT_USER_NAME);
		room1.putItem(shovel);
		player.grabItem(shovel);
		// Dig
    	Action action = interpreter.interpretString("dig shovel", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));	    	
	    	RoomExcavatable romm2 = new RoomExcavatable("Shovel","digdig","^~~~^");
	    	player = new Player(romm2, Player.DEFAULT_USER_NAME);
	    	player.grabItem(shovel);
	    	playerExecutionEngine = new PlayerExecutionEngine(player);
	    	action = interpreter.interpretString("dig shovel", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithDirectObjectActionEat() {
		ItemFood food = (ItemFood) Item.getInstance("food", Player.DEFAULT_USER_NAME);
		ItemCoffee coffee = (ItemCoffee) Item.getInstance("coffee", Player.DEFAULT_USER_NAME);
    	Action action = interpreter.interpretString("eat food", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

			room1.putItem(food);
	    	action = interpreter.interpretString("eat food", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	room1.putItem(coffee);
	    	action = interpreter.interpretString("eat coffee", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
	    	RoomExcavatable romm2 = new RoomExcavatable("Shovel","digdig","^~~~^");
	    	player = new Player(romm2, Player.DEFAULT_USER_NAME);
			player.grabItem(food);
			player.grabItem(mbox);
			assertFalse(mbox.uninstall(food));
			assertFalse(mbox.install(null));
			assertTrue(mbox.installedItem()==null);
			playerExecutionEngine = new PlayerExecutionEngine(player);
	    	action = interpreter.interpretString("eat food", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("eat pit", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testWhenexecuteActionCallWithDirectObjectActionOpen() {
        ItemSafe safe = (ItemSafe)Item.getInstance("safe", Player.DEFAULT_USER_NAME);
        safe.setInspectMessage("This safe appears to require a 4 digit PIN number.");
        safe.setPIN(9292);

        ItemDocument document = (ItemDocument) Item.getInstance("document", Player.DEFAULT_USER_NAME);
        document.setInspectMessage("The document is encrypted with a cipher. The cryptographers at the CIA will need to decrypt it.");
        safe.install(document);
        document.setVisible(false);

        ItemFolder folder = (ItemFolder)Item.getInstance("folder", Player.DEFAULT_USER_NAME);
        folder.setOpenMessage("Good for you");
    	room1.putItem(safe);
    	room1.putItem(folder);
    	
    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
    	player.grabItem(mbox);
    	//room1.putItem(document);
        
    	// open
//    	Action action = interpreter.interpretString("open safe", actionExecutionUnit);
//    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

    	Action action = interpreter.interpretString("open folder", actionExecutionUnit);
    	try {
			assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("open pit", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	document.setVisible(true);
	    	action = interpreter.interpretString("open document", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("open key", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testWhenexecuteActionCallWithDirectObjectActionExplode() {
        ItemDynamite dynamite = (ItemDynamite)Item.getInstance("dynamite", Player.DEFAULT_USER_NAME);
        ItemDocument document = (ItemDocument) Item.getInstance("document", Player.DEFAULT_USER_NAME);
        document.setInspectMessage("The document is encrypted with a cipher. The cryptographers at the CIA will need to decrypt it.");

    	room1.putItem(dynamite);
    	room1.putItem(document);
    	
    	Action action = interpreter.interpretString("detonate document", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("detonate key", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("detonate dynamite", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("go east", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	Room room2 = new Room();
	    	dynamite.setRelatedRoom(room2);
	    	assertTrue(room1.setAdjacentRoom(action, room2));
	    	action = interpreter.interpretString("detonate dynamite", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("detonate dynamite", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testWhenexecuteActionCallWithMove() {
		RoomDark room2 = new RoomDark(TestRoomDark.DARK_ROOM_DESC1, TestRoomDark.DARK_ROOM_SHORT_DESC1, TestRoomDark.DARK_DESC, TestRoomDark.DARK_SHORT_DESC);
		ItemFlashlight flashlight = (ItemFlashlight) Item.getInstance("flashlight", Player.DEFAULT_USER_NAME);
		assertTrue(room1.setAdjacentRoom(Action.ACTION_GO_WEST, room2));
		player.grabItem(flashlight);
		
		Action action = interpreter.interpretString("go east", actionExecutionUnit);
    	try {
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("go west", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	    	
	    	ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
	    	RoomRequiredItem room3 = new RoomRequiredItem("You are in the room that required food", "Required",
	                "pit", "Warning you need key", mbox);
			assertTrue(room2.setAdjacentRoom(Action.ACTION_GO_WEST, room3));
			player.grabItem(mbox);
			
	    	action = interpreter.interpretString("go w", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void testWhenexecuteActionCallWithMoveWithWrongDirection() {
		RoomDark room2 = new RoomDark(TestRoomDark.DARK_ROOM_DESC1, TestRoomDark.DARK_ROOM_SHORT_DESC1, TestRoomDark.DARK_DESC, TestRoomDark.DARK_SHORT_DESC);
		ItemFlashlight flashlight = (ItemFlashlight) Item.getInstance("flashlight", Player.DEFAULT_USER_NAME);
		assertTrue(room1.setAdjacentRoom(Action.ACTION_GO_DOWN, room2));
		player.grabItem(flashlight);
		
    	try {
    		Action action = interpreter.interpretString("go go", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

			action = interpreter.interpretString("go west go ", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("go wests", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	    	
	    	action = interpreter.interpretString("go west go deefsdf", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("travel up", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("move down", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("move down", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("move up", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testWhenexecuteActionCallWithHasNoObject() {
		Action action = interpreter.interpretString("lookAround", actionExecutionUnit);
    	try {
			assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	action = interpreter.interpretString("l", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("jump", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	    			
	    	action = interpreter.interpretString("climb", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("inventory", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

			ItemFlashlight flashlight = (ItemFlashlight) Item.getInstance("flashlight", Player.DEFAULT_USER_NAME);
			player.grabItem(flashlight);
	    	
	    	action = interpreter.interpretString("inventory", actionExecutionUnit);
	    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("pass", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithInDirectObject() {
    	try {
    		Action action = interpreter.interpretString("put key in pit", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	ItemFlashlight flashlight = (ItemFlashlight) Item.getInstance("flashlight", Player.DEFAULT_USER_NAME);
			player.grabItem(flashlight);
			ItemDocument document = (ItemDocument) Item.getInstance("document", Player.DEFAULT_USER_NAME);
	        document.setInspectMessage("The document is encrypted with a cipher. The cryptographers at the CIA will need to decrypt it.");
	    	
	    	action = interpreter.interpretString("put flashlight in anyva", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("put flashlight in pit", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("remove key from pit", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	    	
	    	action = interpreter.interpretString("remove key from document", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));

	    	action = interpreter.interpretString("remove any from pit", actionExecutionUnit);
	    	assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithInDirectObjectWithComputerAndCPU() {
    	try {
    		ItemComputer com = (ItemComputer)Item.getInstance(StringForItems.COMPUTER, Player.DEFAULT_USER_NAME);
    		ItemCPU cpu = (ItemCPU)Item.getInstance(StringForItems.CPU, Player.DEFAULT_USER_NAME);
    		player.grabItem(cpu);
    		room1.putItem(com);
    		Action action = interpreter.interpretString("put cpu in computer", actionExecutionUnit);
			assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));

			action = interpreter.interpretString("remove cpu from computer", actionExecutionUnit);
			assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));			
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWhenexecuteActionCallWithInDirectObjectWithPutNoHostableAndNoInstall() {
    	try {
    		ItemComputer com = (ItemComputer)Item.getInstance(StringForItems.COMPUTER, Player.DEFAULT_USER_NAME);
    		ItemCPU cpu = (ItemCPU)Item.getInstance(StringForItems.CPU, Player.DEFAULT_USER_NAME);
    		ItemShovel shovel = (ItemShovel) Item.getInstance("shovel", Player.DEFAULT_USER_NAME);
    		ItemVendingMachine vm = (ItemVendingMachine) Item.getInstance("machine", Player.DEFAULT_USER_NAME);
        	player.grabItem(shovel);
    		player.grabItem(cpu);
    		room1.putItem(com);
    		room1.putItem(vm);

    		Action action = interpreter.interpretString("put shovel in computer", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
			
			action = interpreter.interpretString("put cpu in machine", actionExecutionUnit);
			assertFalse(playerExecutionEngine.executeAction(action, actionExecutionUnit));
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testItShouldThrowTerminateGameExceptionWhenEatUneatableItem() throws TerminateGameException {
    	ItemShovel shovel = (ItemShovel) Item.getInstance("shovel", Player.DEFAULT_USER_NAME);
    	player.grabItem(shovel);
    	Action action = interpreter.interpretString("eat shovel", actionExecutionUnit);
    	assertThrows(TerminateGameException.class,() -> {
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);	
    	});
	}
	
	@Test
	public void testItShouldThrowTerminateGameExceptionWhenUserInputTerminate() throws TerminateGameException {
		// terminate
    	Action action = interpreter.interpretString("terminate", actionExecutionUnit);
    	assertThrows(TerminateGameException.class,() -> {
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);
    	});
	}
	
	@Test
	public void testItShouldThrowTerminateGameExceptionWhenUserShakeVendingMachine() throws TerminateGameException {
    	assertThrows(TerminateGameException.class,() -> {
    		// Shake
    		ItemVendingMachine vm = (ItemVendingMachine) Item.getInstance("machine", Player.DEFAULT_USER_NAME);
    		room1.putItem(vm);
    		Action action = interpreter.interpretString("shake machine", actionExecutionUnit);
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);
    	});
	}

	@Test
	public void testItShouldThrowTerminateGameExceptionWhenUserMoveRoomLockableWithoutKey() {
		Room end = new RoomLockable("You are inside of a building", "Building interior", true, Item.getInstance("key", Player.DEFAULT_USER_NAME));
		//RoomDark room2 = new RoomDark(TestRoomDark.DARK_ROOM_DESC1, TestRoomDark.DARK_ROOM_SHORT_DESC1, TestRoomDark.DARK_DESC, TestRoomDark.DARK_SHORT_DESC);
		//ItemFlashlight flashlight = (ItemFlashlight) Item.getInstance("flashlight", Player.DEFAULT_USER_NAME);
		assertTrue(room1.setAdjacentRoom(Action.ACTION_GO_WEST, end));
		//player.grabItem(flashlight);
		
		try {
			Action action = interpreter.interpretString("go west", actionExecutionUnit);
			playerExecutionEngine.executeAction(action, actionExecutionUnit);
			action = interpreter.interpretString("go east", actionExecutionUnit);
			playerExecutionEngine.executeAction(action, actionExecutionUnit);    		
		} catch (TerminateGameException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testItShouldThrowTerminateGameExceptionWhenUserMoveRoomRequiredCheck() throws TerminateGameException {
		ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
		RoomRequiredItem room2 = new RoomRequiredItem("You are in the room that required food", "Required",
	            "pit", "Warning you need key", mbox);
		player = new Player(room1, Player.DEFAULT_USER_NAME);
		playerExecutionEngine = new PlayerExecutionEngine(player);
		assertTrue(room1.setAdjacentRoom(Action.ACTION_GO_WEST, room2));
		((RoomRequiredItem)room2).setPlayerDiesOnEntry(true);
		
		assertThrows(TerminateGameException.class,() -> {
    		Action action = interpreter.interpretString("go west", actionExecutionUnit);
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);
    	});
	}

	@Test
	public void testItShouldThrowTerminateGameExceptionWhenUserDropItemAboutRoomRequired() throws TerminateGameException {
		ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
		RoomRequiredItem room2 = new RoomRequiredItem("You are in the room that required food", "Required",
	            "pit", "Warning you need key", mbox);
		room2.setSafeDirection(Action.ACTION_GO_EAST);
		room2.setLoseMessage("You Lose");
		player = new Player(room2, Player.DEFAULT_USER_NAME);
		playerExecutionEngine = new PlayerExecutionEngine(player);	
		player.grabItem(mbox);
		
		assertThrows(TerminateGameException.class,() -> {
    		Action action = interpreter.interpretString("drop pit", actionExecutionUnit);
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);
    		((RoomRequiredItem)room2).setPlayerDiesOnItemDiscard(true);
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);
    	});
	}
	
	@Test
	public void testItShouldThrowTerminateGameExceptionWhenUserMoveOtherRoomInCurrentRoomTypeIsRoomRequired()throws TerminateGameException {
		ItemMagicBox mbox = (ItemMagicBox) Item.getInstance("pit", Player.DEFAULT_USER_NAME);
		RoomRequiredItem room2 = new RoomRequiredItem("You are in the room that required food", "Required",
	            "pit", "Warning you need key", mbox);
		player = new Player(room2, Player.DEFAULT_USER_NAME);
		playerExecutionEngine = new PlayerExecutionEngine(player);	
		
		assertThrows(TerminateGameException.class,() -> {
    		Action action = interpreter.interpretString("go east", actionExecutionUnit);
    		playerExecutionEngine.executeAction(action, actionExecutionUnit);
    	});
	}

	@Test
	public void testItShouldThrowTerminateGameExceptionWhenUserMoveRoomDarkWithLight() throws TerminateGameException {
		RoomDark room2 = new RoomDark(TestRoomDark.DARK_ROOM_DESC1, TestRoomDark.DARK_ROOM_SHORT_DESC1, TestRoomDark.DARK_DESC, TestRoomDark.DARK_SHORT_DESC);
		player = new Player(room2, Player.DEFAULT_USER_NAME);
		playerExecutionEngine = new PlayerExecutionEngine(player);
		
		assertThrows(TerminateGameException.class,() -> {
			Action action = interpreter.interpretString("go west", actionExecutionUnit);
			playerExecutionEngine.executeAction(action, actionExecutionUnit);
    	});
		
	}
}
