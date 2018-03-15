package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Destroyable;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Valuable;

public class ItemClayPot extends Item implements Destroyable, Holdable, Hostable {

    protected String destroyMessage;
    protected Item installedItem;
    protected boolean disappears;

    public ItemClayPot(String s, String sd, String[] a) {
        super(s, sd, a);
        this.installedItem = null;
        setValue(3);
    }
    public void setDestroyMessage(String s) {
        this.destroyMessage = s;
    }

    public void destroy() {
        System.out.println(destroyMessage);
    }

    public void setDisappears(boolean b) {
        this.disappears = b;
    }

    public boolean disappears() {
        return this.disappears;
    }

    public void install(Item i) {
        this.installedItem = i;
    }

    public boolean uninstall(Item i) {
        if (this.installedItem == null) {
            return false;
        } else if (this.installedItem == i) {
            this.installedItem = null;
            return true;
        } else {
            return false;
        }
    }

    public Item installedItem() {
        return this.installedItem;
    }
}
