package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemFood;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomDark;
import edu.cmu.tartan.room.RoomLockable;
import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.room.RoomRequiredItem;

class TestGameValidate {

	GameValidate gv;
	GameContext gc;
	private ArrayList<Room> rooms = new ArrayList<>();
	private ArrayList<Room> noRoom = new ArrayList<>();
	private static final String USER_ID = "Default";
	private static final String ROOM1 = "ROOM1";
	private static final String ROOM2 = "ROOM2";
	private static final String ROOM3 = "ROOM3";
	private static final String ROOM4 = "ROOM4";
	private static final String ROOM5 = "ROOM5";
	private static final String ROOM6 = "ROOM6";
	private static final String ROOM7 = "ROOM7";
	private static final String ROOM8 = "ROOM8";

	Item luminousItem = Item.getInstance("torch", USER_ID);
	Item food = Item.getInstance("food", USER_ID);
	Item requiredItemGold = Item.getInstance("gold", USER_ID);
	Item requiredItemDia = Item.getInstance("diamond", USER_ID);
	Item obscuringItem = Item.getInstance("fridge", USER_ID);
	Item key = Item.getInstance("key", USER_ID);
	Item lock = Item.getInstance("lock", USER_ID);

	ArrayList<MapConfig> errorList;
	
	@BeforeEach
	void setUp() {
		
		gc = new GameContext(USER_ID);
		
		Room room1Normal = new Room(ROOM1, ROOM1);
		RoomDark room2Dark = new RoomDark(ROOM2, ROOM2, "dark", "dark", true);
		RoomRequiredItem room3Require = new RoomRequiredItem(ROOM3, ROOM3, requiredItemGold);
		RoomRequiredItem room4Require = new RoomRequiredItem(ROOM4, ROOM4, requiredItemDia);
		RoomObscured room5Obscure = new RoomObscured(ROOM5, ROOM5, obscuringItem);
		RoomObscured room6Obscure = new RoomObscured(ROOM6, ROOM6, obscuringItem);
		RoomLockable room7Lock = new RoomLockable(ROOM7, ROOM7, true, key);
		
		((ItemFood) food).setMeltItem(requiredItemDia);
		
		room1Normal.putItem(luminousItem);
		room1Normal.putItem(requiredItemGold);
		room1Normal.putItem(food);
		room1Normal.putItem(lock);
		room1Normal.putItem(key);
		
		room5Obscure.setObscuringItem(obscuringItem);
		
		rooms.add(room1Normal);
		rooms.add(room2Dark);
		rooms.add(room3Require);
		rooms.add(room4Require);
		rooms.add(room5Obscure);
		rooms.add(room6Obscure);
		rooms.add(room7Lock);
	}
	
	@Test
	void testNoRoom() {
		gc.setRooms(noRoom);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertTrue(errorList.contains(MapConfig.NO_ROOM));
	}
	
	@Test
	void testHaveLuminousItem() {
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertFalse(errorList.contains(MapConfig.NO_LUMINOUS));
	}
	
	@Test
	void testDontHaveLuminousItem() {
		rooms.get(0).remove(luminousItem);
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertTrue(errorList.contains(MapConfig.NO_LUMINOUS));
	}
	
	@Test
	void testHaveRequiredItem() {
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertFalse(errorList.contains(MapConfig.NO_REQUIRED_ITEM));
	}
	
	@Test
	void testDontHaveRequiredItemGold() {
		rooms.get(0).remove(requiredItemGold);
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertTrue(errorList.contains(MapConfig.NO_REQUIRED_ITEM));
	}
	
	@Test
	void testDontHaveRequiredItemHiddenDiamond() {
		rooms.get(0).remove(food);
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertTrue(errorList.contains(MapConfig.NO_REQUIRED_ITEM));
	}
	
	@Test
	void testHaveObscuringItem() {
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertFalse(errorList.contains(MapConfig.NO_OBSTACLE));
	}
	
	@Test
	void testDontHaveObscuringItem() {
		RoomObscured room8Obscure = new RoomObscured(ROOM8, ROOM8);
		rooms.add(room8Obscure);
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertTrue(errorList.contains(MapConfig.NO_OBSTACLE));
	}
	
	@Test
	void testHaveItemLock() {
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertFalse(errorList.contains(MapConfig.NO_LOCK));
	}
	
	@Test
	void testDontHaveItemLock() {
		rooms.get(0).remove(lock);
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertTrue(errorList.contains(MapConfig.NO_LOCK));
	}
	
	@Test
	void testHaveItemKey() {
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertFalse(errorList.contains(MapConfig.NO_KEY));
	}
	
	@Test
	void testDontHaveItemKey() {
		rooms.get(0).remove(key);
		gc.setRooms(rooms);
		GameValidate gv = new GameValidate(gc);
		errorList = gv.check();
		assertTrue(errorList.contains(MapConfig.NO_KEY));
	}
	
	
	@SuppressWarnings("unused")
	private void printErrorList() { 
		
		if(errorList.isEmpty())
			System.out.println("No Error");
		else {
			for (MapConfig mapConfig : errorList) {
				System.out.println(mapConfig.name());
			}
		}
	}
	
	class GameValidateSpy extends GameValidate {

		public GameValidateSpy(GameContext gameContext) {
			super(gameContext);
		}
		
	}
}
