package edu.cmu.tartan.item;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;
import edu.cmu.tartan.Player;


class TestItem {


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