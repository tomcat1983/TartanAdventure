package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Shakeable;

/**
 * This class for a keycard, which can be shaken.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemVendingMachine extends Item implements Shakeable {
	private static final long serialVersionUID = 1L;
	protected int count;
	/**
     * Constructor
     * @param d description
     * @param sd long description
     * @param a aliases
     */
    public ItemVendingMachine(String d, String sd, String[] a, String userId) {
        super(d, sd, a, userId);
        this.count = 0;
        setValue(15);
    }

    /**
     * Shaking this machine too much can cause an accident
     * @return true if an accident can occur; false otherwise
     */
    @Override
	public boolean accident() {
        return this.count > 2;
    }

    /* (non-Javadoc)
     * @see edu.cmu.tartan.properties.Shakeable#shake()
     */
    @Override
	public void shake() {
        switch (this.count) {
            case 0:
                gameInterface.println(userId, MessageType.PRIVATE, "You shake the vending machine, and your favorite treat inches its way off the tray.");
                break;
            case 1:
            	gameInterface.println(userId, MessageType.PRIVATE, "The treat begins to bend toward the will of gravity.");
                break;
            case 2:
            	gameInterface.println(userId, MessageType.PRIVATE, "Just as the candy falls, the machine also falls over and crushes you.");
                break;
            default:
                break;
        }
        this.count++;
    }

    /**
     * @param count
     */
    public void setCount(int count) {
    	this.count = count;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + count;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj!=null && this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ItemVendingMachine other = (ItemVendingMachine) obj;
		return count == other.count;
	}
}
