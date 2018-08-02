package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Pushable;

/**
 * A button can be pushed
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemButton extends Item implements Pushable {
	private static final long serialVersionUID = 1L;

	protected String pushMessage;

    /**
     * @param s
     * @param sd
     * @param a
     * @param userId
     */
    public ItemButton(String s, String sd, String[] a, String userId) {
        super(s, sd, a, userId);
        this.pushMessage = "Pushed.";
        setValue(2);
    }

    /**
     * Push the button
     */
    @Override
    public void push() {
    	gameInterface.println(userId, MessageType.PRIVATE, this.pushMessage);
    }

    /**
     * Set the message to display when the button is pushed
     * @param s the message
     */
    public void setPushMessage(String s) {
        this.pushMessage = s;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pushMessage == null) ? 0 : pushMessage.hashCode());
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
		ItemButton other = (ItemButton) obj;
		if (pushMessage == null) {
			if (other.pushMessage != null) {
				return false;
			}
		} else if (!pushMessage.equals(other.pushMessage)) {
			return false;
		}
		return true;
	}

}
