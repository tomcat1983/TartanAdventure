package edu.cmu.tartan.goal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.TerminateGameException;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;

class TestGoal {

	GamePointsGoal pointGoal; 
	GameCollectGoal collectGoal; 
	GameExploreGoal exploreGoal; 

	private Integer winningScore = 100;  
	final static String USER_NAME = "Default";
	Player player;
	private ArrayList<String> itemsList = new ArrayList<>(); 
	private List<String> places = new ArrayList<>();
	final static String DIAMOND = "diamond";
	final static String FOOD = "food";
	
	final static String ROOM1 = "Room1";
	final static String ROOM2 = "Room2";
	private Room room;
	
	@BeforeEach
	public void testSetup() {
		room = new Room();
		player = new Player(room, USER_NAME);
		pointGoal = new GamePointsGoal(winningScore, player);
		
		itemsList.clear();
		itemsList.add(DIAMOND);
		itemsList.add(FOOD);
		collectGoal = new GameCollectGoal(itemsList, player);
		
		places.clear();
		places.add(ROOM1);
		places.add(ROOM2);
		exploreGoal = new GameExploreGoal(places, player);
	}

	/*
	 * Game Point Goal Test 
	 */
	
	@Test
	void testGamePointGoalWinningScore() {
		assertEquals(winningScore, pointGoal.getWinningScore());	
	}

	@Test
	void testGamePointGoalDescribeIncludeWinningScore() {
		CharSequence seq = winningScore.toString();
		boolean bool = pointGoal.describe().contains(seq);
		assertTrue(bool);
	}

	@Test
	void testGamePointGoalStatusIncludeWinningScore() {
		CharSequence seq = "out of " + winningScore.toString();
		boolean bool = pointGoal.getStatus().contains(seq);
		assertTrue(bool);
	}
	
	@Test
	void testGamePointGoalStatusIncludePlayerScore() {
		player.score(20);
		player.score(20);	//should 40 
		CharSequence seq = "40 out of";
		boolean bool = pointGoal.getStatus().contains(seq);
		assertTrue(bool);
	}
	
	@Test
	void testGamePointGoalIsNotAchivedYet() {
		player.score(20);
		player.score(20);	//should 40 
		boolean bool = pointGoal.isAchieved();
		assertFalse(bool);
	}
	
	@Test
	void testGamePointGoalIsAchived() {
		player.score(50);
		player.score(50);	//should 100 
		boolean bool = pointGoal.isAchieved();
		assertTrue(bool);
	}
	
	@Test
	void testGamePointGoalSetPlayer() {
		
		GamePointsGoalSpy pointGoalSpy = new GamePointsGoalSpy(winningScore);
		pointGoalSpy.setPlayer(player);
		assertEquals(player, pointGoalSpy.getPlayer());
	}
	
	/*
	 * Game Explore Goal Test 
	 */
	
	@Test
	void testGameExploreGoalDescribeIncludeRoom1() {
		CharSequence seq = ROOM1;
		boolean bool = exploreGoal.describe().contains(seq);
		assertTrue(bool);
	}

	@Test
	void testGameExploreGoalDescribeIncludeRoom2() {
		CharSequence seq = ROOM2;
		boolean bool = exploreGoal.describe().contains(seq);
		assertTrue(bool);
	}
	
	@Test
	void testGameExploreGoalStatusIncludeGoalRoomCount() {
		CharSequence seq = "out of " + Integer.toString(places.size()) + " rooms.";
		boolean bool = exploreGoal.getStatus().contains(seq);
		assertTrue(bool);
	}
	
	@Test
	void testGameExploreGoalStatusIncludePlayerVisitCount() throws TerminateGameException {
		
		Room room1 = new Room(ROOM1, ROOM1);
		PlayerSpy playerSpy = new PlayerSpy(room, USER_NAME);
		exploreGoal.setPlayer(playerSpy);
		playerSpy.saveRoomSpy(room1);
		
		CharSequence seq = "1 out of " + Integer.toString(places.size()) + " rooms.";
		exploreGoal.isAchieved();	// to calculate get item count
		boolean bool = exploreGoal.getStatus().contains(seq);
		
		System.out.println(seq + "\n"+ exploreGoal.getStatus());
		
		assertTrue(bool);
	}
	
	@Test
	void testGameExploreGoalStatusIsNotAchivedYet() {
		
		Room room1 = new Room(ROOM1, ROOM1);
		PlayerSpy playerSpy = new PlayerSpy(room, USER_NAME);
		exploreGoal.setPlayer(playerSpy);
		playerSpy.saveRoomSpy(room1);
		
		boolean bool = exploreGoal.isAchieved();
		assertFalse(bool);
	}
	
	@Test
	void testGameExploreGoalStatusIsAchived() {
		
		Room room1 = new Room(ROOM1, ROOM1);
		Room room2 = new Room(ROOM2, ROOM2);

		PlayerSpy playerSpy = new PlayerSpy(room, USER_NAME);
		exploreGoal.setPlayer(playerSpy);

		playerSpy.saveRoomSpy(room1);
		playerSpy.saveRoomSpy(room2);

		boolean bool = exploreGoal.isAchieved();
		assertTrue(bool);
	}
	
	@Test
	void testGameExploreGoalSetPlayer() {
		
		GameExploreGoalSpy exploreGoalSpy = new GameExploreGoalSpy(places);
		exploreGoalSpy.setPlayer(player);
		assertEquals(player, exploreGoalSpy.getPlayer());
	}
	
	
	/*
	 * Game Collect Goal Test 
	 */

	
	@Test
	void testGameCollectGoalDescribeIncludeDiamond() {
		CharSequence seq = DIAMOND;
		boolean bool = collectGoal.describe().contains(seq);
		assertTrue(bool);
	}

	@Test
	void testGameCollectGoalDescribeIncludeFood() {
		CharSequence seq = FOOD;
		boolean bool = collectGoal.describe().contains(seq);
		assertTrue(bool);
	}
	
	@Test
	void testGameCollectGoalStatusIncludeGoalItemCount() {
		CharSequence seq = "out of " + Integer.toString(itemsList.size()) + " items.";
		boolean bool = collectGoal.getStatus().contains(seq);
		assertTrue(bool);
	}
	
	@Test
	void testGameCollectGoalStatusIncludePlayerItemCount() {
		
		Item item = Item.getInstance(DIAMOND, USER_NAME);
		player.pickup(item);
		
		CharSequence seq = "1 out of " + Integer.toString(itemsList.size()) + " items.";
		collectGoal.isAchieved();	// to calculate get item count
		boolean bool = collectGoal.getStatus().contains(seq);
		
		assertTrue(bool);
	}
	
	@Test
	void testGameCollectGoalStatusIsNotAchivvedYet() {
		
		Item item = Item.getInstance(DIAMOND, USER_NAME);
		player.pickup(item);
		
		boolean bool = collectGoal.isAchieved();
		assertFalse(bool);
	}
	
	@Test
	void testGameCollectGoalStatusIsAchived() {
		
		Item item1 = Item.getInstance(DIAMOND, USER_NAME);
		Item item2 = Item.getInstance(FOOD, USER_NAME);
		player.pickup(item1);
		player.pickup(item2);
		
		boolean bool = collectGoal.isAchieved();
		assertTrue(bool);
	}
	
	@Test
	void testGameCollectGoalSetPlayer() {
		
		GameCollectGoalSpy collectGoalSpy = new GameCollectGoalSpy(itemsList);
		collectGoalSpy.setPlayer(player);
		assertEquals(player, collectGoalSpy.getPlayer());
	}
	
	
	class PlayerSpy extends Player {

		public PlayerSpy(Room currentRoom, String userName) {
			super(currentRoom, userName);
		}
		
		public void saveRoomSpy(Room room) {
			this.saveRoom(room);
		}
		
	}
	
	class GamePointsGoalSpy extends GamePointsGoal {

		public GamePointsGoalSpy(Integer g, Player p) {
			super(g, p);
		} 
		
		public GamePointsGoalSpy(Integer g) {
			super(g, null);
		} 
		
		public Player getPlayer() { 
			return player; 
		}
		
	}
	
	class GameCollectGoalSpy extends GameCollectGoal {

		public GameCollectGoalSpy(ArrayList<String> items, Player p) {
			super(items, p);
		}
		
		public GameCollectGoalSpy(ArrayList<String> items) {
			super(items, null);
		}

		public Player getPlayer() { 
			return player; 
		}
		
	}
	
	class GameExploreGoalSpy extends GameExploreGoal {

		public GameExploreGoalSpy(List<String> places, Player p) {
			super(places, p);
		}
		
		public GameExploreGoalSpy(List<String> places) {
			super(places, null);
		}

		public Player getPlayer() { 
			return player; 
		}

	}
}
