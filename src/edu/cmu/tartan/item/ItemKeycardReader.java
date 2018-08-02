package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Hostable;

/**
 * This class for a keycard reader, which can hold a keycard.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemKeycardReader extends Item implements Hostable {
	private static final long serialVersionUID = 1L;
	private Item installedItem;
    private String installMessage;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemKeycardReader(String s, String sd, String[] a, String userId) {
        super(s, sd, a, userId);
        this.installMessage = null;
        setValue(25);
    }

    /**
     * Install a keycard
     * @param item the keycard to install in the reader
     */
    @Override
	public boolean install(Item item) {
        if (!(item instanceof ItemKeycard)) return false;
        installedItem = item;

        for (int i = 0; i < 3; i++) {
            gameInterface.println(userId, MessageType.PRIVATE,"...");
            try {
                Thread.sleep(100);
            } catch (Exception e1) {
            	gameLogger.severe(e1.getMessage());
            	return false;
            }
        }
        if (installMessage != null) {
        	gameInterface.println(userId, MessageType.PRIVATE, installMessage);
        }
        relatedItem.setVisible(true);
        return true;
    }

    /**
     * Unstall the keycard
     * @param i the keycard to uninstall
     * @return true if uninstalled; false otherwise
     */
    @Override
    public boolean uninstall(Item i) {
        if (!(i instanceof ItemKeycard)) return false;

        if (this.installedItem == null) {
            return false;
        } else if (this.installedItem == i) {
            this.installedItem = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fetch the installed keycard
     * @return
     */
    @Override
	public Item installedItem() {
        return this.installedItem;
    }

    /**
     * Set the install message
     * @param s the message
     */
    public void setInstallMessage(String s) {
        this.installMessage = s;
    }

    /**
     * @return
     */
    public String getInstallMessage() {
        return installMessage;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((installMessage == null) ? 0 : installMessage.hashCode());
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
		ItemKeycardReader other = (ItemKeycardReader) obj;
		if (installMessage == null) {
			if (other.getInstallMessage() != null) {
				return false;
			}
		} else if (!installMessage.equals(other.getInstallMessage())) {
			return false;
		}

		return true;
	}
}