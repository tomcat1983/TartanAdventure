package edu.cmu.tartan.xml;

import java.util.ArrayList;
import java.util.List;

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
import edu.cmu.tartan.properties.Meltable;
import edu.cmu.tartan.properties.Pushable;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomDark;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomLockable;
import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.room.RoomRequiredItem;

public class XmlResponseUploadMap extends XmlResponse {

	XmlParseResult xmlParseResult;
	CustomizingGame customizingGame;
	private String userId;

	XmlResponseUploadMap() {
		xmlParseResult = XmlParseResult.SUCCESS;
		customizingGame = new CustomizingGame();
		msgType = XmlMessageType.UPLOAD_MAP_DESIGN;
	}


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

	/* (non-Javadoc)
	 * @see edu.cmu.tartan.xml.XmlResponse#doYourJob(org.w3c.dom.Document)
	 */
	@Override
	public XmlParseResult doYourJob(Document doc) {

		xmlParseResult = parsingRoomInfo(doc);

		if(xmlParseResult.equals(XmlParseResult.SUCCESS))
			xmlParseResult = parsingGoalInfo(doc);

		if(xmlParseResult.equals( XmlParseResult.SUCCESS )) {

			//For save map to server PC
			String mapXml = XmlWriter.convertDocumentToString(doc);
			XmlWriter.saveXmlStringToFile("userMap.xml", mapXml);


			if(!XmlWriter.overWriteFile("gameMap.xml", "userMap.xml"))
				gameLogger.severe("IOException occur when copying userMap.xml to gameMap.xml");

		}

		return xmlParseResult;
	}


	/**
	 * @param doc
	 * @return
	 */
	public GameConfiguration doYourJobReturnGameConfiguration(Document doc) {

		XmlParseResult result = doYourJob(doc);
		if(result.equals(XmlParseResult.SUCCESS))
			return customizingGame;
		else
			return null;
	}

	/**
	 * @param doc
	 * @return
	 */
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
			if( !result.equals(XmlParseResult.SUCCESS))
				return result;
		}

		for(i=0; i<roomCnt; i++) {

			updateRoomDescription(i);
		}

		return result;
	}


	private void updateRoomDescription(int roomIndex) {

		StringBuilder sb = new StringBuilder();
		Room currentRoom = customizingGame.getRoomIndex(roomIndex);
		sb.append("\nYou are in " + currentRoom.shortDescription());


		if(currentRoom instanceof RoomRequiredItem) {
			String requiredItem = ((RoomRequiredItem)currentRoom).requiredItem().toString();
			sb.append(" that require " + requiredItem);
			// don't display direction for hidden room
			String warningStr = "You need " + requiredItem;
			((RoomRequiredItem)currentRoom).setWarningDescription(warningStr+" long");
			((RoomRequiredItem)currentRoom).setShortWarningDescription(warningStr+" short");
		}
		else if(currentRoom instanceof RoomLockable) {
			sb.append("(Locked)");
		}
		else if(currentRoom instanceof RoomDark) {
			sb.append("(Dark)");
		}
		else if(currentRoom instanceof RoomExcavatable) {
			sb.append("(Excavatable)");
		}

		sb.append(".\n");


		String directionStr;

		directionStr = getDirectionString(currentRoom, Action.ACTION_GO_EAST);
		if(directionStr != null )
			sb.append(directionStr);

		directionStr = getDirectionString(currentRoom, Action.ACTION_GO_SOUTH);
		if(directionStr != null )
			sb.append(directionStr);

		directionStr = getDirectionString(currentRoom, Action.ACTION_GO_NORTH);
		if(directionStr != null )
			sb.append(directionStr);

		directionStr = getDirectionString(currentRoom, Action.ACTION_GO_WEST);
		if(directionStr != null )
			sb.append(directionStr);

		currentRoom.setDescription(sb.toString());

	}


	private String getDirectionString(Room currentRoom, Action actionGoDirection) {

		String directionStr = null;
		Room adjacentRooms;

		adjacentRooms = currentRoom.getRoomForDirection(actionGoDirection);
		if(adjacentRooms == null)
			return null;

		String[] directionAlias = actionGoDirection.getAliases();

		if(adjacentRooms instanceof RoomObscured) {

			ArrayList<Item> items = currentRoom.getItems();
			for (Item item : items) {
				if(item instanceof Pushable )
					((RoomObscured)adjacentRooms).setUnobscureMessage( "You've revelealed a hidden room to the " + directionAlias[0] + "!");
			}
			// don't display direction for hidden room
		}
		else if(adjacentRooms instanceof RoomDark) {
			directionStr = "There seems to be a dark room to the " + directionAlias[0] + ". \n";
		}
		else if(adjacentRooms instanceof RoomLockable) {
			directionStr = "There is a locked room to the " + directionAlias[0] + ". \n";
		}
		else if(adjacentRooms instanceof RoomExcavatable) {
			directionStr = "There is a excavable room to the " + directionAlias[0] + ". \n";
		}
		else
			directionStr = "You can go " + directionAlias[0] + " to " + adjacentRooms.shortDescription() + ". \n";

		return directionStr;
	}


	//type="lockable" lock_item="lock:2" key_item="key:3:3"
	/**
	 * @param currentRoom
	 * @param nList
	 * @param roomIndex
	 * @return
	 */
	public XmlParseResult setRelationshipForRoomLockable(RoomLockable currentRoom, NodeList nList, int roomIndex) {

		String keyItemStr = getAttributeValueAtNthTag("key_item", nList, roomIndex);
		String lockItemStr = getAttributeValueAtNthTag("lock_item", nList, roomIndex);

		if(keyItemStr == null || lockItemStr == null)
			return XmlParseResult.INVALID_DATA;

		//key_item="itemName:roomIndex"
		String[] splited = keyItemStr.split(":");
		String keyName = splited[0];
		int keyLocationIndex = Integer.parseInt(splited[1]);

		//lock_item="itemName:roomIndex"
		splited = lockItemStr.split(":");
		String lockName = splited[0];
		int lockLocationIndex = Integer.parseInt(splited[1]);

		Room keyLocatedRoom = customizingGame.getRoomIndex(keyLocationIndex);
		Room lockLocatedRoom = customizingGame.getRoomIndex(lockLocationIndex);

		Item lock = Item.getInstance(lockName, userId);
        Item key = Item.getInstance(keyName, userId);

		lock.setRelatedRoom(currentRoom);
		lockLocatedRoom.putItem(lock);
		keyLocatedRoom.putItem(key);

		currentRoom.setKey(key);

		return XmlParseResult.SUCCESS;
	}

	//type="require" require_item="gold:11:2"/>
	/**
	 * @param currentRoom
	 * @param nList
	 * @param roomIndex
	 * @return
	 */
	public XmlParseResult setRelationshipForRoomRequiredItem(RoomRequiredItem currentRoom, NodeList nList, int roomIndex) {

		String requireItemStr = getAttributeValueAtNthTag("require_item", nList, roomIndex);

		if(requireItemStr == null)
			return XmlParseResult.INVALID_DATA;

		//require_item="itemName:roomIndex"
		String[] splited = requireItemStr.split(":");
		String itemName = splited[0];
		int itemLocationIndex = Integer.parseInt(splited[1]);

		Room itemLocatedRoom = customizingGame.getRoomIndex(itemLocationIndex);
		Item requireItem = Item.getInstance(itemName, userId);
		currentRoom.setRequiredItem(requireItem);
		itemLocatedRoom.putItem(requireItem);

		return XmlParseResult.SUCCESS;
	}


	//type="obscured" obstacle="fridge:9" />
	/**
	 * @param currentRoom
	 * @param nList
	 * @param roomIndex
	 * @return
	 */
	public XmlParseResult setRelationshipForRoomObscured(RoomObscured currentRoom, NodeList nList, int roomIndex) {

		String obstacleStr = getAttributeValueAtNthTag("obstacle", nList, roomIndex);

		if(obstacleStr == null)
			return XmlParseResult.INVALID_DATA;

		//obstacle="itemName:roomIndex"
		String[] splited = obstacleStr.split(":");
		String itemName = splited[0];
		int itemLocationIndex = Integer.parseInt(splited[1]);

		Room itemLocatedRoom = customizingGame.getRoomIndex(itemLocationIndex);
		Item obstacle = Item.getInstance(itemName, userId);
		currentRoom.setObscuringItem(obstacle);
		currentRoom.setObscured(true);
		currentRoom.setUnobscureMessage("You've revelealed a hidden room to direction(TODO)");
		currentRoom.setObscureMessage("This room is hidden");
		obstacle.setRelatedRoom(currentRoom);
		itemLocatedRoom.putItem(obstacle);

		return XmlParseResult.SUCCESS;
	}


	/**
	 * @param nList
	 * @param roomIndex
	 * @return
	 */
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

	/**
	 * @param currentRoom
	 * @param hiddenItemStr
	 */
	public void setHiddenItem(Room currentRoom, String hiddenItemStr) {

		Item hiddenItem = Item.getInstance(hiddenItemStr, userId);

		if(hiddenItem != null) {
			List<Item> items = currentRoom.getItems();
			for (Item item : items) {
				if(item instanceof Meltable)
					((Meltable) item).setMeltItem(hiddenItem);
			}
		}
	}

	/**
	 * @param nList
	 * @param roomIndex
	 * @return
	 */
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

	/**
	 * @param room
	 * @param itemList
	 */
	public void putItemListIntoRoom (Room room, String itemList) {

		//itemList="shovel-key"
		String[] splitedToEachItem = itemList.split("-");

		for (String itemName : splitedToEachItem) {
			Item item = Item.getInstance(itemName, userId);
			room.putItem(item);
		}
	}

	/**
	 * @param roomType
	 * @param roomIndex
	 * @return
	 */
	@Nullable
	public Room makeRoom(String roomType, String roomIndex) {

		Room room = null;
		String shortDescription = "Room"+ (Integer.parseInt(roomIndex)+1);

		//room type normal, dark, lockable, excavatable, obscured, require
		if(roomType.equals("normal"))
			room = new Room("Description", shortDescription);
		else if(roomType.equals("dark"))
			room = new RoomDark("Description Dark", shortDescription, "You cannot see", "blind!");
		else if(roomType.equals("lockable"))
			room = new RoomLockable("Description lock", shortDescription, true);
		else if(roomType.equals("excavatable"))
			room = new RoomExcavatable("Description excavatable", shortDescription, "digdig");
		else if(roomType.equals("obscured"))
			room = new RoomObscured("Description obscured", shortDescription);
		else if(roomType.equals("require"))
			room = new RoomRequiredItem("Description require", shortDescription, "itemName", "Warning you need itemName");

		return room;
	}

	/**
	 * @param doc
	 * @return
	 */
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

	/**
	 * @param goalType
	 * @param goalIndex
	 * @param goalObjects
	 * @return
	 */
	@Nullable
	public GameGoal makeGoal(String goalType, String goalIndex, String goalObjects) {

		GameGoal goal = null;

		/*
		 * 	<goal index="0" type="collect" object="diamond-shovel" />
		 * 	<goal index="1" type="explore" object="6-11-12-13" />
		 * 	<goal index="2" type="point" object="100" />
		 */
		if(goalType.equals("collect"))
			goal = new GameCollectGoal(makeItemListStringSplitedByDash(goalObjects));
		else if(goalType.equals("explore"))
			goal = new GameExploreGoal(makeRoomListStringSplitedByDash(goalObjects));
		else if(goalType.equals("point"))
			goal = new GamePointsGoal(Integer.valueOf(goalObjects));

		return goal;
	}


	private ArrayList<String> makeItemListStringSplitedByDash(String strTobeSplited) {

		ArrayList<String> itemList = new ArrayList<>();

		//type="collect" object="diamond-shovel"
		String[] splitedToEachItem = strTobeSplited.split("-");
		for (String itemStr : splitedToEachItem) {
			itemList.add(itemStr);
		}

		return itemList;
	}

	private ArrayList<String> makeRoomListStringSplitedByDash(String strTobeSplited) {

		ArrayList<String> roomList = new ArrayList<>();

		//<goal index="1" type="explore" object="6-11-12-13" />
		String[] splitedToEachItem = strTobeSplited.split("-");
		for (String roomIndex : splitedToEachItem) {
			int roomNumber = Integer.parseInt(roomIndex)+1;
			roomList.add("Room" + roomNumber);
		}

		return roomList;
	}
}
