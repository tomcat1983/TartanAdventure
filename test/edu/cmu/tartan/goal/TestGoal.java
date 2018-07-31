package edu.cmu.tartan.goal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;

class TestGoal {

	GamePointsGoal pointGoal; 
	GameCollectGoal collectGoal; 

	private Integer winningScore = 100;  
	final static String USER_NAME = "Default";
	Player player;
	private ArrayList<String> itemsList = new ArrayList<>(); 
	final static String DIAMOND = "diamond";
	final static String FOOD = "food";
	
	@BeforeEach
	public void testSetup() {
		Room room = new Room();
		player = new Player(room, USER_NAME);
		pointGoal = new GamePointsGoal(winningScore, player);
		
		itemsList.clear();
		itemsList.add(DIAMOND);
		itemsList.add(FOOD);
		
		collectGoal = new GameCollectGoal(itemsList, player);
	}


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
