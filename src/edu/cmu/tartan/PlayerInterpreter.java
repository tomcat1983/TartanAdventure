package edu.cmu.tartan;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.item.Item;

import java.util.Arrays;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This class attempts to interpret player input in a flexible way. It is experimental at best!
 */
public class PlayerInterpreter {
	private static final int DIRECTIONAL_COMMAND_LENGTH = 2;
	private static final int INDIRECT_ACTIO_LENGTH = 4;
	
	private static final String INDIRECT_OP_PUT = "put";
	private static final String INDIRECT_OP_INSTALL = "install";
	private static final String INDIRECT_OP_REMOVE = "remove";
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

    /**
     * Interpret the input in terms of its action.
     * @param string input string.
     * @return an Action corresponding to the input.
     */
    public Action interpretString(String string, ActionExecutionUnit actionExecutionUnit) {
        if(string.equals("")) {
            return Action.ACTION_PASS;
        }
        return action(string.toLowerCase().split(" "), actionExecutionUnit);
    }
    
    private Action getDirectObject(Action action, String[] string, ActionExecutionUnit actionExecutionUnit) {
    	if(string.length > 1) {
            String d = string[1];
            // item is the direct object of the action
            actionExecutionUnit.setDirectObject(Item.getInstance(d, actionExecutionUnit.getUserId()));
            return action;
        }
        else {
        	gameInterface.println(actionExecutionUnit.getUserId(), MessageType.PRIVATE, "You must supply a direct object.");
            return Action.ACTION_PASS;
        }
    }
    
    private Action getIndirectObject(Action action, String[] string, ActionExecutionUnit actionExecutionUnit) {
        if(INDIRECT_ACTIO_LENGTH==string.length && (INDIRECT_OP_PUT.equals(string[0]) || INDIRECT_OP_INSTALL.equals(string[0]) || INDIRECT_OP_REMOVE.equals(string[0]))) {
            String d = string[1];
            Item item = Item.getInstance(d, actionExecutionUnit.getUserId());
            // item is the direct object of the action
            actionExecutionUnit.setDirectObject(item);
            if(string.length > 2) {
                String in = string[2];
                if(in.equals("in") || in.equals("from")) {
                    if(string.length > 3) {
                        String io = string[3];
                        Item indob = Item.getInstance(io, actionExecutionUnit.getUserId());
                        actionExecutionUnit.setIndirectObject(indob);
                        return action;
                    }
                    else {
                    	gameInterface.println(actionExecutionUnit.getUserId(), MessageType.PRIVATE, "You must supply an indirect object.");
                        return Action.ACTION_ERROR;
                    }
                }
                else {
                    return Action.ACTION_PASS;
                }
            }
            return Action.ACTION_ERROR;
        }
        else {
        	gameInterface.println(actionExecutionUnit.getUserId(), MessageType.PRIVATE, "You must supply a direct object.");
            return Action.ACTION_ERROR;
        }    	
    }
    
    private Action getDirectional(String[] string, ActionExecutionUnit actionExecutionUnit) {
		if(("go".equals(string[0]) || "travel".equals(string[0]) || "move".equals(string[0])) && string.length == DIRECTIONAL_COMMAND_LENGTH) {
            Action moveAction = stringCompareToGoActionAliases(string[1]);
            if(moveAction == null) {
            	gameInterface.println(actionExecutionUnit.getUserId(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_UNDERSTAND);
                return Action.ACTION_ERROR;
            }
            return moveAction;
        } 
		return Action.ACTION_ERROR;
    }
    
    private Action findAction(Action action, String[] string,  ActionExecutionUnit actionExecutionUnit) {
        switch(action.type()) {
        	case TYPE_DIRECTIONAL:
        		return getDirectional(string, actionExecutionUnit);
        	case TYPE_HASDIRECTOBJECT:
	        	return getDirectObject(action, string, actionExecutionUnit);
	        case TYPE_HASINDIRECTOBJECT:
	            // test if it has indirect object
	            // "Take Diamond from Microwave"
	        	return getIndirectObject(action, string, actionExecutionUnit);          
	        case TYPE_HASNOOBJECT:
	            return action;
	        case TYPE_UNKNOWN:
	            return Action.ACTION_ERROR;
	        default:
	        	gameInterface.println(actionExecutionUnit.getUserId(), MessageType.PRIVATE, "Unknown type");
	            break;
        }
        return Action.ACTION_PASS;
    }
    
    private Action stringCompareToActionAliases(String s) {
    	for( Action action : Action.values()) {
            for(String alias : action.getAliases()) {
                if(s.compareTo(alias) == 0) {
                    return action;
                }
            }
        }
    	return null;
    }
    
    private Action stringCompareToGoActionAliases(String s) {
    	for( Action action : Action.values()) {
    		if(action.type()==Type.TYPE_DIRECTIONAL) {
	            for(String alias : action.getAliases()) {
	                if(s.compareTo(alias) == 0) {
	                    return action;
	                }
	            }
    		}
        }
    	return null;
    }
    
    /**
     * Attempt to select the appropriate action for the given input string
     * @param string the description of what is to be done
     * @return Action
     */
    private Action action(String[] string, @NonNull ActionExecutionUnit actionExecutionUnit) {

        if(string == null || string.length == 0) {
        	gameInterface.println(actionExecutionUnit.getUserId(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_UNDERSTAND);
            return Action.ACTION_PASS;
        }
        else {
            // input could be northeast, put cpu in vax, throw shovel, examine bin
            String s = string[0];
            Action action = stringCompareToActionAliases(s);
            if(action == null) {
            	gameInterface.println(actionExecutionUnit.getUserId(), MessageType.PRIVATE, GamePlayMessage.I_DO_NOT_UNDERSTAND);
                return Action.ACTION_ERROR;
            }
            return findAction(action, string, actionExecutionUnit);
        }
    }
}