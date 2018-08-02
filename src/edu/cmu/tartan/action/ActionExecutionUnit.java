package edu.cmu.tartan.action;

import edu.cmu.tartan.item.Item;

public class ActionExecutionUnit {
    private Item directObject;
    private Item indirectObject;
    private String userId;

    /**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param directObject
	 * @param indirectObject
	 */
	public ActionExecutionUnit(Item directObject, Item indirectObject) {
    	this.directObject = directObject;
    	this.indirectObject = indirectObject;
    }

    /**
     * @param directObject
     */
    public void setDirectObject(Item directObject) {
        this.directObject = directObject;
    }

    /**
     * @return
     */
    public Item directObject() {
        return this.directObject;
    }

    /**
     * @param indirectObject
     */
    public void setIndirectObject(Item indirectObject) {
        this.indirectObject = indirectObject;
    }

    /**
     * @return
     */
    public Item indirectObject() {
        return this.indirectObject;
    }
}
