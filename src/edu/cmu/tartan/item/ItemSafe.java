package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;

import java.util.Scanner;

public class ItemSafe extends Item implements Hostable, Openable {

    private int value=10;

    public ItemSafe(String d, String sd, String[] a) {
        super(d, sd, a);
        this.installedItem = null;
        this.pin = null;
    }

    public void setPIN(String pin) {
        this.pin = pin;
    }

    // Hostable
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
    public void open() {
        Scanner s = new Scanner(System.in);
        String input = "";
        System.out.println("Enter the four-digit PIN number.");
        input = s.nextLine();
        if (input.equals(this.pin)) {
            this.installedItem.setVisible(true);
            System.out.println("The safe door swings open.");
            if (this.installedItem != null) {
                System.out.println("You have revealed a " + this.installedItem.detailDescription() + ".");
            }
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    protected Item installedItem;
    protected String pin;

    @Override
    public int value() {
        return 10;
    }

    @Override
    public void setValue(int value) {

    }
}
