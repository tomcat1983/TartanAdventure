package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Openable;

public class ItemFolder extends Item implements Openable {

    protected String openMessage;

    public ItemFolder(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(3);
    }

    public Boolean open() {

        System.out.println(this.openMessage);
        return true;
    }

    public void setOpenMessage(String o) {
        this.openMessage = o;
    }

}
