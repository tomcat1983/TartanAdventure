package edu.cmu.tartan.item;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.Player;

class TestItem {

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
	void testInstallKeyToLockReturnTrue() {
		assertTrue(lock.install(key));
	}
	
	@Test
	void testInstallFoodToLockReturnFalse() {
		assertFalse(lock.install(food));
	}
	
	@Test
	void testInstalledItemIsKey() {
		lock.install(key);  
		assertEquals(key, lock.installedItem());
	}
	
	
	
	@Test
	void testCanSerializeItem() {
		ItemShovel itemShovel = (ItemShovel) Item.getInstance("shovel", Player.DEFAULT_USER_NAME);
		try {
			FileOutputStream fos = new FileOutputStream("Item.serial");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(itemShovel);
			out.close();

			FileInputStream fis = new FileInputStream("Item.serial");
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);
			final ItemShovel restoredItemShovel = (ItemShovel) in.readObject();
			assertEquals(restoredItemShovel, itemShovel);
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	      
}