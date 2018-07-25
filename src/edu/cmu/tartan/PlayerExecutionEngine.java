package edu.cmu.tartan;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
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
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	private Player player;
	
	PlayerExecutionEngine(Player player) {
		this.player = player;
	}	
	
	/**
     * Determine if item in room
     * @param item the item to check
     * @return not null if the time is hosted in the room
     */
    private Item containerForItem(@NonNull Item item) {
        for(Item i : player.currentRoom().getItems()) {
            if (i instanceof Hostable && (item.equals(((Hostable)i).installedItem())) && item.isVisible()) {
                    return i;
            }
        }
        return null;
    }
	
    private boolean actionPickup(@NonNull Item item) {
        Item container = null;
        if(player.currentRoom().hasItem(item)) {
            if(item instanceof Holdable) {
				gameInterface.println(GamePlayMessage.TAKEN);

                player.currentRoom().remove(item);
                player.pickup(item);
                player.score( ((Holdable)item).value());
                return true;
            }
            else {
                gameInterface.println("You cannot pick up this item.");
                return false;
            }
        }
        else if((container = containerForItem(item)) != null) {
        	gameInterface.println(GamePlayMessage.TAKEN);
            ((Hostable)container).uninstall(item);
            player.pickup(item);
            Holdable h = (Holdable) item;
            player.score(h.value());
            return true;
        }
        else if(player.hasItem(item)) {
            gameInterface.println("You already have that item in your inventory.");
            return false;
        }
        else {
			gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }    	
    }
    
    private boolean actionDestroy(@NonNull Item item) {
        if (player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if (item instanceof Destroyable) {
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
                return true;
            }
            else {
                gameInterface.println("You cannot break this item.");
                return false;
            }
        }
        else {
			gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }
    }
    
    private boolean actionInspect(@NonNull Item item) {
    	if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Inspectable) {
                item.inspect();
                return true;
            }
            else {
            	// unreachabl, every item have a Inspectable.
                gameInterface.println("You cannot inspect this item.");
                return false;
            }
        }
        else {
			gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }
    }
    
    private boolean actionDrop(@NonNull Item item) {
    	boolean result = true;
    	if(player.hasItem(item)) {
            if(item instanceof Holdable) {
                gameInterface.println("Dropped.");
                player.drop(item);
                gameInterface.println("You Dropped '" +item.description() + "' costing you "
                        + item.value() + " points.");
                player.currentRoom().putItem(item);
                result = true;
            }
            else {
                gameInterface.println("You cannot drop this item.");
                result = false;
            }
        }
        else {
            gameInterface.println("You don't have that item to drop.");
            result = false;
        }
        if(player.currentRoom() instanceof RoomRequiredItem) {
            RoomRequiredItem r = (RoomRequiredItem)player.currentRoom();
            r.playerDidDropRequiredItem();
            result = true;
        }
        return result;
    }
    
    private boolean actionThrow(@NonNull Item item) {
        if(player.hasItem(item)) {
            if(item instanceof Chuckable) {
                gameInterface.println("Thrown.");
                ((Chuckable)item).chuck();
                player.drop(item);
                player.currentRoom().putItem(item);
                return true;
            }
            else {
                gameInterface.println("You cannot throw this item.");
                return false;
            }
        }
        else {
            gameInterface.println("You don't have that item to throw.");
            return false;
        }    	
    }
    
    private boolean actionShake(@NonNull Item item) {
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Shakeable) {
                ((Shakeable)item).shake();
                if(((Shakeable)item).accident()) {
                    player.terminate();
                }
                return true;
            }
            else {
                gameInterface.println("I don't know how to do that.");
                return false;
            }
        }
        else {
			gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }
    }
    
    private boolean actionEnable(@NonNull Item item) {
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Startable) {
                gameInterface.println("Done.");
                ((Startable)item).start();
                return true;
            }
            else {
                gameInterface.println("I don't know how to do that.");
                return false;
            }
        }
        else {
			gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }	
    }
    
    private boolean actionPush(@NonNull Item item) {
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Pushable) {

                // Pushing the button is worth points
                Pushable p = (Pushable) item;
                p.push();
                player.score(item.value());

                if(player.currentRoom() instanceof RoomElevator) { // player is in an elevator
                    ((RoomElevator)player.currentRoom()).call(Integer.parseInt(item.getAliases()[0])-1);
                } else if(item.relatedRoom() instanceof RoomElevator) { // player is next to an elevator
                    ((RoomElevator)item.relatedRoom()).call(player.currentRoom());
                }
                return true;
            }
            else {
                gameInterface.println("Nothing happens.");
                return false;
            }
        }
        else {
			gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }    	
    }
    
    private boolean actionDig(@NonNull Item item) {
    	if (player.currentRoom() instanceof RoomExcavatable && ("Shovel".equals(item.description()) || "shovel".equals(item.description()))) {
            RoomExcavatable curr = (RoomExcavatable) player.currentRoom();
            curr.dig();
            return true;
        } else {
            gameInterface.println("You are not allowed to dig here");
            return false;
        }
    }
    
    private boolean actionEat(@NonNull Item item) {
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Edible) {
                // eating something gives scores
                Edible e = (Edible)item;
                e.eat();
                player.score(item.value());
                // Once we eat it, then it's gone
                player.currentRoom().remove(item);
                return true;
            }
            else {
                if(item instanceof Holdable) {
                    gameInterface.println("As you  shove the " + item + " down your throat, you begin to choke.");
                    player.terminate();
                }
                else {
                    gameInterface.println("That cannot be consumed.");
                }
                return false;
            }
        }
        return false;
    }
    
    private boolean actionOpen(@NonNull Item item) {
        if(player.hasItem(item) || player.currentRoom().hasItem(item)) {
            if(item instanceof Openable) {
                Openable o = ((Openable)item);
                // if you can open the item , you score!
                if (o.open()) {
                    player.score(item.value());
                    player.currentRoom().remove(item);
                }
                return true;
            }
            else {
                gameInterface.println("You cannot open ");
                return false;
            }
        }
        else {
			gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }    	
    }
    
    private boolean actionExplode(@NonNull Item item) {
        if(player.currentRoom().hasItem(item)) {
            if(item instanceof Explodable) {
                if(player.currentRoom().isAdjacentToRoom(item.relatedRoom())) {
                    Explodable explode = (Explodable)item;
                    explode.explode();
                    player.score(explode.value());
                    return true;
                }
                else {
                    gameInterface.println("There isn't anything to blow up here.");
                    return false;
                }
            }
            else {
                gameInterface.println("That item is not an explosive.");
                return false;
            }
        }
        else {
            gameInterface.println("You do not have that item in your inventory.");
            return false;
        }    	
    }
    
    private boolean hasDirectObject(@NonNull Action action, @NonNull ActionExecutionUnit actionExecutionUnit) {
    	Item item = actionExecutionUnit.directObject();
        if(item==null) {
        	gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
        	return false;
        }
    	
        switch(action) {
            case ACTION_PICKUP:
            	return actionPickup(item);
            case ACTION_DESTROY: 
                return actionDestroy(item);
            case ACTION_INSPECT:
                return actionInspect(item);
            case ACTION_DROP:
            	return actionDrop(item);
            case ACTION_THROW:
            	return actionThrow(item);
            case ACTION_SHAKE:
            	return actionShake(item);
            case ACTION_ENABLE: 
            	return actionEnable(item);
            case ACTION_PUSH:
            	return actionPush(item);
            case ACTION_DIG:
            	return actionDig(item);
            case ACTION_EAT:
            	return actionEat(item);
            case ACTION_OPEN:
            	return actionOpen(item);
            case ACTION_EXPLODE: 
            	return actionExplode(item);
            default :
            	gameInterface.println("I don't know about " + action);
            	return false;
        }
    }
    
    private void actionPut(Item itemToPut, Item itemToBePutInto) {
    	
        if(!player.hasItem(itemToPut)) {
            gameInterface.println("You don't have that object in your inventory.");
        }
        else if(itemToBePutInto == null) {
            gameInterface.println("You must supply an indirect object.");
        }
        else if(!player.currentRoom().hasItem(itemToBePutInto)) {
            gameInterface.println("That object doesn't exist in this room.");
        }
        else if(itemToBePutInto instanceof ItemMagicBox && !(itemToPut instanceof Valuable)) {
            gameInterface.println("This item has no value--putting it in this " + itemToBePutInto + " will not score you any points.");
        }
        else if(!(itemToBePutInto instanceof Hostable) || !(itemToPut instanceof Installable)) {
            gameInterface.println("You cannot put a " + itemToPut + " into this " + itemToBePutInto);
        }
        else {
            gameInterface.println("Done.");
            player.drop(itemToPut);
            player.putItemInItem(itemToPut, itemToBePutInto);
        }
    }
    
    private void actionTake(Item contents, Item container) {
    	if(!player.currentRoom().hasItem(container)) {
			gameInterface.println(GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
        }
        else if(!(container instanceof Hostable)) {
            gameInterface.println("You can't have an item inside that.");
        }
        else {
            if(((Hostable)container).installedItem() == contents) {
                ((Hostable)container).uninstall(contents);
                player.pickup(contents);
				gameInterface.println(GamePlayMessage.TAKEN);
            }
            else {
                gameInterface.println("That item is not inside this " + container);
            }
        }
    }
    
    private boolean hasIndirectObject(Action action, ActionExecutionUnit actionExecutionUnit) {
    	Item directItem = actionExecutionUnit.directObject();
        Item indirectItem = actionExecutionUnit.indirectObject();
        
        switch(action) {
        	case ACTION_PUT:
        		actionPut(directItem, indirectItem);
        		break;
        	case ACTION_TAKE:
        		actionTake(directItem, indirectItem);
        		break;
        	default :
        		 gameInterface.println("There is not indirect object action");
        		 return false;
        }
        return true;
    }
    
    private boolean hasNoObject(Action action) {
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
	            List<Item> items = player.getCollectedItems();
	            if (items.isEmpty()) {
	                gameInterface.println("You don't have any items.");
	            }
	            else {
	                for(Item item : player.getCollectedItems()) {
	                    gameInterface.println("You have a " + item.description() + ".");
	                }
	            }
	            break;
	        case ACTION_DIE:
	            player.terminate();
	            break;
	        default:
	        	gameInterface.println("There is not no object action");
	        	return false;
    	}
    	return true;
    }
    
    private boolean unknownObject(Action action) {
        switch(action) {
	        case ACTION_PASS: 
	            // intentionally blank
	            break;	        
	        case ACTION_ERROR: 
	            gameInterface.println("I don't understand that.");
	            break;
	        case ACTION_UNKNOWN: 
	            gameInterface.println("I don't understand that.");
	            break;
	        default:
	        	gameInterface.println("It's unknown action");
	        	return false;
        }
        return true;
    }

    /**
     * Execute an action in the game. This method is where game play really occurs.
     * @param action The action to execute
     * @return boolean if action handle to case return true, else return false. 
     */
    public boolean executeAction(Action action, ActionExecutionUnit actionExecutionUnit) {

        switch(action.type()) {
            // Handle navigation
            case TYPE_DIRECTIONAL:
                return player.move(action);
            // A direct item is an item that is required for an action. These
            // items can be picked up, eaten, pushed
            // destroyed, etc.
            case TYPE_HASDIRECTOBJECT:
            	return hasDirectObject(action, actionExecutionUnit);
            // Indirect objects are secondary objects that may be used by direct objects, such as a key for a lock
            case TYPE_HASINDIRECTOBJECT:
            	return hasIndirectObject(action, actionExecutionUnit);            	
            // Some actions do not require an object
            case TYPE_HASNOOBJECT:
            	return hasNoObject(action);
            case TYPE_UNKNOWN: 
            	return unknownObject(action);
            default:
                gameInterface.println("I don't understand that");
                return false;
        }
    }
}
