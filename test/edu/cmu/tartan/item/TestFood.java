package edu.cmu.tartan.item;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestFood {

	static final String USER_ID = "default";
	ItemFood food; 
	ItemDiamond diamond;
	ItemGold gold; 
	
	@BeforeEach
	void setup() {
		food = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		diamond = new ItemDiamond(StringForItems.DIAMOND, StringForItems.WHITE_DIAMOND, new String[]{StringForItems.DIAMOND, StringForItems.JEWEL}, USER_ID);
    	gold = new ItemGold(StringForItems.GOLD, StringForItems.SHINY_GOLD_BAR, new String[]{StringForItems.GOLD, StringForItems.BAR}, USER_ID);
  
	}
	
	
	@Test
	void testFoodHaveSameHashCodeWithoutHiddenItem() { 
		ItemFood food2 = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		int hashCode1 = food.hashCode(); 
		int hashCode2 = food2.hashCode(); 
		assertEquals(hashCode1, hashCode2);
	}
	
	@Test
	void testFoodHaveSameHashCodeWithSameHiddenItem() { 
		ItemFood food2 = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		food.setMeltItem(diamond);
		food2.setMeltItem(diamond);
		int hashCode1 = food.hashCode(); 
		int hashCode2 = food2.hashCode(); 
		assertEquals(hashCode1, hashCode2);
	}
	
	@Test
	void testFoodHaveDiffHashCodeWithDiffHiddenItem() { 
		ItemFood food2 = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		food.setMeltItem(diamond);
		food2.setMeltItem(gold);
		int hashCode1 = food.hashCode(); 
		int hashCode2 = food2.hashCode(); 
		assertNotEquals(hashCode1, hashCode2);
	}
	
	@Test
	void testFoodNotEqualDiamond() {
		assertFalse(food.equals(diamond));
	}
	
	@Test
	void testFoodNotEqualFakeGold() {
		ItemGold goldPretendToFood = new ItemGold(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD, StringForItems.FOOD}, USER_ID);
		assertFalse(food.equals(goldPretendToFood));
	}
	
	@Test
	void testFoodEqualWithoutHiddenItem() {
		ItemFood food2 = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		assertTrue(food2.equals(food));
		assertTrue(food.equals(food2));
	}
	
	@Test
	void testFoodEqualWithSameHiddenItem() {
		ItemFood food2 = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		food.setMeltItem(diamond);
		food2.setMeltItem(diamond);
		assertTrue(food2.equals(food));
		assertTrue(food.equals(food2));
	}
	
	@Test
	void testFoodNotEqualWithDiffHiddenItem() {
		ItemFood food2 = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		food.setMeltItem(diamond);
		food2.setMeltItem(gold);
		assertFalse(food2.equals(food));
		assertFalse(food.equals(food2));
	}
	
	@Test
	void testFoodNotEqualWithNullHidden() {
		ItemFood food2 = new ItemFood(StringForItems.FOOD, StringForItems.FOOD, new String[]{StringForItems.FOOD}, USER_ID);
		food2.setMeltItem(gold);
		assertFalse(food2.equals(food));
		assertFalse(food.equals(food2));
	}
}