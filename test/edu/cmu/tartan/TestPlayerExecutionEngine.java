package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemKey;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.TestRoom;

class TestPlayerExecutionEngine {

	@Test
	void testWhenCalledexecuteActionWithDirectObject() {
		PlayerInterpreter interpreter = new PlayerInterpreter();
		Room room1 = new Room(TestRoom.FORK_ROOM_DESCRIPTION, TestRoom.FORK);
		Player player = new Player(room1);
		PlayerExecutionEngine playerExecutionEngine = new PlayerExecutionEngine(player);
		ActionExecutionUnit actionExecutionUnit = new ActionExecutionUnit(null, null);
    	Action action = interpreter.interpretString("pickup key", actionExecutionUnit);
    	ItemKey keyItem = (ItemKey) Item.getInstance("key");
    	room1.putItem(keyItem);
    	
    	assertTrue(playerExecutionEngine.executeAction(action, actionExecutionUnit));
	}

}
