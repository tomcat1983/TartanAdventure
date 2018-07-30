package edu.cmu.tartan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.games.CollectGame;
import edu.cmu.tartan.games.DarkRoomGame;
import edu.cmu.tartan.games.DemoGame;
import edu.cmu.tartan.games.ExploreGame;
import edu.cmu.tartan.games.InvalidGameException;
import edu.cmu.tartan.games.LockRoomGame;
import edu.cmu.tartan.games.ObscuredRoomGame;
import edu.cmu.tartan.games.PointsGame;
import edu.cmu.tartan.games.RideElevatorGame;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.xml.GameMode;
import edu.cmu.tartan.xml.XmlParser;

/**
 * The main class for game logic. Many if not all decisions about game play are made
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public abstract class Game {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	/**
	 * Game interface for game message
	 */
	protected static final GameInterface gameInterface = GameInterface.getInterface();

	/**
	 * Game context for load and save
	 */
	@NonNull protected GameContext context;
    /**
     * Attempt to interpret input more flexibly.
     */
	private PlayerInterpreter interpreter;
    /**
     * The game execute
     */
    private PlayerExecutionEngine playerExecutionEngine;

    /**
     * Create and configure a new game.
     */
    public Game(@NonNull String userId) {
        // Parse room from file
    	this.context = new GameContext(userId);
        this.interpreter = new PlayerInterpreter();
    }

    /**
     *  Display the game menu
     * @param menu The game menu
     */
    private void printMenu(ArrayList<GameConfiguration> menu) {

        StringBuilder sb = new StringBuilder("Choose a game from the options to below or type 'help' for help. \n");
        for (int i = 0; i < menu.size(); i++) {
            sb.append( (i+1) + ":  " + menu.get(i).getName() + "\n");
        }
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, sb.toString());
    }

    protected GameConfiguration gameFromXML(GameMode mode) {
		XmlParser parseXml;
		try {
			parseXml = new XmlParser();
			return parseXml.loadGameMapXml(mode, context.getUserId());
		} catch (ParserConfigurationException e) {
			gameLogger.severe("Game loading failure. Exception: \n" + e);
	       	gameLogger.severe(e.getMessage());
	       	return null;
		}

    }

    private ArrayList<GameConfiguration> loadGameMenu() {
    	// Need to load about real game from XML(new game feature)

    	ArrayList<GameConfiguration> menu = new ArrayList<>();

        // These are the currently supported games.
        menu.add(new CollectGame());
        menu.add(new PointsGame());
        menu.add(new ExploreGame());
        menu.add(new DarkRoomGame());
        menu.add(new LockRoomGame());
        menu.add(new RideElevatorGame());
        menu.add(new ObscuredRoomGame());
        menu.add(new DemoGame());

        // XML loading game
        GameConfiguration loaclGame = gameFromXML(GameMode.LOCAL);
        if(loaclGame!=null) {
            menu.add(loaclGame);
        }
    	return menu;
    }

    private boolean loadGame(String input, ArrayList<GameConfiguration> menu) {
    	int select = 0;
    	try {
            if (input.equalsIgnoreCase("help")) {
                help();
            } else {
            	select = Integer.parseInt(input) - 1;
            	if(select > 0 || menu.size() >= select) {
                    GameConfiguration gameConfig = menu.get(select);
                    context.setGameName(gameConfig.getName());
                    return gameConfig.configure(context);
            	} else {
            		gameInterface.println(context.getUserId(), MessageType.PRIVATE, "Invaild selection");
            		return false;
            	}
            }
        }
        catch (InvalidGameException ige) {
        	gameInterface.println(context.getUserId(), MessageType.PRIVATE, "Game improperly configured, please try again.");
            return false;
        }
        catch(Exception e) {
        	gameInterface.println(context.getUserId(), MessageType.PRIVATE, "Invalid selection.");
        }

    	return false;
    }

    /**
     * Configure the game.
     */
    public boolean configureGame() {
    	ArrayList<GameConfiguration> menu = loadGameMenu();

        if(!menu.isEmpty()) {
        	boolean result = false;
            while(!result) {
                printMenu(menu);
                gameInterface.print(context.getUserId(), MessageType.PRIVATE, "> ");
                String input = gameInterface.getCommand(context.getUserId());
                result = loadGame(input, menu);
            }
        }
        // Once the game has been configured, it is time to play!
        showIntro();
        // Configure the game, add the goals and exe
        context.setPlayerGameGoal();
        this.playerExecutionEngine = new PlayerExecutionEngine(context.getPlayer());
        return true;
    }

    private boolean processGameCommand(String input) throws TerminateGameException {
    	if (this instanceof LocalGame && input.compareTo("quit") == 0) {
    		return handleQuit();
        }
        else if (input.compareTo("look") == 0) {
            context.getPlayer().lookAround();
        }
        else if (input.compareTo("help") == 0) {
            help();
        }
        else if (input.compareTo("status") == 0) {
            status();
        }
        else if(this instanceof LocalGame && input.compareTo("save") == 0 ) {
        	handleSave();
        }
        else {
        	ActionExecutionUnit actionExecutionUnit = new ActionExecutionUnit(null, null);
        	actionExecutionUnit.setUserId(context.getUserId());
        	Action action = interpreter.interpretString(input, actionExecutionUnit);
        	playerExecutionEngine.executeAction(action, actionExecutionUnit);
        	// every time an action is executed the game state must be evaluated
            if (evaluateGame()) {
                winGame();
                return true;
            }
        }
    	return false;
    }

    /**
     * Start the Game.
     */
    public void start() {
    	gameInterface.print(context.getUserId(), MessageType.PRIVATE, "Enjoy your game!");

        // Orient the player
        context.getPlayer().lookAround();
        try {
            String input = null;
            while(true) {
            	gameInterface.print(context.getUserId(), MessageType.PRIVATE, "> ");

                input = gameInterface.getCommand(context.getUserId());
                if(processGameCommand(input)) {
                	break;
                }
            }
        } catch(TerminateGameException e) {
        	return;
        } catch(Exception e) {
        	gameLogger.severe("I don't understand that \n\nException: \n" + e);
        	gameLogger.severe(e.getMessage());
            start();
        }
        gameInterface.println(context.getUserId(), MessageType.LOSE, "Game Over");
    }

    /**
     * Display the win game message
     */
    private void winGame() {

    	gameInterface.println(context.getUserId(), MessageType.WIN, "Congrats!");

    	gameInterface.println(context.getUserId(), MessageType.WIN, "You've won the '" + context.getGameName() + "' game!\n" );
    	gameInterface.println(context.getUserId(), MessageType.WIN, "- Final score: " + context.getPlayer().getScore());
    	gameInterface.println(context.getUserId(), MessageType.WIN, "- Final inventory: ");
        if (context.getPlayer().getCollectedItems().isEmpty()) {
        	gameInterface.println(context.getUserId(), MessageType.WIN, "You don't have any items.");
        }
        else {
            for (Item i : context.getPlayer().getCollectedItems()) {
                gameInterface.println(context.getUserId(), MessageType.WIN, i.toString() + " ");
            }
        }
        gameInterface.println(context.getUserId(), MessageType.WIN, "\n");
    }

    /**
     * Determine if all the game goals have been completed
     * @return
     */
    private Boolean evaluateGame() {
        List<GameGoal> playerGoals = context.getPlayer().getGoals();

        for (Iterator<GameGoal> iterator = playerGoals.iterator(); iterator.hasNext(); ) {
            GameGoal g = iterator.next();
            if (g.isAchieved()) {
                iterator.remove();
            }
        }
        return playerGoals.isEmpty();
    }

    private void status() {
    	Player player = context.getPlayer();

        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "The current game is '" + context.getGameName() + "': " + context.getGameDescription());

		if(context.getGoals().size() == 1)
			gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- There is a goal to achive");
		else
			gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- There are " + context.getGoals().size() + " goals to achive");


        for (int i=0; i < context.getGoals().size(); i++) {
            gameInterface.println(context.getUserId(), MessageType.PRIVATE, (i+1)+ " "+ context.getGoals().get(i).describe() + " - status: " + context.getGoals().get(i).getStatus());
        }
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- Current room:  " + player.currentRoom() + "\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- Items in current room: ");
        for (Item i : player.currentRoom().getItems()) {
            gameInterface.println(context.getUserId(), MessageType.PRIVATE, "   * " + i.toString() + " ");
        }
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "\n");

        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- Current score: " + player.getScore());

        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- Current inventory: ");
        if (player.getCollectedItems().isEmpty()) {
            gameInterface.println(context.getUserId(), MessageType.PRIVATE, "   You don't have any items.");
        } else {
            for (Item i : player.getCollectedItems()) {
                gameInterface.println(context.getUserId(), MessageType.PRIVATE, "   * " + i.toString() + " ");
            }
        }
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "\n");

        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- Rooms visited: ");
        List<Room> rooms = player.getRoomsVisited();
        if (rooms.isEmpty()) {
            gameInterface.println(context.getUserId(), MessageType.PRIVATE, "You have not been to any rooms.");
        } else {
            for (Room r : rooms) {
                gameInterface.println(context.getUserId(), MessageType.PRIVATE, "  * " +r.description() + " ");
            }
        }
    }

    private void appendString(Action action, StringBuilder builder) {
        for (String string : action.getAliases()) builder.append("'" + string + "' ");
    }
    /**
     * Display help menu
     */
    private void help() {
        // Credit to emacs Dunnet by Ron Schnell
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "Welcome to TartanAdventure RPG Help." +
                "Here is some useful information (read carefully because there are one\n" +
                "or more clues in here):\n");

        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- To view your current items: type \"inventory\"\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- You have a number of actions available:\n");

        StringBuilder directions = new StringBuilder("Direction: go [");
        StringBuilder dirobj = new StringBuilder("Manipulate object directly: [");
        StringBuilder indirobj = new StringBuilder("Manipulate objects indirectly, e.g. Put cpu in computer: [");
        StringBuilder misc = new StringBuilder("Misc. actions [");

        for( Action a : Action.values()) {
            if (a.type() == Type.TYPE_DIRECTIONAL) {
            	appendString(a, directions);
            } else if (a.type() == Type.TYPE_HASDIRECTOBJECT) {
            	appendString(a, dirobj);
            } else if (a.type() == Type.TYPE_HASINDIRECTOBJECT) {
            	appendString(a, indirobj);
            } else if (a.type() == Type.TYPE_UNKNOWN) {
            	appendString(a, misc);
            }
        }
        directions.append("]");
        dirobj.append("]");
        indirobj.append("]");
        misc.append("]");

        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- "+ directions.toString() + "\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- " + dirobj.toString() + "\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- " + indirobj.toString() + "\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- " +misc.toString() + "\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- You can inspect an inspectable item by typing \"Inspect <item>\"\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- You can quit by typing \"quit\"\n");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "- Good luck!\n");
    }

    /**
     * Show the game introduction
     */
    public void showIntro() {
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "Welcome to Tartan Adventure (1.0), by Tartan Inc..");
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "Game: " + context.getGameDescription());
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, "To get help type 'help' ... let's begin\n");
    }

    public boolean handleSave() {
    	if(this instanceof ServerGame) {
    		gameInterface.print(context.getUserId(), MessageType.PRIVATE, GamePlayMessage.SAVE_CANNOT_10_6);
    		return false;
    	}
    	if(this instanceof LocalGame) {
    		((LocalGame)this).save(context.getUserId());
    		gameInterface.print(context.getUserId(), MessageType.PRIVATE, GamePlayMessage.SAVE_SUCCESSFUL_10_4);
    		return true;
    	} else {
    		gameInterface.println(context.getUserId(), MessageType.PRIVATE, GamePlayMessage.SAVE_FAILURE_2_3);
    		return false;
    	}
    }

    public boolean handleQuit() {
        for (GameGoal g: context.getGoals()) {
        	gameInterface.println(context.getUserId(), MessageType.PRIVATE, g.getStatus());
        }
        gameInterface.println(context.getUserId(), MessageType.PRIVATE, GamePlayMessage.WILL_YOU_SAVE_2_1);
        gameInterface.print(context.getUserId(), MessageType.PRIVATE, "> ");

        String input = gameInterface.getCommand(context.getUserId());
        if("yes".equalsIgnoreCase(input)) {
        	return handleSave();
        } else {
        	gameInterface.println(context.getUserId(), MessageType.PRIVATE, GamePlayMessage.REJECT_SAVE_2_4);
        	return true;
        }
    }
}