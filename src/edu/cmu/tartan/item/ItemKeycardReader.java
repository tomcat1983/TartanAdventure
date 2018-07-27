package edu.cmu.tartan.item;

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
	private Item installedItem;
    private String installMessage;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemKeycardReader(String s, String sd, String[] a) {
        super(s, sd, a);
        this.installMessage = null;
        setValue(25);
    }

    /**
     * Install a keycard
     * @param item the keycard to install in the reader
     */
    public void install(Item item) {
        if (!(item instanceof ItemKeycard)) return;
        installedItem = item;

        for (int i = 0; i < 3; i++) {
            gameInterface.println("...");
            try {
                Thread.sleep(1000);
            } catch (Exception e1) {
            	gameInterface.severe(e1.getMessage());
            }
        }
        if (installMessage != null) {
        	gameInterface.println(installMessage);
        }
        relatedItem.setVisible(true);
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
			if (other.installMessage != null) {
				return false;
			}
		} else if (!installMessage.equals(other.installMessage)) {
			return false;
		}
		return true;
	}
}
