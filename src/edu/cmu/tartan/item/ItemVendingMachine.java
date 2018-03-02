package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Shakeable;

public class ItemVendingMachine extends Item implements Shakeable {

    public ItemVendingMachine(String d, String sd, String[] a) {
        super(d, sd, a);
        this.count = 0;
    }

    public boolean deadly() {
        return this.count > 2;
    }

    public void shake() {
        switch (this.count) {
            case 0:
                System.out.println("You shake the vending machine, and your favorite treat inches its way off the tray.");
                break;
            case 1:
                System.out.println("The treat begins to bend toward the will of gravity.");
                break;
            case 2:
                System.out.println("Just as the candy falls, the machine also falls over and crushes your body.");
                break;
            default:
                break;
        }
        this.count++;
    }

    protected int count;
}
