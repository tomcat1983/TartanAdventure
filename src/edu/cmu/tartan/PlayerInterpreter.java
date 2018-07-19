package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.item.Item;

import java.util.Arrays;

/**
 * This class attempts to interpret player input in a flexible way. It is experimental at best!
 */
public class PlayerInterpreter {
	
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
            actionExecutionUnit.setDirectObject(Item.getInstance(d));
            return action;
        }
        else {
        	gameInterface.println("You must supply a direct object.");
            return Action.ACTION_PASS;
        }
    }
    
    private Action getIndirectObject(Action action, String[] string, ActionExecutionUnit actionExecutionUnit) {
        if(string.length > 0) {
            String d = string[1];
            Item item = Item.getInstance(d);
            // item is the direct object of the action
            actionExecutionUnit.setDirectObject(item);
            if(string.length > 2) {
                String in = string[2];
                if(in.equals("in") || in.equals("from")) {
                    if(string.length > 3) {
                        String io = string[3];
                        Item indob = Item.getInstance(io);
                        actionExecutionUnit.setIndirectObject(indob);
                        return action;
                    }
                    else {
                    	gameInterface.println("You must supply an indirect object.");
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
        	gameInterface.println("You must supply a direct object.");
            return Action.ACTION_ERROR;
        }    	
    }
    
    private Action findAction(Action action, String[] string,  ActionExecutionUnit actionExecutionUnit) {
        switch(action.type()) {
        	case TYPE_DIRECTIONAL:
        		return action;
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
	        	gameInterface.println("Unknown type");
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
    /**
     * Attempt to select the appropriate action for the given input string
     * @param string the description of what is to be done
     * @return
     */
    private Action action(String[] string, ActionExecutionUnit actionExecutionUnit) {

        if(string == null || string.length == 0) {
            return Action.ACTION_PASS;
        }
        if(string[0].compareTo("go") == 0 || string[0].compareTo("travel") == 0 || string[0].compareTo("move") == 0){
            String[] command = Arrays.copyOfRange(string, 1, string.length);
            return action(command, actionExecutionUnit);
        }
        else {
            // input could be northeast, put cpu in vax, throw shovel, examine bin

            String s = string[0];
            Action action = stringCompareToActionAliases(s);
            if(action == null) {
                return Action.ACTION_ERROR;
            }
            return findAction(action, string, actionExecutionUnit);
        }
    }
}
