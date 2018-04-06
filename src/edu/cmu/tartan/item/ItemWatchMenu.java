package edu.cmu.tartan.item;

import java.util.LinkedList;

/**
 * This class for a watch menu,
 * <p/>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemWatchMenu {

    /**
     * Constructor
     * @param title the title for the menu
     */
    public ItemWatchMenu(String title) {

        this.title = title;
        this.text = null;
        this.menus = new LinkedList<ItemWatchMenu>();

        String prev = "Quit";
        if (!this.title.equals(prev)) {
            ItemWatchMenu exit = new ItemWatchMenu("Quit.");
            this.menus.add(exit);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void add(ItemWatchMenu menu) {
        this.menus.add(this.menus.size() - 1, menu);
    }

    public ItemWatchMenu get(int i) {
        return this.menus.get(i);
    }

    public int count() {
        return this.menus.size();
    }

    public String toString() {
        String title = (this.title != null) ? this.title + "\n\n" : "";
        String text = (this.text != null) ? this.text + "\n\n" : "";
        String top = title + text;
        String list = "";
        for (int i = 0; i < this.menus.size(); i++) {
            ItemWatchMenu menu = this.menus.get(i);
            list += (i + 1) + "  " + menu.getTitle() + "\n";
        }
        return top + list;
    }

    protected String title;
    protected String text;
    protected LinkedList<ItemWatchMenu> menus;
}
