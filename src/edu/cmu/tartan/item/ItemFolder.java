package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Openable;

public class ItemFolder extends Item implements Openable {

    public ItemFolder(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    public void open() {
        System.out.println(this.openMessage);
    }

    public void setOpenMessage(String o) {
        this.openMessage = o;
    }

    protected String openMessage;
}
