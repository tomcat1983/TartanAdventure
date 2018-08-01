package edu.cmu.tartan;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemCPU;
import edu.cmu.tartan.item.ItemComputer;
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
				gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.TAKEN);

                player.currentRoom().remove(item);
                player.pickup(item);
                player.score( ((Holdable)item).value());
                return true;
            }
            else {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You cannot pick up this item.");
                return false;
            }
        }
        else if((container = containerForItem(item)) != null) {
        	gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.TAKEN);
            ((Hostable)container).uninstall(item);
            player.pickup(item);
            Holdable h = (Holdable) item;
            player.score(h.value());
            return true;
        }
        else if(player.hasItem(item)) {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You already have that item in your inventory.");
            return false;
        }
        else {
			gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
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
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You cannot break this item.");
                return false;
            }
        }
        else {
			gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }
    }
    
    private boolean actionInspect(@NonNull Item item) {
    	if((player.currentRoom().hasItem(item) || player.hasItem(item)) && item instanceof Inspectable) {
    		return item.inspect();
        }
        else {
			gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }
    }
    
    private boolean actionDrop(@NonNull Item item) throws TerminateGameException {
    	boolean result = true;
    	if(player.hasItem(item)) {
            if(item instanceof Holdable) {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "Dropped.");
                player.dropItem(item);
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You Dropped '" +item.description() + "' costing you "
                        + item.value() + " points.");
                result = true;
            }
            else {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You cannot drop this item.");
                result = false;
            }
        }
        else {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You don't have that item to drop.");
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
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "Thrown.");
                ((Chuckable)item).chuck();
                player.drop(item);
                player.currentRoom().putItem(item);
                return true;
            }
            else {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You cannot throw this item.");
                return false;
            }
        }
        else {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You don't have that item to throw.");
            return false;
        }    	
    }
    
    private boolean actionShake(@NonNull Item item) throws TerminateGameException {
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Shakeable) {
                ((Shakeable)item).shake();
                if(((Shakeable)item).accident()) {
                    player.terminate();
                }
                return true;
            }
            else {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "I don't know how to do that.");
                return false;
            }
        }
        else {
			gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }
    }
    
    private boolean actionEnable(@NonNull Item item) {
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Startable) {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "Done.");
                ((Startable)item).start();
                return true;
            }
            else {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "I don't know how to do that.");
                return false;
            }
        }
        else {
			gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
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
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "Nothing happens.");
                return false;
            }
        }
        else {
			gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }    	
    }
    
    private boolean actionDig(@NonNull Item item) {
    	if (player.currentRoom() instanceof RoomExcavatable && ("Shovel".equals(item.description()) || "shovel".equals(item.description()))) {
            RoomExcavatable curr = (RoomExcavatable) player.currentRoom();
            return curr.dig();
        } else {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You are not allowed to dig here");
            return false;
        }
    }
    
    private boolean actionEat(@NonNull Item item) throws TerminateGameException {
        if(player.currentRoom().hasItem(item) || player.hasItem(item)) {
            if(item instanceof Edible) {
                // eating something gives scores
                Edible e = (Edible)item;
                e.eat();
                player.score(item.value());
                // Once we eat it, then it's gone
                if(player.hasItem(item)) {	// bug fixed
                	player.drop(item);
                }
                player.currentRoom().remove(item);
                return true;
            }
            else {
                if(item instanceof Holdable) {
                    gameInterface.println(player.getUserName(), MessageType.PRIVATE, "As you  shove the " + item + " down your throat, you begin to choke.");
                    player.terminate();
                }
                else {
                    gameInterface.println(player.getUserName(), MessageType.PRIVATE, "That cannot be consumed.");
                }
                return false;
            }
        } else {
        	gameInterface.println(player.getUserName(), MessageType.PRIVATE, "I don't see that here.");
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
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You cannot open ");
                return false;
            }
        }
        else {
			gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }    	
    }
    
    private boolean actionExplode(@NonNull Item item) {
        if(player.currentRoom().hasItem(item)) {
            if(item instanceof Explodable) {
                if(player.currentRoom().isAdjacentToRoom(item.relatedRoom())) {
                    Explodable explode = (Explodable)item;
                    if(explode.explode()) {
                        player.score(explode.value());                    	
                    }
                    return true;
                }
                else {
                    gameInterface.println(player.getUserName(), MessageType.PRIVATE, "There isn't anything to blow up here.");
                    return false;
                }
            }
            else {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "That item is not an explosive.");
                return false;
            }
        }
        else {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "That item is not in the room");
            return false;
        }    	
    }
    
    private boolean hasDirectObject(@NonNull Action action, @NonNull ActionExecutionUnit actionExecutionUnit) throws TerminateGameException {
    	Item item = actionExecutionUnit.directObject();
        if(item==null) {
        	gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
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
            	gameInterface.println(player.getUserName(), MessageType.PRIVATE, "I don't know about " + action);
            	return false;
        }
    }
    
    private boolean actionPut(@NonNull Item itemToPut, Item itemToBePutInto) {
    	
        if(!player.hasItem(itemToPut)) {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You don't have that object in your inventory.");
            return false;
        }
        else if(itemToBePutInto == null) {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You must supply an indirect object.");
            return false;
        }
        else if(!player.currentRoom().hasItem(itemToBePutInto)) {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "That object doesn't exist in this room.");
            return false;
        }
        else if(!(itemToBePutInto instanceof Hostable) || !(itemToPut instanceof Installable)) {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You cannot put a " + itemToPut + " into this " + itemToBePutInto);
            return false;
        }
        else {
	        gameInterface.println(player.getUserName(), MessageType.PRIVATE, "Try to put...");
	        player.drop(itemToPut);
	        if(player.putItemInItem(itemToPut, itemToBePutInto)) {
		        if(itemToPut instanceof ItemCPU && itemToBePutInto instanceof ItemComputer) {
		        	player.score(itemToPut.value() + itemToBePutInto.value());
		        }
		        return true;
	        } else {
	        	player.grabItem(itemToPut);
	        }
        	gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You cann't put the " + itemToPut + ".");
        	return false;
        }
    }
    
    private boolean actionTake(@NonNull Item contents,@NonNull Item container) {
    	if(!player.currentRoom().hasItem(container)) {
			gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_SEE_THAT_HERE);
			return false;
        }
        else if(!(container instanceof Hostable)) {
            gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You can't have an item inside that.");
            return false;
        }
        else {
            if(((Hostable)container).installedItem() == contents) {
                ((Hostable)container).uninstall(contents);
                player.pickup(contents);
				gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.TAKEN);
				return true;
            }
            else {
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "That item is not inside this " + container);
                return false;
            }
        }
    }
    
    private boolean hasIndirectObject(@NonNull Action action,@NonNull ActionExecutionUnit actionExecutionUnit) {
    	Item directItem = actionExecutionUnit.directObject();
        Item indirectItem = actionExecutionUnit.indirectObject();
        
        switch(action) {
        	case ACTION_PUT:
        		return actionPut(directItem, indirectItem);
        	case ACTION_REMOVE:
        		return actionTake(directItem, indirectItem);
        	default :
        		 gameInterface.println(player.getUserName(), MessageType.PRIVATE, "There is not indirect object action");
        		 return false;
        }
    }
    
    private boolean hasNoObject(@NonNull Action action) throws TerminateGameException {
    	switch(action) {
	        case ACTION_LOOK:
	            return player.lookAround();
	        case ACTION_CLIMB:
	            return player.move(Action.ACTION_GO_UP);
	        case ACTION_JUMP:
	            return player.move(Action.ACTION_GO_DOWN);
	        case ACTION_VIEW_ITEMS:
	            List<Item> items = player.getCollectedItems();
	            if (items.isEmpty()) {
	                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You don't have any items.");
	            }
	            else {
	                for(Item item : player.getCollectedItems()) {
	                    gameInterface.println(player.getUserName(), MessageType.PRIVATE, "You have a " + item.description() + ".");
	                }
	            }
	            return true;
	        case ACTION_DIE:
	            player.terminate();
	            return true;
	        default:
	        	// unreachable
	        	gameInterface.println(player.getUserName(), MessageType.PRIVATE, "There is not no object action");
	        	return false;
    	}
    }
    
    private void unknownObject(Action action) {
        switch(action) {
	        case ACTION_PASS: 
	            // intentionally blank
	            break;	        
	        case ACTION_ERROR:
	            gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_UNDERSTAND);
	            break;
	        case ACTION_UNKNOWN:
	            gameInterface.println(player.getUserName(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_UNDERSTAND);
	            break;
	        default:
	        	gameInterface.println(player.getUserName(), MessageType.PRIVATE, "It's unknown action");
	        	break;
        }
    }

    /**
     * Execute an action in the game. This method is where game play really occurs.
     * @param action The action to execute
     * @return boolean if action handle to case return true, else return false. 
     * @throws TerminateGameException 
     */
    public boolean executeAction(@NonNull Action action, ActionExecutionUnit actionExecutionUnit) throws TerminateGameException {

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
            	unknownObject(action);
            	return false;
            default:
            	// unreachable
                gameInterface.println(player.getUserName(), MessageType.PRIVATE, "I don't understand that");
                return false;
        }
    }
}
