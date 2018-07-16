package edu.cmu.tartan;

import java.util.Vector;

import edu.cmu.tartan.action.Action;
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
        for(Item i : player.currentRoom().getItems()) {
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
        if(player.currentRoom().hasItem(item)) {
            if(item instanceof Holdable) {
                System.out.println("Taken.");

                player.currentRoom().remove(item);
                player.pickup(item);
                player.score( ((Holdable)item).value());
            }
            else {
                System.out.println("You cannot pick up this item.");
            }
        }
        else if((container = containerForItem(item)) != null) {

            System.out.println("Taken.");
            ((Hostable)container).uninstall(item);
            player.pickup(item);
            Holdable h = (Holdable) item;
            player.score(h.value());
        }
        else if(player.hasItem(item)) {
            System.out.println("You already have that item in your inventory.");
        }
        else {
            System.out.println("I don't see that here.");
        }    	
    }
    
    private void actionDestroy(Item item) {
        if (player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if (item instanceof Destroyable) {
                //System.out.println("Smashed.");
                ((Destroyable) item).setDestroyMessage("Smashed.");
                ((Destroyable)item).destroy();
                ((Destroyable) item).setDisappears(true);
                item.setDescription("broken " + item.toString());
                item.setDetailDescription("broken " + item.detailDescription());
                if (((Destroyable)item).disappears()) {
                    player.drop(item);
                    player.currentRoom().remove(item);
                    // Get points!
                    player.score(item.value());
                }

                if(item instanceof Hostable) {
                    player.currentRoom().putItem(((Hostable)item).installedItem());
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
    	if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
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
    	if(player.hasItem(item)) {
            if(item instanceof Holdable) {
                System.out.println("Dropped.");
                player.drop(item);
                System.out.println("You Dropped '" +item.description() + "' costing you "
                        + item.value() + " points.");
                player.currentRoom().putItem(item);
            }
            else {
                System.out.println("You cannot drop this item.");
            }
        }
        else {
            System.out.println("You don't have that item to drop.");
        }
        if(player.currentRoom() instanceof RoomRequiredItem) {
            RoomRequiredItem r = (RoomRequiredItem)player.currentRoom();
            r.playerDidDropRequiredItem();
        }
    }
    
    private void actionThrow(Item item) {
        if(player.hasItem(item)) {
            if(item instanceof Chuckable) {
                System.out.println("Thrown.");
                ((Chuckable)item).chuck();
                player.drop(item);
                player.currentRoom().putItem(item);
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
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Shakeable) {
                ((Shakeable)item).shake();
                if(((Shakeable)item).accident()) {
                    player.terminate();
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
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
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
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Pushable) {

                // Pushing the button is worth points
                Pushable p = (Pushable) item;
                p.push();
                player.score(item.value());

                if(item.relatedRoom() instanceof RoomElevator) { // player is next to an elevator
                    ((RoomElevator)item.relatedRoom()).call(player.currentRoom());
                }
                else if(player.currentRoom() instanceof RoomElevator) { // player is in an elevator
                    ((RoomElevator)player.currentRoom()).call(Integer.parseInt(item.getAliases()[0])-1);
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
    	if (player.currentRoom() instanceof RoomExcavatable && item.description() == "Shovel") {
            RoomExcavatable curr = (RoomExcavatable) player.currentRoom();
            curr.dig();
        } else {
            System.out.println("You are not allowed to dig here");
        }
    }
    
    private void actionEat(Item item) {
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Edible) {
                // eating something gives scores
                Edible e = (Edible)item;
                e.eat();
                player.score(item.value());
                // Once we eat it, then it's gone
                player.currentRoom().remove(item);
            }
            else {
                if(item instanceof Holdable) {
                    System.out.println("As you  shove the " + item + " down your throat, you begin to choke.");
                    player.terminate();
                }
                else {
                    System.out.println("That cannot be consumed.");
                }
            }
        }    	
    }
    
    private void actionOpen(Item item) {
        if(player.hasItem(item) || player.currentRoom().hasItem(item)) {
            if(item instanceof Openable) {
                Openable o = ((Openable)item);
                // if you can open the item , you score!
                if (o.open()) {
                    player.score(item.value());
                    player.currentRoom().remove(item);
                }
            }
            else {
                System.out.println("You cannot open ");
            }
        }
        else {
            System.out.println("I don't see that here.");
        }    	
    }
    
    private void actionExplode(Item item) {
        if(player.currentRoom().hasItem(item)) {
            if(item instanceof Explodable) {
                if(player.currentRoom().isAdjacentToRoom(item.relatedRoom())) {
                    Explodable explode = (Explodable)item;
                    explode.explode();
                    player.score(explode.value());
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
    
    private void actionPut(Item itemToPut, Item itemToBePutInto) {
    	
        if(!player.hasItem(itemToPut)) {
            System.out.println("You don't have that object in your inventory.");
        }
        else if(itemToBePutInto == null) {
            System.out.println("You must supply an indirect object.");
        }
        else if(!player.currentRoom().hasItem(itemToBePutInto)) {
            System.out.println("That object doesn't exist in this room.");
        }
        else if(itemToBePutInto instanceof ItemMagicBox && !(itemToPut instanceof Valuable)) {
            System.out.println("This item has no value--putting it in this " + itemToBePutInto + " will not score you any points.");
        }
        else if(!(itemToBePutInto instanceof Hostable) || !(itemToPut instanceof Installable)) {
            System.out.println("You cannot put a " + itemToPut + " into this " + itemToBePutInto);
        }
        else {
            System.out.println("Done.");
            player.drop(itemToPut);
            player.putItemInItem(itemToPut, itemToBePutInto);
        }
    }
    
    private void actionTake(Item contents, Item container) {
    	if(!player.currentRoom().hasItem(container)) {
            System.out.println("I don't see that here.");
        }
        else if(!(container instanceof Hostable)) {
            System.out.println("You can't have an item inside that.");
        }
        else {
            if(((Hostable)container).installedItem() == contents) {
                ((Hostable)container).uninstall(contents);
                player.pickup(contents);
                System.out.println("Taken.");
            }
            else {
                System.out.println("That item is not inside this " + container);
            }
        }
    }
    
    private void hasIndirectObject(Action action) {
    	Item directItem = action.directObject();
        Item indirectItem = action.indirectObject();
        
        switch(action) {
        	case ACTION_PUT:
        		actionPut(directItem, indirectItem);
        		break;
        	case ACTION_TAKE:
        		actionTake(directItem, indirectItem);
        		break;
        	default :
        		 System.out.println("There is not indirect object action");
        		 break;
        }    	
    }
    
    private void hasNoObject(Action action) {
    	switch(action) {
	        case ACTION_LOOK:
	            player.lookAround();
	            break;
	        case ACTION_CLIMB:
	            player.move(Action.ACTION_GO_UP);
	            break;
	        case ACTION_JUMP:
	            player.move(Action.ACTION_GO_DOWN);
	            break;
	        case ACTION_VIEW_ITEMS:
	            Vector<Item> items = player.getCollectedItems();
	            if (items.isEmpty()) {
	                System.out.println("You don't have any items.");
	            }
	            else {
	                for(Item item : player.getCollectedItems()) {
	                    System.out.println("You have a " + item.description() + ".");
	                }
	            }
	            break;
	        case ACTION_DIE:
	            player.terminate();
	            break;
	        default:
	        	System.out.println("There is not no object action");
	        	break;
    	}
    }
    
    private void unknownObject(Action action) {
        switch(action) {
	        case ACTION_PASS: 
	            // intentionally blank
	            break;	        
	        case ACTION_ERROR: 
	            System.out.println("I don't understand that.");
	            break;
	        case ACTION_UNKNOWN: 
	            System.out.println("I don't understand that.");
	            break;
	        default:
	        	System.out.println("It's unknown action");
	        	break;
        }    	
    }

    /**
     * Execute an action in the game. This method is where game play really occurs.
     * @param action The action to execute
     */
    public void executeAction(Action action) {

        switch(action.type()) {
            // Handle navigation
            case TYPE_DIRECTIONAL:
                player.move(action);
                break;
            // A direct item is an item that is required for an action. These
            // items can be picked up, eaten, pushed
            // destroyed, etc.
            case TYPE_HASDIRECTOBJECT:
            	hasDirectObject(action);
            	break;
            // Indirect objects are secondary objects that may be used by direct objects, such as a key for a lock
            case TYPE_HASINDIRECTOBJECT:
            	hasIndirectObject(action);
            	break;            	
            // Some actions do not require an object
            case TYPE_HASNOOBJECT:
            	hasNoObject(action);
                break;
            case TYPE_UNKNOWN: 
            	unknownObject(action);
                break;
            default:
                System.out.println("I don't understand that");
                break;
        }
    }

}
