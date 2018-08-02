package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Hostable;

/**
 * This class for a computer
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemComputer extends Item implements Hostable {
	private static final long serialVersionUID = 1L;
	private Item installedItem;
    /**
     * Constructor for a computer
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemComputer(String s, String sd, String[] a, String userId) {
        super(s, sd, a, userId);
        setValue(50);
    }
    /**
     * Install an item in the microwave
     * @param i the item to install
     */
    @Override
	public boolean install(Item item) {
    	if (!(item instanceof ItemCPU)) return false;
    	if(installedItem==null) {
    		installedItem = item;
    		gameInterface.println(userId, MessageType.PRIVATE, "Computer isn't working. Need more item. To be continued...");
    		return true;
    	}
    	return false;
    }

    /* (non-Javadoc)
     * @see edu.cmu.tartan.properties.Hostable#uninstall(edu.cmu.tartan.item.Item)
     */
    @Override
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

    /* (non-Javadoc)
     * @see edu.cmu.tartan.properties.Hostable#installedItem()
     */
    @Override
	public Item installedItem() {
        return this.installedItem;
    }

}
