package edu.cmu.tartan.properties;

import edu.cmu.tartan.item.Item;

public interface Meltable  {
	void setMeltItem(Item item);
	Item meltItem();
}
