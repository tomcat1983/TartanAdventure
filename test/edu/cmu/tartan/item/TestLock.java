package edu.cmu.tartan.item;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomLockable;

class TestLock {

	static final String USER_ID = "default";
	ItemLock lock;
	ItemKey key;
	ItemFood food; 
	
	@BeforeEach
	void setup() {
		lock = new ItemLock(StringForItems.LOCK, StringForItems.GOLD_LOCK, new String[]{StringForItems.LOCK}, USER_ID);
		key = new ItemKey(StringForItems.KEY, StringForItems.GOLD_KEY, new String[]{StringForItems.KEY}, USER_ID);
		food = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
	}
	
	@Test
	void testLockInstallKeyOK() {
		assertTrue(lock.install(key));
	}
	
	@Test
	void testLockInstallFoodShouldntWork() {
		assertFalse(lock.install(food));
	}
	
	@Test
	void testLockInstalledItemIsKey() {
		lock.install(key);  
		assertEquals(key, lock.installedItem());
	}
	
	@Test
	void testLockInstalledKeyIsNotFood() {
		lock.install(key);  
		assertNotEquals(food, lock.installedItem());
	}
	
	@Test
	void testLockCantUninstallKeyAlreadyEmpty() {
		assertFalse(lock.uninstall(key));
	}
	
	@Test
	void testLockCantUninstallWithDifferentKey() {
		lock.install(key);
		ItemKey key2 = new ItemKey(StringForItems.KEY, StringForItems.GOLD_KEY, new String[]{StringForItems.KEY}, "tak");
		assertFalse(lock.uninstall(key2));
	}
	
	@Test
	void testLockUninstallKeyOk() {
		lock.install(key);
		assertTrue(lock.uninstall(key));
	}
	
	
	@Test
	void testLockOpenLockableRoom() {
		lock.install(key);
		Room room = new RoomLockable("LOCK", "LOCK", true, key);
		Player player = new Player(room, USER_ID);
		room.setPlayer(player);
		lock.setRelatedRoom(room);
		assertTrue(lock.open());
	}
	
	@Test
	void testLockCantOpenNormalRoom() {
		lock.install(key);
		Room room = new Room("NORMAL", "NORMAL");
		Player player = new Player(room, USER_ID);
		room.setPlayer(player);
		lock.setRelatedRoom(room);
		assertFalse(lock.open());
	}
	
	@Test
	void testLockHashCode() { 
		int hashCodeOfLock = lock.hashCode(); 
		int hashCodeOfKey = key.hashCode(); 
		lock.install(key);
		assertEquals(hashCodeOfLock + hashCodeOfKey, lock.hashCode());
	}
	
	@Test
	void testLockHashCodeSame() { 
		ItemLock lock2 = new ItemLock(StringForItems.LOCK, StringForItems.GOLD_LOCK, new String[]{StringForItems.LOCK}, "tak");
		assertEquals(lock.hashCode(), lock2.hashCode());
	}
	
	@Test
	void testLockNotEqualKey() {
		assertFalse(lock.equals(key));
	}
	
	@Test
	void testLockEqualLock2() {
		ItemLock lock2 = new ItemLock(StringForItems.LOCK, StringForItems.GOLD_LOCK, new String[]{StringForItems.LOCK}, USER_ID);
		assertTrue(lock.equals(lock2));
	}
	
	@Test
	void testLockNotEqualLock2WithKey1() {
		ItemLock lock2 = new ItemLock(StringForItems.LOCK, StringForItems.GOLD_LOCK, new String[]{StringForItems.LOCK}, USER_ID);
		lock2.install(key);
		assertFalse(lock.equals(lock2));
	}
	
	@Test
	void testLockEqualLock2WithEachHaveKey() {
		ItemLock lock2 = new ItemLock(StringForItems.LOCK, StringForItems.GOLD_LOCK, new String[]{StringForItems.LOCK}, USER_ID);
		ItemKey key2 = new ItemKey(StringForItems.KEY, StringForItems.GOLD_KEY, new String[]{StringForItems.KEY}, "tak");
		lock.install(key);
		lock2.install(key2);
		assertTrue(lock.equals(lock2));
	}
	
}