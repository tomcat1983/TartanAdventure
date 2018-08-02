package edu.cmu.tartan.games;

import java.util.ArrayList;

import org.eclipse.jdt.annotation.Nullable;

import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.GameContext;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.room.Room;

/**
 * Example game to explore a series of rooms. This is the configuraion discussed in the project description.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 h.h.tak
 * Versions:
 * 1.0 July 2018 - initial version
 */
public class CustomizingGame extends GameConfiguration {

	public CustomizingGame() {
		super("Customizing game made with XML");
	}

	private ArrayList<Room> rooms = new ArrayList<>();
	private ArrayList<GameGoal> goals = new ArrayList<>();


	/**
	 * @param room
	 */
	public void addRoom(Room room) {
		rooms.add(room);
	}

	/**
	 * @param i
	 * @return
	 */
	public Room getRoomIndex(int i) {
		return rooms.get(i);
	}

	/**
	 * @param goal
	 */
	public void addGoal(GameGoal goal) {
		goals.add(goal);
	}

	/**
	 * @param i
	 * @return
	 */
	public GameGoal getGameGoalIndex(int i) {
		return goals.get(i);
	}

	/**
	 * Configure the game
	 * @param game the Game object that will manage exectuion
	 * @throws InvalidGameException
	 */
	@Override
	public boolean configure(@Nullable GameContext context) throws InvalidGameException {
		if(context==null) {
			return false;
		}
		// Set the initial room
		Player player = new Player(rooms.get(0), context.getUserId());
		context.setPlayer(player);

		for (GameGoal gameGoal : goals) {
			gameGoal.setPlayer(player);
			context.addGoal(gameGoal);
		}
		context.setRooms(rooms);
		context.setGameDescription(makeGameDescription());


		if (!context.validate()) throw new InvalidGameException("Game improperly configured");

		return true;
	}

	/**
	 * @return
	 */
	public String makeGameDescription() {

		StringBuilder gameDescription = new StringBuilder();

		gameDescription.append("This is ");

		for (GameGoal gameGoal : goals) {

			if(gameGoal instanceof GameCollectGoal) {
				gameDescription.append("[Collect Item] ");
			}
			else if(gameGoal instanceof GameExploreGoal) {
				gameDescription.append("[Explore Room] ");
			}
			else if(gameGoal instanceof GamePointsGoal) {
				gameDescription.append("[Get Point] ");
			}
		}

		gameDescription.append("Game");

		return gameDescription.toString();
	}
}