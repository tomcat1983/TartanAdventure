package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;

public class ItemMagicBox extends Item implements Hostable {

    public ItemMagicBox(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    public void install(Item i) {
        //this.installedItem = i;
        // items fall into black hole
    }

    public boolean uninstall(Item i) {
        return false;
    }

    public Item installedItem() {
        return null;
    }
}
