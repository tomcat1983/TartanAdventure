package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;

import java.util.Scanner;

public class ItemSafe extends Item implements Hostable, Openable {

    protected Item installedItem = null;
    protected Integer pin = null;

    public ItemSafe(String d, String sd, String[] a) {
        super(d, sd, a);
        this.installedItem = null;
        this.pin = null;
        setValue(750);
    }

    public void setPIN(Integer pin) {
        this.pin = pin;
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

    // Openable
    public Boolean open() {
        Scanner s = new Scanner(System.in);
        String input = "";
        System.out.println("Enter the four-digit PIN number.");
        Integer p = Integer.parseInt(s.nextLine());
        if (p.intValue() == this.pin.intValue()) {

            this.installedItem.setVisible(true);
            System.out.println("The safe door swings open.");
            if (this.installedItem != null) {
                System.out.println("You have revealed a '" + this.installedItem.detailDescription() + "'.");
            }
            return true;
        } else {
            System.out.println("Incorrect PIN.");
        }
        return false;
    }
}
