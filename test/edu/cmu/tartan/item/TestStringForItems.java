package edu.cmu.tartan.item;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestStringForItems {

	@BeforeEach
	void setup() { 
		
	}
	
	@Test
	public void testIllegalStateException() throws IllegalStateException {

		assertThrows(IllegalStateException.class,()->{
			new StringForItems();
		});
	}
	
	@Test
	public void testItemName() { 
		assertEquals("shovel", StringForItems.SHOVEL);
		assertEquals("metal shovel", StringForItems.METAL_SHOVEL);
		assertEquals("brick", StringForItems.BRICK);
		assertEquals("clay brick", StringForItems.CLAY_BRICK);
		assertEquals("food", StringForItems.FOOD);
		assertEquals("ladder", StringForItems.LADDER);
		assertEquals("wooden ladder", StringForItems.WOODEN_LADDER);
		assertEquals("key", StringForItems.KEY);
		assertEquals("gold key", StringForItems.GOLD_KEY);
		assertEquals("lock", StringForItems.LOCK);
		assertEquals("gold lock", StringForItems.GOLD_LOCK);
		assertEquals("keycard", StringForItems.KEYCARD);
		assertEquals("plastic keycard", StringForItems.PLASTIC_KEYCARD);
		assertEquals("card", StringForItems.CARD);
		assertEquals("keycard reader", StringForItems.KEYCARD_READER);
		assertEquals("metal keycard reader", StringForItems.METAL_KEYCARD_READER);
		assertEquals("reader", StringForItems.READER);
		assertEquals("slot", StringForItems.SLOT);
		assertEquals("pot", StringForItems.POT);
		assertEquals("clay pot", StringForItems.CLAY_POT);
		assertEquals("pottery", StringForItems.POTTERY);
		assertEquals("diamond", StringForItems.DIAMOND);
		assertEquals("white diamond", StringForItems.WHITE_DIAMOND);
		assertEquals("jewel", StringForItems.JEWEL);
		assertEquals("gold", StringForItems.GOLD);
		assertEquals("shiny gold bar", StringForItems.SHINY_GOLD_BAR);
		assertEquals("bar", StringForItems.BAR);
		assertEquals("microwave", StringForItems.MICROWAVE);
		assertEquals("microwave that stinks of month old popcorn", StringForItems.MICROWAVE_DESC);
		assertEquals("appliance", StringForItems.APPLIANCE);
		assertEquals("fridge", StringForItems.FRIDGE);
		assertEquals("white refrigerator", StringForItems.WHITE_REFRIGERATOR);
		assertEquals("refrigerator", StringForItems.REFRIGERATOR);
		assertEquals("flashlight", StringForItems.FLASHLIGTH);
		assertEquals("battery operated flashlight", StringForItems.FLASHLIGTH_MESSAGE);
		assertEquals("torch", StringForItems.TORCH);
		assertEquals("metal torch", StringForItems.METAL_TORCH);
		assertEquals("candle", StringForItems.CANDLE);
		assertEquals("pit", StringForItems.PIT);
		assertEquals("bottomless pit", StringForItems.BOTTOMLESS_PIT);
		assertEquals("hole", StringForItems.HOLE);
		assertEquals("machine", StringForItems.MACHINE);
		assertEquals("vending machine with assorted candies and treats", StringForItems.VENDING_MACHINE_DESC);
		assertEquals("vendor", StringForItems.VENDOR);
		assertEquals("safe", StringForItems.SAFE);
		assertEquals("bullet-proof safe", StringForItems.SAFE_DESC);
		assertEquals("folder", StringForItems.FOLDER);
		assertEquals("manilla folder", StringForItems.MANILLA_FOLDER);
		assertEquals("document", StringForItems.DOCUMENT);
		assertEquals("Secret document", StringForItems.SECRET_DOCUMENT);
		assertEquals("fan", StringForItems.FAN);
		assertEquals("ventilation fan", StringForItems.VENTILATION_FAN);
		assertEquals("computer", StringForItems.COMPUTER);
		assertEquals("Apple computer", StringForItems.APPLE_COMPUTER);
		assertEquals("apple", StringForItems.APPLE);
		assertEquals("keyboard", StringForItems.KEYBOARD);
		assertEquals("imac", StringForItems.IMAC);
		assertEquals("cpu", StringForItems.CPU);
		assertEquals("Apple computer cpu", StringForItems.APPLE_COMPUTER_CPU);
		assertEquals("apple cpu", StringForItems.APPLE_CPU);
		assertEquals("coffee", StringForItems.COFFEE);
		assertEquals("steaming cup of coffee", StringForItems.COFFEE_STAMING);
		assertEquals("beverage", StringForItems.BEVERAGE);
		assertEquals("mug", StringForItems.MUG);
		assertEquals("light", StringForItems.LIGHT);
		assertEquals("desk light", StringForItems.DESK_LIGHT);
		assertEquals("dynamite", StringForItems.DYNAMITE);
		assertEquals("bundle of dynamite", StringForItems.BUNDLE_OF_DYNAMITE);
		assertEquals("explosive", StringForItems.EXPLOSIVE);
		assertEquals("explosives", StringForItems.EXPLOSIVES);
		assertEquals("Elevator Button", StringForItems.ELEVATOR_BUTTON);
		assertEquals("button", StringForItems.BUTTON_SMALL);
		assertEquals("Button", StringForItems.BUTTON);
		assertEquals("Floor 1 Button", StringForItems.FLOOR_1_BUTTON);
		assertEquals("Floor 2 Button", StringForItems.FLOOR_2_BUTTON);
		assertEquals("Floor 3 Button", StringForItems.FLOOR_3_BUTTON);
		assertEquals("Floor 4 Button", StringForItems.FLOOR_4_BUTTON);
		assertEquals("Elevator Floor 1 Button", StringForItems.ELEVATOR_FLOOR_1);
		assertEquals("Elevator Floor 2 Button", StringForItems.ELEVATOR_FLOOR_2);
		assertEquals("Elevator Floor 3 Button", StringForItems.ELEVATOR_FLOOR_3);
		assertEquals("Elevator Floor 4 Button", StringForItems.ELEVATOR_FLOOR_4);
		assertEquals("1", StringForItems.ONE);
		assertEquals("2", StringForItems.TWO);
		assertEquals("3", StringForItems.THREE);
		assertEquals("4", StringForItems.FOUR);
		assertEquals("unknown", StringForItems.UNKNOWN);
	}
	

}
