package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.ActionExecutionUnit;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.games.*;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;

import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.jdt.annotation.NonNull;
import java.util.List;

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
	 * Game interface for game message and log
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
        gameInterface.println(sb.toString());
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
            		gameInterface.println("Invaild selection");
            		return false;
            	}
            }
        }
        catch (InvalidGameException ige) {
        	gameInterface.println("Game improperly configured, please try again.");
            return false;
        }
        catch(Exception e) {
        	gameInterface.println("Invalid selection.");
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
                gameInterface.print("> ");
                String input = gameInterface.getCommand();
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
    
    private boolean processGameCommand(String input) {
    	if (input.compareTo("quit") == 0) {
            for (GameGoal g: context.getGoals()) {
            	gameInterface.println(g.getStatus());
            }
            return true;
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
        // Orient the player
        context.getPlayer().lookAround();

        try {
            String input = null;
            while(true) {
            	gameInterface.print("> ");

                input = gameInterface.getCommand();
                if(processGameCommand(input)) {
                	break;
                }
            }
        } catch(Exception e) {
        	gameInterface.severe("I don't understand that \n\nException: \n" + e);
        	gameInterface.severe(e.getMessage());
            start();
        }
        gameInterface.println("Game Over");
    }

    /**
     * Display the win game message
     */
    private void winGame() {

    	gameInterface.println("Congrats!");

    	gameInterface.println("You've won the '" + context.getGameName() + "' game!\n" );
    	gameInterface.println("- Final score: " + context.getPlayer().getScore());
    	gameInterface.println("- Final inventory: ");
        if (context.getPlayer().getCollectedItems().isEmpty()) {
        	gameInterface.println("You don't have any items.");
        }
        else {
            for (Item i : context.getPlayer().getCollectedItems()) {
                gameInterface.println(i.toString() + " ");
            }
        }
        gameInterface.println("\n");
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
    	
        gameInterface.println("The current game is '" + context.getGameName() + "': " + context.getGameDescription() + "\n");
        gameInterface.println("- There are " + context.getGoals().size() + " goals to achieve:");

        for (int i=0; i < context.getGoals().size(); i++) {
            gameInterface.println("  * " + (i+1)+ ": "+ context.getGoals().get(i).describe() + ", status: " + context.getGoals().get(i).getStatus());
        }
        gameInterface.println("\n");
        gameInterface.println("- Current room:  " + player.currentRoom() + "\n");
        gameInterface.println("- Items in current room: ");
        for (Item i : player.currentRoom().getItems()) {
            gameInterface.println("   * " + i.toString() + " ");
        }
        gameInterface.println("\n");

        gameInterface.println("- Current score: " + player.getScore());

        gameInterface.println("- Current inventory: ");
        if (player.getCollectedItems().isEmpty()) {
            gameInterface.println("   You don't have any items.");
        } else {
            for (Item i : player.getCollectedItems()) {
                gameInterface.println("   * " + i.toString() + " ");
            }
        }
        gameInterface.println("\n");

        gameInterface.println("- Rooms visited: ");
        List<Room> rooms = player.getRoomsVisited();
        if (rooms.isEmpty()) {
            gameInterface.println("You have not been to any rooms.");
        } else {
            for (Room r : rooms) {
                gameInterface.println("  * " +r.description() + " ");
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
        gameInterface.println("Welcome to TartanAdventure RPG Help." +
                "Here is some useful information (read carefully because there are one\n" +
                "or more clues in here):\n");

        gameInterface.println("- To view your current items: type \"inventory\"\n");
        gameInterface.println("- You have a number of actions available:\n");

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

        gameInterface.println("- "+ directions.toString() + "\n");
        gameInterface.println("- " + dirobj.toString() + "\n");
        gameInterface.println("- " + indirobj.toString() + "\n");
        gameInterface.println("- " +misc.toString() + "\n");
        gameInterface.println("- You can inspect an inspectable item by typing \"Inspect <item>\"\n");
        gameInterface.println("- You can quit by typing \"quit\"\n");
        gameInterface.println("- Good luck!\n");

    }

    /**
     * Show the game introduction
     */
    public void showIntro() {
        gameInterface.println("Welcome to Tartan Adventure (1.0), by Tartan Inc..");
        gameInterface.println("Game: " + context.getGameDescription());
        gameInterface.println("To get help type 'help' ... let's begin\n");
    }
}
