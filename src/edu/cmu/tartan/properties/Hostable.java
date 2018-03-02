package edu.cmu.tartan.properties;

import edu.cmu.tartan.item.Item;

// couldn't think of a better name...
public interface Hostable {
	public final static String OPERATION = "host";
	void install(Item item);
	boolean uninstall(Item item);
	Item installedItem();
}
