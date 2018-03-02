package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Pushable;

public class ItemButton extends Item implements Pushable {

    public ItemButton(String s, String sd, String[] a) {
        super(s, sd, a);
        this.pushMessage = "Pushed.";
    }

    public void push() {
        System.out.println(this.pushMessage);
    }

    public void setPushMessage(String s) {
        this.pushMessage = s;
    }

    protected String pushMessage;
}
