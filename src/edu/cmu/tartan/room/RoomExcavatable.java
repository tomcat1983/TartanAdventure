package edu.cmu.tartan.room;

import edu.cmu.tartan.item.Item;

import java.util.List;
import java.util.LinkedList;

public class RoomExcavatable extends Room {

	protected String digMessage;
	protected boolean wasDugUp;
	protected List<Item> revealableItems;

	public RoomExcavatable(String description, String shortDescription, String digMessage) {
		super(description, shortDescription);
		this.digMessage = digMessage;
		this.wasDugUp = false;
		this.revealableItems = new LinkedList<>();
	}

    /**
     * Set the item(s) to be excavated
     * @param items the list of excavatable items
     */
	public void setRevealableItems(List<Item> items) {
		if(items != null) {
			this.revealableItems = items;
		}
	}

    /**
     * Perform the excavation
     */
	public boolean dig() {
		if(this.getPlayer().hasItem(Item.getInstance("shovel"))) {
			gameInterface.println(digMessage);
			this.wasDugUp = true;
			this.getItems().addAll(this.revealableItems);
			return true;
		}
		else {
			gameInterface.println("You do not have an item you can use to dig.");
			return false;
		}
	}
}
