package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Pushable;

/**
 * A button can be pushed
 * <p/>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemButton extends Item implements Pushable {

    protected String pushMessage;

    public ItemButton(String s, String sd, String[] a) {
        super(s, sd, a);
        this.pushMessage = "Pushed.";
        setValue(2);
    }

    public void push() {
        System.out.println(this.pushMessage);
    }

    public void setPushMessage(String s) {
        this.pushMessage = s;
    }


}
