package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Meltable;

/**
 * This class for food, which can be held and eaten.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemFood extends Item implements Edible, Holdable, Meltable, Installable {
	private static final long serialVersionUID = 1L;
	private Item hiddenItem = null;

    /**
     * Constructor for food item
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemFood(String s, String sd, String[] a, String userId) {

        super(s, sd, a, userId);
        setValue(3);
    }

    /**
     * Eat the food
     */
    @Override
    public void eat() {
    	gameInterface.println(userId, MessageType.PRIVATE, "Yummy");
    }

    /**
     * Set the item to reveal when the food is melted
     * @param item the item to melt
     */
    @Override
    public void setMeltItem(Item item) {
        hiddenItem = item;

    }

    /**
     * Reveal the hidden item
     * @return the hidden item
     */
    @Override
    public Item meltItem() {
        return hiddenItem;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((hiddenItem == null) ? 0 : hiddenItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!super.equals(obj)) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		ItemFood other = (ItemFood) obj;
		if (hiddenItem == null) {
			if (other.hiddenItem != null) {
				return false;
			}
		} else if (!hiddenItem.equals(other.hiddenItem)) {
			return false;	
		}
		return true;
	}
}
