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
import java.util.Scanner;
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
public class Game {
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

    /**
     * Reads input from the command line.
     */
    private Scanner scanner;

    /**
     * Attempt to interpret input more flexibly.
     */
    private PlayerInterpreter interpreter;
    /**
     * The player for the game
     */
    private Player player;

    /**
     * The game execute
     */
    private PlayerExecutionEngine playerExecutionEngine;
    /**
     * The name and description of the active game
     */
    private String gameName = "";
    private String gameDescription = "";
    /**
     * The set of goals for a game
     */
    private ArrayList<GameGoal> goals = new ArrayList<>();


    /**
     * Create and configure a new game.
     */
    public Game() {
        // Parse room from file
        this.scanner = new Scanner(System.in);
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

    /**
     * Set player game goal(Is it right?)
     */
    private void setPlayerGameGoal() {
        for (GameGoal g : goals) {
            this.player.addGoal(g);
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
                    gameName = gameConfig.getName();
                    gameConfig.configure(this);
                    return true;
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
                String input = this.scanner.nextLine();
                result = loadGame(input, menu); 
            }
        }
        // Once the game has been configured, it is time to play!
        this.showIntro();
        // Configure the game, add the goals and exe
        setPlayerGameGoal();
        return true;
    }
    
    private boolean processGameCommand(String input) {
    	if (input.compareTo("quit") == 0) {
            for (GameGoal g: goals) {
            	gameInterface.println(g.getStatus());
            }
            return true;
        }
        else if (input.compareTo("look") == 0) {
            this.player.lookAround();
        }
        else if (input.compareTo("help") == 0) {
            help();
        }
        else if (input.compareTo("status") == 0) {
            status();
        }
        else {
        	ActionExecutionUnit actionExecutionUnit = new ActionExecutionUnit(null, null);
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
        this.player.lookAround();

        try {
            String input = null;
            while(true) {
            	gameInterface.print("> ");

                input = this.scanner.nextLine();
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

    	gameInterface.println("You've won the '" + gameName + "' game!\n" );
    	gameInterface.println("- Final score: " + player.getScore());
    	gameInterface.println("- Final inventory: ");
        if (player.getCollectedItems().isEmpty()) {
        	gameInterface.println("You don't have any items.");
        }
        else {
            for (Item i : player.getCollectedItems()) {
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
        List<GameGoal> playerGoals = player.getGoals();

        for (Iterator<GameGoal> iterator = playerGoals.iterator(); iterator.hasNext(); ) {
            GameGoal g = iterator.next();
            if (g.isAchieved()) {
                iterator.remove();
            }
        }
        return playerGoals.isEmpty();
    }

    private void status() {
        gameInterface.println("The current game is '" + gameName + "': " + gameDescription + "\n");
        gameInterface.println("- There are " + goals.size() + " goals to achieve:");

        for (int i=0; i < goals.size(); i++) {
            gameInterface.println("  * " + (i+1)+ ": "+ goals.get(i).describe() + ", status: " + goals.get(i).getStatus());
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

    /**
     *  Getter for a player.
     *
     * @return the current player.
     */
    public Player getPlayer() {
        return player;
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
     * Add a goal to the game.
     * @param g the goal to add.
     */
    public void addGoal(GameGoal g) {
        goals.add(g);
    }

    /**
     * Set the player for the game.
     * @param player the player to add to the game.
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.playerExecutionEngine = new PlayerExecutionEngine(this.player);
    }

    /**
     * Show the game introduction
     */
    public void showIntro() {

        gameInterface.println("Welcome to Tartan Adventure (1.0), by Tartan Inc..");
        gameInterface.println("Game: " + gameDescription);
        gameInterface.println("To get help type 'help' ... let's begin\n");
    }

    /**
     * Setter for game description
     * @param description the description
     */
    public void setDescription(String description) {
        this.gameDescription = description;
    }

    /**
     * Ensure that the game parameters are all set
     * @return true if valid, false otherwise
     */
    public boolean validate() {
        // TODO: This method is way too simple. A more thorough validation must be done!
        return (gameName!= null && gameDescription != null);
    }
}