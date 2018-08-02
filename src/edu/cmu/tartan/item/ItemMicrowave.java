package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Meltable;
import edu.cmu.tartan.properties.Startable;

/**
 * This class for a microwave, which can hold something and be started.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemMicrowave extends Item implements Hostable, Startable {
	private static final long serialVersionUID = 1L;

	public static final int MIC_OP_TIME = 100;

	private Item installedItem;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemMicrowave(String s, String sd, String[] a, String userId) {
        super(s, sd, a, userId);
        this.installedItem = null;
        setValue(5);
    }

    /**
     * Start the microwave. If the installed item is meltable, then melt it
     * @return true if started
     */
    @Override
    public Boolean start() {

        for (int i = 0; i < 3; i++) {
            gameInterface.println(userId, MessageType.PRIVATE, "...");
            try {
                Thread.sleep(MIC_OP_TIME);
            } catch (Exception e1) {
            	gameLogger.severe(e1.getMessage());
            }
        }
        gameInterface.println(userId, MessageType.PRIVATE, "Beep beep beep");

        // Only meltable things can be microwaved
        if (installedItem instanceof Meltable) {
            Item item = ((Meltable) installedItem).meltItem();
            if(item!=null) {
	            gameInterface.println(userId, MessageType.PRIVATE, "You melted the " + this.installedItem.detailDescription() + ", and it revealed a " + item.detailDescription() + "!");
	            this.installedItem = item;
	            return true;
            }
        }
        return false;
    }

    /**
     * Install an item in the microwave
     * @param i the item to install
     */
    @Override
	public boolean install(Item i) {
    	if(installedItem==null) {
    		installedItem = i;
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

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((installedItem == null) ? 0 : installedItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null || getClass() != obj.getClass() || !super.equals(obj)) {
			return false;
		} else if (this == obj) {
			return true;
		}

		ItemMicrowave other = (ItemMicrowave) obj;
		if (installedItem == null) {
			if (other.installedItem != null) {
				return false;
			}
		} else if (!installedItem.equals(other.installedItem)) {
			return false;
		}
		return true;
	}

}
