package edu.cmu.tartan.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.games.CustomizingGame;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemLock;
import edu.cmu.tartan.properties.Meltable;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomDark;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomLockable;
import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.room.RoomRequiredItem;

public class XmlResponseUploadMap extends XmlResponse {

	XmlParseResult xmlParseResult; 
	CustomizingGame customizingGame; 
	
	XmlResponseUploadMap() {
		xmlParseResult = XmlParseResult.SUCCESS; 
		customizingGame = new CustomizingGame(); 
	}
	
	
	@Override
	public void makeResponseXmlString() {
		
		XmlWriter xmlWriter; 

		try {
			
			xmlWriter = new XmlWriter(); 
			xmlWriter.startWritingXml(msgType, "server", "client");
			xmlWriter.addChildElement("game_info");
			
			if(xmlParseResult.equals( XmlParseResult.SUCCESS )) {
				xmlWriter.setAttributeToElement("result", "OK");
				xmlWriter.setAttributeToElement("ng_reason", "-");
			
				XmlWriter.overWriteFile("gameMap.xml", "userMap.xml");
			
			}
			else {
				xmlWriter.setAttributeToElement("result", "NG");
				xmlWriter.setAttributeToElement("ng_reason", xmlParseResult.name());
			}

			responseXml = xmlWriter.convertDocumentToString();
			
		} catch (ParserConfigurationException e) {
			gameInterface.severe("ParserConfigurationException");
		} 
		catch (IOException e) {
			gameInterface.severe("IOException occur when copying userMap.xml to gameMap.xml");
		} 
		
		gameInterface.info(responseXml);
	}
	
	@Override
	public XmlParseResult doYourJob(Document doc) {
		
		xmlParseResult = parsingRoomInfo(doc);

		if(xmlParseResult.equals(XmlParseResult.SUCCESS))
			xmlParseResult = parsingGoalInfo(doc);
		
		makeResponseXmlString(); 
		
		//For save map to server PC
		String mapXml = XmlWriter.convertDocumentToString(doc);
		XmlWriter.saveXmlStringToFile("userMap.xml", mapXml);
		
		return xmlParseResult;
	}
	
	
	public GameConfiguration doYourJobReturnGameConfiguration(Document doc) {
		
		XmlParseResult result = doYourJob(doc);
		if(result.equals(XmlParseResult.SUCCESS))
			return customizingGame; 
		else
			return null;
	}
	
	public XmlParseResult parsingRoomInfo(Document doc) {
		
		int i;
		int nodeLength;
		int roomCnt;
		NodeList nList;
		XmlParseResult result = XmlParseResult.SUCCESS; 
		
		nList = getNodeListOfGivenTag("room_info", doc);
		roomCnt = Integer.parseInt(getAttributeValueAtNthTag("room_cnt", nList, 0));	//room_cnt should be unique. 
	
		nList = getNodeListOfGivenTag("room", doc);
		nodeLength = nList.getLength();		//number of tag

		if(nodeLength != roomCnt) {
			return XmlParseResult.INVALID_DATA;
		}
		
		//create room and put item first. 
		for(i=0; i<roomCnt; i++) {
			
			String roomIndex = getAttributeValueAtNthTag("index", nList, i);
			String roomType = getAttributeValueAtNthTag("type", nList, i);
			String itemList = getAttributeValueAtNthTag("item_list", nList, i);

			//roomIndex and roomType should not null. but itemList could be. 
			if(roomIndex == null || roomType == null) {
				return XmlParseResult.INVALID_DATA;
			}
			
			Room room = makeRoom(roomType, roomIndex); 
			if(room == null) {
				return XmlParseResult.INVALID_DATA;
			}
			
			if(itemList != null)
				putItemListIntoRoom(room, itemList);
			
			customizingGame.addRoom(room);
		}
		
		
		//setup path and make relationship for room&item 
		for(i=0; i<roomCnt; i++) {

			result = setAdjacentPath(nList, i);
			if( !result.equals(XmlParseResult.SUCCESS))
				return result; 
			
			result = setRelationship(nList, i);
		}

		return result;
	}
	
	
	//type="lockable" lock_item="lock:2" key_item="key:3:3"
	public XmlParseResult setRelationshipForRoomLockable(RoomLockable currentRoom, NodeList nList, int roomIndex) {

		String keyItemStr = getAttributeValueAtNthTag("key_item", nList, roomIndex);
		String lockItemStr = getAttributeValueAtNthTag("lock_item", nList, roomIndex);

		if(keyItemStr == null || lockItemStr == null)
			return XmlParseResult.INVALID_DATA; 
		
		//key_item="itemName:roomIndex:itemCnt" 
		String[] splited = keyItemStr.split(":");
		String keyName = splited[0];
		int keyLocationIndex = Integer.parseInt(splited[1]);
		int keyCnt = Integer.parseInt(splited[2]);
		
		//lock_item="itemName:roomIndex" 
		splited = lockItemStr.split(":");
		String lockName = splited[0];
		int lockLocationIndex = Integer.parseInt(splited[1]);
		
		Room keyLocatedRoom = customizingGame.getRoomIndex(keyLocationIndex);
		Room lockLocatedRoom = customizingGame.getRoomIndex(lockLocationIndex);

		Item lock = Item.getInstance(lockName);
        Item key = Item.getInstance(keyName);
		((ItemLock) lock).install(key);
		lock.setRelatedRoom(currentRoom);
		lockLocatedRoom.putItem(lock);
		for(int i=0; i<keyCnt; i++)
			keyLocatedRoom.putItem(key);
		
		currentRoom.setKey(key);
		
		return XmlParseResult.SUCCESS;
	}
	
	//type="require" require_item="gold:11:2"/>
	public XmlParseResult setRelationshipForRoomRequiredItem(RoomRequiredItem currentRoom, NodeList nList, int roomIndex) {

		String requireItemStr = getAttributeValueAtNthTag("require_item", nList, roomIndex);

		if(requireItemStr == null)
			return XmlParseResult.INVALID_DATA; 

		//require_item="itemName:roomIndex:itemCnt" 
		String[] splited = requireItemStr.split(":");
		String itemName = splited[0];
		int itemLocationIndex = Integer.parseInt(splited[1]);
		int itemCnt = Integer.parseInt(splited[2]);

		Room itemLocatedRoom = customizingGame.getRoomIndex(itemLocationIndex);
		Item requireItem = Item.getInstance(itemName);
		currentRoom.setRequiredItem(requireItem);

		for(int i=0; i<itemCnt; i++)
			itemLocatedRoom.putItem(requireItem);

		return XmlParseResult.SUCCESS;
	}


	//type="obscured" obstacle="fridge:9" />
	public XmlParseResult setRelationshipForRoomObscured(RoomObscured currentRoom, NodeList nList, int roomIndex) {

		String pbstacleStr = getAttributeValueAtNthTag("require_item", nList, roomIndex);

		if(pbstacleStr == null)
			return XmlParseResult.INVALID_DATA; 

		//obstacle="itemName:roomIndex" 
		String[] splited = pbstacleStr.split(":");
		String itemName = splited[0];
		int itemLocationIndex = Integer.parseInt(splited[1]);

		Room itemLocatedRoom = customizingGame.getRoomIndex(itemLocationIndex);
		Item obstacle = Item.getInstance(itemName);
		currentRoom.setObscuringItem(obstacle);
		currentRoom.setObscured(true);
		currentRoom.setUnobscureMessage("You've revelealed a hidden room to direction(TODO)");
		currentRoom.setObscureMessage("ObscureMessage(TODO)");
		obstacle.setRelatedRoom(currentRoom);
		itemLocatedRoom.putItem(obstacle);		
		
		return XmlParseResult.SUCCESS;
	}

	
	public XmlParseResult setRelationship(NodeList nList, int roomIndex) {
		
		Room currentRoom = customizingGame.getRoomIndex(roomIndex);
		XmlParseResult result = XmlParseResult.SUCCESS; 
		
		if(currentRoom instanceof RoomLockable) {
			result = setRelationshipForRoomLockable((RoomLockable)currentRoom, nList, roomIndex);
		}
		else if (currentRoom instanceof RoomRequiredItem ) {
			result = setRelationshipForRoomRequiredItem((RoomRequiredItem)currentRoom, nList, roomIndex);
		}
		else if(currentRoom instanceof RoomObscured) {
			result = setRelationshipForRoomObscured((RoomObscured)currentRoom, nList, roomIndex);
		}
		
		if( result.equals(XmlParseResult.SUCCESS)) {
			String hiddenItemStr= getAttributeValueAtNthTag("hidden_item", nList, roomIndex);
			//for hiddenItem.  item_list="food:2" hidden_item="diamond"
			if( hiddenItemStr != null )
				setHiddenItem(currentRoom, hiddenItemStr);
		}
		
		return result;
	}
	
	public void setHiddenItem(Room currentRoom, String hiddenItemStr) {
		
		Item hiddenItem = Item.getInstance(hiddenItemStr);
		
		if(hiddenItem != null) {
			List<Item> items = currentRoom.getItems();
			for (Item item : items) {
				if(item instanceof Meltable)
					((Meltable) item).setMeltItem(hiddenItem);
			}
		}
	}
	
	public XmlParseResult setAdjacentPath(NodeList nList, int roomIndex) {
		
		Room currentRoom = customizingGame.getRoomIndex(roomIndex);
		Room nextRoom; 
		String nextRoomIndex;
		int connectedRoomCnt = 0; 
		
		nextRoomIndex = getAttributeValueAtNthTag("north", nList, roomIndex);
		if(nextRoomIndex != null) {
			nextRoom = customizingGame.getRoomIndex(Integer.parseInt(nextRoomIndex));
			currentRoom.setAdjacentRoom(Action.ACTION_GO_NORTH, nextRoom);
			connectedRoomCnt++;
		}
		
		nextRoomIndex = getAttributeValueAtNthTag("south", nList, roomIndex);
		if(nextRoomIndex != null) {
			nextRoom = customizingGame.getRoomIndex(Integer.parseInt(nextRoomIndex));
			currentRoom.setAdjacentRoom(Action.ACTION_GO_SOUTH, nextRoom);
			connectedRoomCnt++;
		}
		
		nextRoomIndex = getAttributeValueAtNthTag("west", nList, roomIndex);
		if(nextRoomIndex != null) {
			nextRoom = customizingGame.getRoomIndex(Integer.parseInt(nextRoomIndex));
			currentRoom.setAdjacentRoom(Action.ACTION_GO_WEST, nextRoom);
			connectedRoomCnt++;
		}
		
		nextRoomIndex = getAttributeValueAtNthTag("east", nList, roomIndex);
		if(nextRoomIndex != null) {
			nextRoom = customizingGame.getRoomIndex(Integer.parseInt(nextRoomIndex));
			currentRoom.setAdjacentRoom(Action.ACTION_GO_EAST, nextRoom);
			connectedRoomCnt++;
		}
		
		//no room connected
		if(connectedRoomCnt == 0)
			return XmlParseResult.INVALID_DATA; 
		else 
			return XmlParseResult.SUCCESS;
	}
	
	public void putItemListIntoRoom (Room room, String itemList) {
		
		//itemList="shovel:5-key:3"
		String[] splitedToEachItem = itemList.split("-");
		
		//splitedToItemAndCnt[0] = "shovel:5", splitedToItemAndCnt[1] = "key:2",  
		for (String itemAndCnt : splitedToEachItem) {
			//splited[0] = "shovel", splited[1] = "5",  

			String[] splited = itemAndCnt.split(":");
			String itemName = splited[0];
			String itemCnt = splited[1];
			
			Item item = Item.getInstance(itemName);
			for(int j=0; j<Integer.parseInt(itemCnt); j++)
				room.putItem(item);
		}
	}
	
	@Nullable
	public Room makeRoom(String roomType, String roomIndex) {
		
		Room room = null; 
		
		//room type normal, dark, lockable, excavatable, obscured, require
		if(roomType.equals("normal"))
			room = new Room("Description", "Room"+ (Integer.parseInt(roomIndex)+1) );
		else if(roomType.equals("dark"))
			room = new RoomDark("Description Dark", "Room"+ (Integer.parseInt(roomIndex)+1) , "You cannot see", "blind!");
		else if(roomType.equals("lockable"))
			room = new RoomLockable("Description lock", "locked", true);
		else if(roomType.equals("excavatable"))
			room = new RoomExcavatable("Description excavatable", "excavatable", "digdig");
		else if(roomType.equals("obscured"))
			room = new RoomObscured("Description obscured", "obscured");
		else if(roomType.equals("require"))
			room = new RoomRequiredItem("Description require", "Required", "itemName", "Warning you need itemName");

		return room; 
	}
	
	public XmlParseResult parsingGoalInfo(Document doc) {

		int i;
		int nodeLength;
		int goalCnt;
		NodeList nList;
		
		nList = getNodeListOfGivenTag("goal_info", doc);
		goalCnt = Integer.parseInt(getAttributeValueAtNthTag("goal_cnt", nList, 0));	//room_cnt should be unique. 
	
		nList = getNodeListOfGivenTag("goal", doc);
		nodeLength = nList.getLength();		//number of tag

		if(nodeLength != goalCnt) {
			return XmlParseResult.INVALID_DATA;
		}
		
		for(i=0; i<goalCnt; i++) {
			
			String goalIndex = getAttributeValueAtNthTag("index", nList, i);
			String goalType = getAttributeValueAtNthTag("type", nList, i);
			String goalObjects = getAttributeValueAtNthTag("object", nList, i);

			//all goal information should not be null 
			if(goalIndex == null || goalType == null || goalObjects == null) {
				return XmlParseResult.INVALID_DATA;
			}
			
			GameGoal goal = makeGoal(goalType, goalIndex, goalObjects); 
			if(goal == null) {
				return XmlParseResult.INVALID_DATA;
			}
			
			customizingGame.addGoal(goal);
		}
		
		return XmlParseResult.SUCCESS;		
	}

	@Nullable
	public GameGoal makeGoal(String goalType, String goalIndex, String goalObjects) {
		
		GameGoal goal = null; 
			
		/*
		 * 	<goal index="0" type="collect" object="diamond-shovel" />
		 * 	<goal index="1" type="explore" object="6-11-12-13" />
		 * 	<goal index="2" type="point" object="100" /> 
		 */
		if(goalType.equals("collect"))
			goal = new GameCollectGoal(makeListStringSplitedByDash(goalObjects));
		else if(goalType.equals("explore"))
			goal = new GameExploreGoal(makeListStringSplitedByDash(goalObjects));
		else if(goalType.equals("point"))
			goal = new GamePointsGoal(Integer.valueOf(goalObjects)); 

		return goal; 
	}


	private List<String> makeListStringSplitedByDash(String strTobeSplited) {
				
		List<String> itemList = new ArrayList<String>();
		
		//type="collect" object="diamond-shovel" 
		String[] splitedToEachItem = strTobeSplited.split("-");		
		for (String itemStr : splitedToEachItem) {
			itemList.add(itemStr);
		}
		
		return itemList; 
	}
}
