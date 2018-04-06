package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Openable;

/**
 * This class for a folder, which can be opened.
 * <p/>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemFolder extends Item implements Openable {

    private String openMessage;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
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
