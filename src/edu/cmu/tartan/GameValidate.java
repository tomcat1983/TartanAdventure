package edu.cmu.tartan;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemKey;
import edu.cmu.tartan.item.ItemKeycard;
import edu.cmu.tartan.item.ItemKeycardReader;
import edu.cmu.tartan.item.ItemLock;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Meltable;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomDark;
import edu.cmu.tartan.room.RoomLockable;
import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.room.RoomRequiredItem;

public class GameValidate  {

	private ArrayList<GameGoal> goals = new ArrayList<>();
	private ArrayList<Room> rooms = new ArrayList<>();
	private ArrayList<Item> requiredItems = new ArrayList<>();
	private ArrayList<String> goalItems = new ArrayList<>();
	private List<String> goalRooms = new ArrayList<>();

	private boolean isDarkRoomExist = false;
	private boolean isRequiredRoomExist = false;
	private boolean isObscuredRoomExist = false;
	private boolean isLockedRoomExist = false;
	private int gamePointGoalCnt = 0;
	private int gameCollectGoalCnt = 0;
	private int gameExploreGoalCnt = 0;
	private ArrayList<MapConfig> configErrors = new ArrayList<>();
	private int obscuredRoomCnt = 0; 
	private int obstacleItemCnt = 0;
	private int pointToAchive = 0;

	protected Logger gameLogger = Logger.getGlobal();

	public GameValidate(GameContext gameContext) {
		this.goals = gameContext.getGoals();
		this.rooms = gameContext.getRooms();
	}

	public ArrayList<MapConfig> check() {

		if(rooms.isEmpty()) {
			configErrors.add(MapConfig.NO_ROOM);
			return configErrors; 
		}
		
		roomTypeCheck(); 

		if(isDarkRoomExist && !isThereLightItem()) {
			configErrors.add(MapConfig.NO_LUMINOUS);
		}
		
		if(isRequiredRoomExist) {
			int requiredItemCnt = getRequiredItemCount();
			if(!isThereRequiredItem(requiredItemCnt))
				configErrors.add(MapConfig.NO_REQUIRED_ITEM);
		}
		
		if(isObscuredRoomExist && obscuredRoomCnt!=obstacleItemCnt) {
			configErrors.add(MapConfig.NO_OBSTACLE);
		}
		
		if(isLockedRoomExist) {
			if(!isThereKeyItem())
				configErrors.add(MapConfig.NO_KEY);
			if(!isThereLockItem())
				configErrors.add(MapConfig.NO_LOCK);
		}
		
		if(goals.isEmpty()) {
			configErrors.add(MapConfig.NO_GOAL);
			return configErrors; 
		}
		
		goalTypeCheck();
		
		if(gameCollectGoalCnt > 1 || gameExploreGoalCnt > 1 || gamePointGoalCnt > 1)
			configErrors.add(MapConfig.DUP_GOAL);

		if(gameCollectGoalCnt == 1 && !isThereGoalItem())
			configErrors.add(MapConfig.NO_GOAL_ITEM);

		if(gameExploreGoalCnt == 1 && !isThereRoomToVisit())
			configErrors.add(MapConfig.NO_GOAL_ROOM);

		if(gamePointGoalCnt == 1) {
			int pointCanEarn = calcPointsCanEarn();
			if(pointCanEarn < pointToAchive)
				configErrors.add(MapConfig.CANT_ACHIVE_POINT);
		}
		
		return configErrors;
	}
	
	private void goalTypeCheck() {

		for (GameGoal goal : goals) {
			if(goal instanceof GameCollectGoal) {
				gameCollectGoalCnt++;
				goalItems = ((GameCollectGoal)goal).getItemList();
			}
			else if(goal instanceof GameExploreGoal) {
				gameExploreGoalCnt++;
				goalRooms = ((GameExploreGoal)goal).getItinerary();
			}
			else if(goal instanceof GamePointsGoal) {
				gamePointGoalCnt++; 
				pointToAchive = ((GamePointsGoal)goal).getWinningScore().intValue();
			}
		}
	}
	
	private void roomTypeCheck() {

		for (Room room : rooms) {
			if(room instanceof RoomDark) {
				isDarkRoomExist = true; 
			}
			else if(room instanceof RoomRequiredItem) {
				isRequiredRoomExist = true;
				Item requiredItem = ((RoomRequiredItem)room).requiredItem();
				if(requiredItem != null)
					requiredItems.add(requiredItem);
			}
			else if(room instanceof RoomObscured) {
				isObscuredRoomExist = true; 
				obscuredRoomCnt++;
				Item obstacle = ((RoomObscured)room).getObscuringItem();
				if(obstacle != null)
					obstacleItemCnt++;
			}
			else if(room instanceof RoomLockable) {
				isLockedRoomExist = true; 
			}
		}
	}


	private boolean isThereLightItem() {
		for (Room room : rooms) {
			ArrayList<Item> items = room.getItems();
			for (Item item : items) {
				if(item instanceof Luminous)
					return true; 
			}
		}
		return false; 
	}

	private int getRequiredItemCount() {
		return requiredItems.size(); 
	}

	private boolean isThereRequiredItem(int requiredItemCnt) {

		int foundedItemCnt = 0; 

		for (Item rItem : requiredItems) {
			for (Room room : rooms) {
				ArrayList<Item> items = room.getItems();
				for (Item item : items) {
					if(item.equals(rItem))
						foundedItemCnt++; 
				}
			}
		}

		return (requiredItemCnt == foundedItemCnt);
	}

	private boolean isThereKeyItem() {
		for (Room room : rooms) {
			ArrayList<Item> items = room.getItems();
			for (Item item : items) {
				if(item instanceof ItemKey || item instanceof ItemKeycard)
					return true; 
			}
		}
		return false; 
	}
	
	private boolean isThereLockItem() {
		for (Room room : rooms) {
			ArrayList<Item> items = room.getItems();
			for (Item item : items) {
				if(item instanceof ItemLock || item instanceof ItemKeycardReader)
					return true; 
			}
		}
		return false; 
	}

	private boolean isThereGoalItem() {

		int goalItemCnt = goalItems.size(); 
		int foundedItemCnt = 0; 

		for (String goalItem : goalItems) {
			for (Room room : rooms) {
				ArrayList<Item> items = room.getItems();
				for (Item item : items) {
		
					if(item.toString().equals(goalItem))
						foundedItemCnt++; 
					
					if(item instanceof Meltable) { 
						Item melted = ((Meltable) item).meltItem();
						if(melted.toString().equals(goalItem))
							foundedItemCnt++; 
					}
				}
			}
		}

		return (goalItemCnt == foundedItemCnt);
	}
	
	private boolean isThereRoomToVisit() {

		int roomToVisit = goalRooms.size(); 
		int foundedRoomCnt = 0; 

		for (String goalRoom : goalRooms) {
			for (Room room : rooms) {
				if(room.shortDescription().equals(goalRoom))
					foundedRoomCnt++;
			}
		}

		return (roomToVisit == foundedRoomCnt);
	}
	
	private int calcPointsCanEarn() {

		int sum = 0; 

		for (Room room : rooms) {
			ArrayList<Item> items = room.getItems();
			for (Item item : items) {
				sum += item.value();
			}
		}

		return sum;
	}
	
		
}
