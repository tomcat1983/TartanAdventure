package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

import java.util.Scanner;
import java.util.Stack;

/**
 * This class for a watch, which can be held .
 * <p/>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemWatch extends Item implements Holdable, Valuable {

    protected Stack<ItemWatchMenu> stack;

    /**
     * Constructor
     * @param d description
     * @param sd long description
     * @param a aliases
     */
    public ItemWatch(String d, String sd, String[] a) {
        super(d, sd, a);
        this.stack = new Stack<ItemWatchMenu>();
        setValue(75);
    }

    // Inspectable
    public Boolean inspect() {

        Scanner s = new Scanner(System.in);
        String input = "";
        System.out.println("\nCIA SmartWatch v3.2.2\n");
        sleep(800);
        System.out.println("Authenticating via retina scan...");
        for (int i = 0; i < 3; i++) {
            sleep(800);
            System.out.println("...");
        }
        sleep(800);
        System.out.println("Retina scan complete. A hologram appears.");
        sleep(800);
        int n = 0;
        out:
        while (true) {
            ItemWatchMenu menu = this.stack.peek();
            System.out.println(menu.toString());
            while (true) {
                System.out.print("$ ");
                input = s.nextLine();
                try {
                    n = Integer.parseInt(input);
                } catch (Exception e) {
                    if (input.toLowerCase().equals("exit")) {
                        while (this.stack.size() > 1) stack.pop();
                        break out;
                    }
                    System.out.println("Invalid selection.");
                    continue;
                }
                if (n == menu.count()) {
                    if (this.stack.size() == 1) {
                        break out;
                    } else {
                        this.stack.pop();
                    }
                } else if (n > menu.count() || n < 0) {
                    System.out.println("Invalid selection.");
                    continue;
                } else {
                    this.stack.push(menu.get(n - 1));
                }
                break;
            }
        }
        System.out.println("You turn off the watch and lower your wrist.");
        return true;
    }

    public void setMenu(ItemWatchMenu main) {
        this.stack.push(main);
    }

    private void sleep(int s) {
        try {
            Thread.sleep(s);
        } catch (Exception e1) {
            // pass
        }
    }
}
