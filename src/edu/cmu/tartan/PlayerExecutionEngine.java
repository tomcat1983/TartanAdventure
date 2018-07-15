package edu.cmu.tartan;

import java.util.Vector;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.Chuckable;
import edu.cmu.tartan.properties.Destroyable;
import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Explodable;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Inspectable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Openable;
import edu.cmu.tartan.properties.Pushable;
import edu.cmu.tartan.properties.Shakeable;
import edu.cmu.tartan.properties.Startable;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;

public class PlayerExecutionEngine {
	
	private Player player;
	
	PlayerExecutionEngine(Player player) {
		this.player = player;
	}	
	
	/**
     * Determine if item in room
     * @param item the item to check
     * @return not null if the time is hosted in the room
     */
    private Item containerForItem(Item item) {
        for(Item i : this.player.currentRoom().getItems()) {
            if (i instanceof Hostable) {
                if((item == ((Hostable)i).installedItem()) && item.isVisible()) {
                    return i;
                }
            }
        }
        return null;
    }
	
    private void actionPickup(Item item) {
        Item container = null;
        if(this.player.currentRoom().hasItem(item)) {
            if(item instanceof Holdable) {
                System.out.println("Taken.");

                this.player.currentRoom().remove(item);
                this.player.pickup(item);
                this.player.score( ((Holdable)item).value());
            }
            else {
                System.out.println("You cannot pick up this item.");
            }
        }
        else if((container = containerForItem(item)) != null) {

            System.out.println("Taken.");
            ((Hostable)container).uninstall(item);
            this.player.pickup(item);
            Holdable h = (Holdable) item;
            this.player.score(h.value());
        }
        else if(this.player.hasItem(item)) {
            System.out.println("You already have that item in your inventory.");
        }
        else {
            System.out.println("I don't see that here.");
        }    	
    }
    
    private void actionDestroy(Item item) {
        if (this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
            if (item instanceof Destroyable) {
                //System.out.println("Smashed.");
                ((Destroyable) item).setDestroyMessage("Smashed.");
                ((Destroyable)item).destroy();
                ((Destroyable) item).setDisappears(true);
                item.setDescription("broken " + item.toString());
                item.setDetailDescription("broken " + item.detailDescription());
                if (((Destroyable)item).disappears()) {
                    this.player.drop(item);
                    this.player.currentRoom().remove(item);
                    // Get points!
                    this.player.score(item.value());
                }

                if(item instanceof Hostable) {
                    this.player.currentRoom().putItem(((Hostable)item).installedItem());
                    ((Hostable)item).uninstall(((Hostable)item).installedItem());
                }
            }
            else {
                System.out.println("You cannot break this item.");
            }
        }
        else {
            System.out.println("I don't see that here.");
        }
    }
    
    private void actionInspect(Item item) {
    	if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
            if(item instanceof Inspectable) {
                item.inspect();
            }
            else {
                System.out.println("You cannot inspect this item.");
            }
        }
        else {
            System.out.println("I don't see that here.");
        }
    }
    
    private void actionDrop(Item item) {
    	if(this.player.hasItem(item)) {
            if(item instanceof Holdable) {
                System.out.println("Dropped.");
                this.player.drop(item);
                System.out.println("You Dropped '" +item.description() + "' costing you "
                        + item.value() + " points.");
                this.player.currentRoom().putItem(item);
            }
            else {
                System.out.println("You cannot drop this item.");
            }
        }
        else {
            System.out.println("You don't have that item to drop.");
        }
        if(this.player.currentRoom() instanceof RoomRequiredItem) {
            RoomRequiredItem r = (RoomRequiredItem)this.player.currentRoom();
            r.playerDidDropRequiredItem();
        }
    }
    
    private void actionThrow(Item item) {
        if(this.player.hasItem(item)) {
            if(item instanceof Chuckable) {
                System.out.println("Thrown.");
                ((Chuckable)item).chuck();
                this.player.drop(item);
                this.player.currentRoom().putItem(item);
            }
            else {
                System.out.println("You cannot throw this item.");
            }
        }
        else {
            System.out.println("You don't have that item to throw.");
        }    	
    }
    
    private void actionShake(Item item) {
        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
            if(item instanceof Shakeable) {
                ((Shakeable)item).shake();
                if(((Shakeable)item).accident()) {
                    this.player.terminate();
                }
            }
            else {
                System.out.println("I don't know how to do that.");
            }
        }
        else {
            System.out.println("I don't see that here.");
        }
    }
    
    private void actionEnable(Item item) {
        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
            if(item instanceof Startable) {
                System.out.println("Done.");
                ((Startable)item).start();
            }
            else {
                System.out.println("I don't know how to do that.");
            }
        }
        else {
            System.out.println("I don't see that here.");
        }	
    }
    
    private void actionPush(Item item) {
        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
            if(item instanceof Pushable) {

                // Pushing the button is worth points
                Pushable p = (Pushable) item;
                p.push();
                this.player.score(item.value());

                if(item.relatedRoom() instanceof RoomElevator) { // player is next to an elevator
                    ((RoomElevator)item.relatedRoom()).call(this.player.currentRoom());
                }
                else if(this.player.currentRoom() instanceof RoomElevator) { // player is in an elevator
                    ((RoomElevator)this.player.currentRoom()).call(Integer.parseInt(item.getAliases()[0])-1);
                }
            }
            else {
                System.out.println("Nothing happens.");
            }
        }
        else {
            System.out.println("I don't see that here.");
        }    	
    }
    
    private void actionDig(Item item) {
    	if (this.player.currentRoom() instanceof RoomExcavatable && item.description() == "Shovel") {
            RoomExcavatable curr = (RoomExcavatable) this.player.currentRoom();
            curr.dig();
        } else {
            System.out.println("You are not allowed to dig here");
        }
    }
    
    private void actionEat(Item item) {
        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
            if(item instanceof Edible) {
                // eating something gives scores
                Edible e = (Edible)item;
                e.eat();
                player.score(item.value());
                // Once we eat it, then it's gone
                this.player.currentRoom().remove(item);
            }
            else {
                if(item instanceof Holdable) {
                    System.out.println("As you  shove the " + item + " down your throat, you begin to choke.");
                    this.player.terminate();
                }
                else {
                    System.out.println("That cannot be consumed.");
                }
            }
        }    	
    }
    
    private void actionOpen(Item item) {
        if(this.player.hasItem(item) || this.player.currentRoom().hasItem(item)) {
            if(item instanceof Openable) {
                Openable o = ((Openable)item);
                // if you can open the item , you score!
                if (o.open()) {
                    player.score(item.value());
                    this.player.currentRoom().remove(item);
                }
            }
            else {
                System.out.println("You cannot open this.");
            }
        }
        else {
            System.out.println("I don't see that here.");
        }    	
    }
    
    private void actionExplode(Item item) {
        if(this.player.currentRoom().hasItem(item)) {
            if(item instanceof Explodable) {
                if(this.player.currentRoom().isAdjacentToRoom(item.relatedRoom())) {
                    Explodable explode = (Explodable)item;
                    explode.explode();
                    this.player.score(explode.value());
                }
                else {
                    System.out.println("There isn't anything to blow up here.");
                }
            }
            else {
                System.out.println("That item is not an explosive.");
            }
        }
        else {
            System.out.println("You do not have that item in your inventory.");
        }    	
    }
    
    private void hasDirectObject(Action action) {
    	Item item = action.directObject();
    	
        switch(action) {
            case ACTION_PICKUP: 
                if(item!=null) {
                	// Is it right code? If object is null, something process below funciton? 
                	actionPickup(item);
                } else {
                    System.out.println("I don't see that here.");
                }
                break;
            case ACTION_DESTROY: 
                actionDestroy(item);
                break;
            case ACTION_INSPECT:
                actionInspect(item);
                break;
            case ACTION_DROP:
            	actionDrop(item);
                break;
            case ACTION_THROW:
            	actionThrow(item);
                break;
            case ACTION_SHAKE:
            	actionShake(item);
                break;
            case ACTION_ENABLE: 
            	actionEnable(item);
                break;
            case ACTION_PUSH:
            	actionPush(item);
                break;
            case ACTION_DIG:
            	actionDig(item);
                break;
            case ACTION_EAT:
            	actionEat(item);
                break;
            case ACTION_OPEN:
            	actionOpen(item);
                break;
            case ACTION_EXPLODE: 
            	actionExplode(item);
                break;
            default :
            	System.out.println("I don't know about " + action);
            	break;
        }
    }

    /**
     * Execute an action in the game. This method is where game play really occurs.
     * @param a The action to execute
     */
    public void executeAction(Action a) {

        switch(a.type()) {
            // Handle navigation
            case TYPE_DIRECTIONAL:
                player.move(a);
                break;
            // A direct item is an item that is required for an action. These
            // items can be picked up, eaten, pushed
            // destroyed, etc.
            case TYPE_HASDIRECTOBJECT:
            	  hasDirectObject(a);
            	  break;
            // Indirect objects are secondary objects that may be used by direct objects, such as a key for a lock
            case TYPE_HASINDIRECTOBJECT:
                switch(a) {
                    case ACTION_PUT: {
                        Item itemToPut = a.directObject();
                        Item itemToBePutInto = a.indirectObject();
                        if(!this.player.hasItem(itemToPut)) {
                            System.out.println("You don't have that object in your inventory.");
                            break;
                        }
                        else if(itemToBePutInto == null) {
                            System.out.println("You must supply an indirect object.");
                            break;
                        }
                        else if(!this.player.currentRoom().hasItem(itemToBePutInto)) {
                            System.out.println("That object doesn't exist in this room.");
                            break;
                        }
                        else if(itemToBePutInto instanceof ItemMagicBox && !(itemToPut instanceof Valuable)) {
                            System.out.println("This item has no value--putting it in this " + itemToBePutInto + " will not score you any points.");
                        }
                        else if(!(itemToBePutInto instanceof Hostable) || !(itemToPut instanceof Installable)) {
                            System.out.println("You cannot put a " + itemToPut + " into this " + itemToBePutInto);
                        }
                        else {
                            System.out.println("Done.");
                            this.player.drop(itemToPut);
                            this.player.putItemInItem(itemToPut, itemToBePutInto);
                        }
                        break;
                    }
                    case ACTION_TAKE: {
                        Item contents = a.directObject();
                        Item container = a.indirectObject();
                        if(!this.player.currentRoom().hasItem(container)) {
                            System.out.println("I don't see that here.");
                        }
                        else if(!(container instanceof Hostable)) {
                            System.out.println("You can't have an item inside that.");
                        }
                        else {
                            if(((Hostable)container).installedItem() == contents) {
                                ((Hostable)container).uninstall(contents);
                                this.player.pickup(contents);
                                System.out.println("Taken.");
                            }
                            else {
                                System.out.println("That item is not inside this " + container);
                            }
                        }
                        break;
                    }
                }
            // Some actions do not require an object
            case TYPE_HASNOOBJECT: {
                switch(a) {
                    case ACTION_LOOK:
                        this.player.lookAround();
                        break;
                    case ACTION_CLIMB:
                        player.move(Action.ACTION_GO_UP);
                        break;
                    case ACTION_JUMP:
                        player.move(Action.ACTION_GO_DOWN);
                        break;
                    case ACTION_VIEW_ITEMS:
                        Vector<Item> items = this.player.getCollectedItems();
                        if (items.isEmpty()) {
                            System.out.println("You don't have any items.");
                        }
                        else {
                            for(Item item : this.player.getCollectedItems()) {
                                System.out.println("You have a " + item.description() + ".");
                            }
                        }
                        break;
                    case ACTION_DIE:
                        this.player.terminate();
                        break;
                }
                break;
            }
            case TYPE_UNKNOWN: {
                switch(a) {
                    case ACTION_PASS: {
                        // intentionally blank
                        break;
                    }
                    case ACTION_ERROR: {
                        System.out.println("I don't understand that.");
                        break;
                    }
                    case ACTION_UNKNOWN: {
                        System.out.println("I don't understand that.");
                        break;
                    }
                }
                break;
            }
            default:
                System.out.println("I don't understand that");
                break;
        }
    }

}
