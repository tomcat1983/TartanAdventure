package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;

public class ItemCPU extends Item implements Holdable, Installable  {
	
	/**
     * Constructor for a computer
     * @param s description
     * @param sd long description
     * @param a aliases
     */
	public ItemCPU(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(100);
    }
}
