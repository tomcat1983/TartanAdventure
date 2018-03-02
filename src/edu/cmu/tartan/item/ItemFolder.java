package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Openable;

public class ItemFolder extends Item implements Openable {

    private int value = 3;

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

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;

    }
}
