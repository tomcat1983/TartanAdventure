package edu.cmu.tartan.item;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.room.Room;

class TestIKeycardReader {

	static final String USER_ID = "default";
	ItemLock lock;
	ItemKey key;
	ItemFood food; 
	ItemKeycardReader keycardReader;
	ItemKeycard keycard; 
	
	@BeforeEach
	void setup() {
		lock = new ItemLock(StringForItems.LOCK, StringForItems.GOLD_LOCK, new String[]{StringForItems.LOCK}, USER_ID);
		key = new ItemKey(StringForItems.KEY, StringForItems.GOLD_KEY, new String[]{StringForItems.KEY}, USER_ID);
		food = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		keycardReader = new ItemKeycardReader(StringForItems.KEYCARD_READER, StringForItems.METAL_KEYCARD_READER, new String[]{StringForItems.READER, StringForItems.SLOT}, USER_ID);
		keycard = new ItemKeycard(StringForItems.KEYCARD, StringForItems.PLASTIC_KEYCARD, new String[]{StringForItems.KEYCARD, StringForItems.CARD}, USER_ID);
		keycardReader.setInstallMessage("installed message");
	}
	
	@Test
	void testReaderInstallKeycardOK() {
		food.setVisible(false);
		keycardReader.setRelatedItem(food);
		assertTrue(keycardReader.install(keycard));
	}
	
	@Test
	void testReaderInstallKeyShouldntWork() {
		food.setVisible(false);
		keycardReader.setRelatedItem(food);
		assertFalse(keycardReader.install(key));
	}
	
	@Test
	void testReaderInstalledItemIsKeycard() {
		food.setVisible(false);
		keycardReader.setRelatedItem(food);
		keycardReader.install(keycard);  
		assertEquals(keycard, keycardReader.installedItem());
	}
	
	@Test
	void testReaderInstalledKeycardIsNotFood() {
		food.setVisible(false);
		keycardReader.setRelatedItem(food);
		keycardReader.install(keycard);  
		assertNotEquals(food, keycardReader.installedItem());
	}
	
	@Test
	void testReaderCantUninstallKeycardAlreadyEmpty() {
		assertFalse(keycardReader.uninstall(keycard));
	}
	
	@Test
	void testReaderCantUninstallWithDifferentKeycard() {
		food.setVisible(false);
		keycardReader.setRelatedItem(food);
		keycardReader.install(keycard);
		ItemKeycard keycard2 = new ItemKeycard(StringForItems.KEYCARD, StringForItems.KEYCARD, new String[]{StringForItems.KEYCARD}, "tak");
		assertFalse(keycardReader.uninstall(keycard2));
	}
	
	@Test
	void testReaderUninstallKeyOk() {
		food.setVisible(false);
		keycardReader.setRelatedItem(food);
		keycardReader.install(keycard);
		assertTrue(keycardReader.uninstall(keycard));
	}
	
	
	@Test
	void testReaderFoodIsVisible() {
		food.setVisible(false);
		keycardReader.setRelatedItem(food);
		keycardReader.install(keycard);
		assertTrue(food.isVisible());
	}
	
	@Test
	void testReaderFoodIsNotVisible() {
		food.setVisible(false);
		keycardReader.setRelatedItem(food);
		keycardReader.install(key);
		assertFalse(food.isVisible());
		
	}
	
	@Test
	void testReaderCardHashCodeDiff() { 
		int hashCodeOfReader = keycardReader.hashCode(); 
		int hashCodeOfKeycard = keycard.hashCode(); 
		assertNotEquals(hashCodeOfReader, hashCodeOfKeycard);
	}
	
	@Test
	void testReaderNotEqualKeycard() {
		assertFalse(keycardReader.equals(keycard));
	}
	
	@Test
	void testReaderNotEqualReader2BeacuseInstallMessage() {
		ItemKeycardReader keycardReader2 = new ItemKeycardReader(StringForItems.KEYCARD_READER, StringForItems.METAL_KEYCARD_READER, new String[]{StringForItems.READER, StringForItems.SLOT}, "tak");
		assertFalse(keycardReader2.equals(keycardReader));
		assertFalse(keycardReader.equals(keycardReader2));
	}
	
	@Test
	void testReaderEqualReader2WameInstallMessage() {
		ItemKeycardReader keycardReader2 = new ItemKeycardReader(StringForItems.KEYCARD_READER, StringForItems.METAL_KEYCARD_READER, new String[]{StringForItems.READER, StringForItems.SLOT}, "tak");
		keycardReader2.setInstallMessage("installed message");
		assertTrue(keycardReader2.equals(keycardReader));
		assertTrue(keycardReader.equals(keycardReader2));
	}

	
}