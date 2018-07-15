package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.games.*;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.*;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

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
        System.out.println(sb.toString());
    }

    /**
     * TODO Set player game goal(will be removed)
     */
    private void setPlayerGameGoal() {
        for (GameGoal g : goals) {
            this.player.addGoal(g);
        }
    }

    private ArrayList<GameConfiguration> loadGame() {
    	// TODO need to load about real game from XML(new game feature)
    	
    	// TODO I will select between call with argument and some global variable.
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
    
    /**
     * Configure the game.
     */
    public boolean configureGame() {

    	ArrayList<GameConfiguration> menu = loadGame();
    	
        if(!menu.isEmpty()) {
        	int choice = 0;
            while(true) {
                printMenu(menu);
                System.out.print("> ");
                String input = this.scanner.nextLine();
                try {
                    if (input.equalsIgnoreCase("help")) {
                        help();
                    } else {
                    	choice = Integer.parseInt(input) - 1;
                    	if(choice > 0 || menu.size() >= choice) {
                            GameConfiguration gameConfig = menu.get(choice);
                            gameName = gameConfig.getName();
                            gameConfig.configure(this);
                            break;
                    	} else {
                    		System.out.println("Invaild selection");                    		
                    	}
                    }
                }
                catch (InvalidGameException ige) {
                    System.out.println("Game improperly configured, please try again.");
                    return false;
                }
                catch(Exception e) {
                    System.out.println("Invalid selection.");
                }
            }
        }
        // Once the game has been configured, it is time to play!
        this.showIntro();
        // Configure the game, add the goals and exe
        setPlayerGameGoal();
        return true;
    }

    /**
     * Start the Game.
     * @throws NullPointerException
     */
    public void start() throws NullPointerException {    	
        // Orient the player
        this.player.lookAround();

        try {
            String input = null;
            while(true) {
                System.out.print("> ");

                input = this.scanner.nextLine();

                if (input.compareTo("quit") == 0) {
                    for (GameGoal g: goals) {
                        System.out.println(g.getStatus());
                    }
                    break;
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
                	playerExecutionEngine.executeAction(this.interpreter.interpretString(input));;
                    // executeAction(this.interpreter.interpretString(input));
                    // every time an action is executed the game state must be evaluated
                    if (evaluateGame()) {
                        winGame();
                        break;
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("I don't understand that \n\nException: \n" + e);
            e.printStackTrace();
            start();
        }
        System.out.println("Game Over");
    }

    /**
     * Display the win game message
     */
    private void winGame() {

        System.out.println("Congrats!");

        System.out.println("You've won the '" + gameName + "' game!\n" );
        System.out.println("- Final score: " + player.getScore());
        System.out.println("- Final inventory: ");
        if (player.getCollectedItems().isEmpty()) {
            System.out.println("You don't have any items.");
        }
        else {
            for (Item i : player.getCollectedItems()) {
                System.out.println(i.toString() + " ");
            }
        }
        System.out.println("\n");
    }

    /**
     * Determine if all the game goals have been completed
     * @return
     */
    private Boolean evaluateGame() {
        Vector<GameGoal> playerGoals = player.getGoals();

        for (Iterator<GameGoal> iterator = playerGoals.iterator(); iterator.hasNext(); ) {
            GameGoal g = iterator.next();
            if (g.isAchieved()) {
                iterator.remove();
            }
        }
        return playerGoals.isEmpty();
    }

    private void status() {
        System.out.println("The current game is '" + gameName + "': " + gameDescription + "\n");
        System.out.println("- There are " + goals.size() + " goals to achieve:");

        for (int i=0; i < goals.size(); i++) {
            System.out.println("  * " + (i+1)+ ": "+ goals.get(i).describe() + ", status: " + goals.get(i).getStatus());
        }
        System.out.println("\n");
        System.out.println("- Current room:  " + player.currentRoom() + "\n");
        System.out.println("- Items in current room: ");
        for (Item i : player.currentRoom().getItems()) {
            System.out.println("   * " + i.toString() + " ");
        }
        System.out.println("\n");

        System.out.println("- Current score: " + player.getScore());

        System.out.println("- Current inventory: ");
        if (player.getCollectedItems().isEmpty()) {
            System.out.println("   You don't have any items.");
        } else {
            for (Item i : player.getCollectedItems()) {
                System.out.println("   * " + i.toString() + " ");
            }
        }
        System.out.println("\n");

        System.out.println("- Rooms visited: ");
        Vector<Room> rooms = player.getRoomsVisited();
        if (rooms.isEmpty()) {
            System.out.println("You have not been to any rooms.");
        } else {
            for (Room r : rooms) {
                System.out.println("  * " +r.description() + " ");
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

    /**
     * Display help menu
     */
    private void help() {

        // Credit to emacs Dunnet by Ron Schnell
        System.out.println("Welcome to TartanAdventure RPG Help." +
                "Here is some useful information (read carefully because there are one\n" +
                "or more clues in here):\n");

        System.out.println("- To view your current items: type \"inventory\"\n");
        System.out.println("- You have a number of actions available:\n");

        StringBuilder directions = new StringBuilder("Direction: go [");
        StringBuilder dirobj = new StringBuilder("Manipulate object directly: [");
        StringBuilder indirobj = new StringBuilder("Manipulate objects indirectly, e.g. Put cpu in computer: [");
        StringBuilder misc = new StringBuilder("Misc. actions [");

        for( Action a : Action.values()) {
            if (a.type() == Type.TYPE_DIRECTIONAL) {
                for (String s : a.getAliases()) directions.append("'" + s + "' ");
            } else if (a.type() == Type.TYPE_HASDIRECTOBJECT) {
                for (String s : a.getAliases()) dirobj.append("'" + s + "' ");
            } else if (a.type() == Type.TYPE_HASINDIRECTOBJECT) {
                for (String s : a.getAliases()) indirobj.append("'" + s + "' ");
            } else if (a.type() == Type.TYPE_UNKNOWN) {
                for (String s : a.getAliases()) misc.append("'" + s + "' ");
            }
        }
        directions.append("]");
        dirobj.append("]");
        indirobj.append("]");
        misc.append("]");

        System.out.println("- "+ directions.toString() + "\n");
        System.out.println("- " + dirobj.toString() + "\n");
        System.out.println("- " + indirobj.toString() + "\n");
        System.out.println("- " +misc.toString() + "\n");
        System.out.println("- You can inspect an inspectable item by typing \"Inspect <item>\"\n");
        System.out.println("- You can quit by typing \"quit\"\n");
        System.out.println("- Good luck!\n");

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

        System.out.println("Welcome to Tartan Adventure (1.0), by Tartan Inc..");
        System.out.println("Game: " + gameDescription);
        System.out.println("To get help type 'help' ... let's begin\n");
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
