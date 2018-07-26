package edu.cmu.tartan.action;

import edu.cmu.tartan.item.Item;

public class ActionExecutionUnit {
    private Item directObject;
    private Item indirectObject;

    public ActionExecutionUnit(Item directObject, Item indirectObject) {
    	this.directObject = directObject;
    	this.indirectObject = indirectObject;
    }
    
    public void setDirectObject(Item directObject) {
        this.directObject = directObject;
    }

    public Item directObject() {
        return this.directObject;
    }

    public void setIndirectObject(Item indirectObject) {
        this.indirectObject = indirectObject;
    }

    public Item indirectObject() {
        return this.indirectObject;
    }
}
