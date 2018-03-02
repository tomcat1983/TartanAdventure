package edu.cmu.tartan.properties;

import edu.cmu.tartan.item.Item;

// couldn't think of a better name...
public interface Hostable {
	void install(Item item);
	boolean uninstall(Item item);
	Item installedItem();
}
