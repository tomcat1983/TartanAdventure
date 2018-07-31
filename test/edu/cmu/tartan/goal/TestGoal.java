package edu.cmu.tartan.goal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.room.Room;

class TestGoal {

	GamePointsGoal pointGoal; 
	private Integer winningScore = 100;  
	private String userName = "Default";
	Player player;

	@BeforeEach
	public void testSetup() {
		Room room = new Room();
		player = new Player(room, userName);
		pointGoal = new GamePointsGoal(winningScore, player);
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
		CharSequence seq = winningScore.toString();
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
}
