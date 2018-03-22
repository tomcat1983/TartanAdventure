package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.configuration.*;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.*;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class Game {

    protected Scanner scanner;
    protected PlayerInterpreter interpreter;
    protected Player player;
    private File savedGameFile = null;
    private String gameName = "";
    private Vector<GameGoal> goals = new Vector<>();
    private String gameDescription = "";

    public Game() {

        // Parse room from file
        if (savedGameFile == null)
            this.scanner = new Scanner(System.in);
        else {
            // TODO: load saved game state from a file
        }

        configureGame();

        this.interpreter = new PlayerInterpreter();

        for (GameGoal g : goals) {
            this.player.addGoal(g);
        }
    }

    private void printMenu(Vector<GameConfiguration> menu) {

        StringBuilder sb = new StringBuilder("Choose a game from the options to below: \n");
        for (int i = 0; i < menu.size(); i++) {
            sb.append( (i+1) + ":  " + menu.elementAt(i).name + "\n");
        }
        System.out.println(sb.toString());
    }

    private void configureGame() {

        Vector<GameConfiguration> menu = new Vector<GameConfiguration>();

        menu.add(new CollectGame());
        menu.add(new PointsGame());
        menu.add(new ExploreGame());
        menu.add(new DarkRoomGame());
        menu.add(new LockRoomGame());
        menu.add(new RideElevatorGame());
        menu.add(new ObscuredRoomGame());

        printMenu(menu);

        int choice = 0;
        while(true) {
            System.out.print("> ");
            String input = this.scanner.nextLine();
            try {
                choice = Integer.parseInt(input) - 1;
            }
            catch(Exception e) {
                System.out.println("Invalid selection.");
                continue;
            }
            try {
                GameConfiguration gameConfig = menu.elementAt(choice);
                gameName = gameConfig.name;
                gameConfig.configure(this);
                break;
            }
            catch (InvalidGameException ige) {
                System.out.println("Game improperly configured, please try again.");
            }
        }

        this.showIntro();
    }

    private void executeAction(Action a) {

        switch(a.type()) {

            case TYPE_DIRECTIONAL:
                move(a);
                break;
            case TYPE_HASDIRECTOBJECT:
                Item val = a.directObject();

                switch(a) {
                    case ActionPickUp: {
                        Item o = a.directObject();
                        Item container = null;
                        if(this.player.currentRoom().hasItem(o)) {
                            if(o instanceof Holdable) {
                                System.out.println("Taken.");

                                this.player.currentRoom().remove(o);
                                this.player.pickup(o);
                                this.player.score( ((Holdable)o).value());
                            }
                            else {
                                System.out.println("You cannot pick up this item.");
                            }
                        }
                        else if((container = containerForItem(o)) != null) {

                            System.out.println("Taken.");
                            ((Hostable)container).uninstall(o);
                            this.player.pickup(o);
                            Holdable h = (Holdable) o;
                            this.player.score( ((Holdable)o).value());
                        }
                        else if(this.player.hasItem(o)) {
                            System.out.println("You already have that item in your inventory.");
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;
                    }
                    case ActionDestroy: {
                        Item item = a.directObject();
                        if (this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if (item instanceof Destroyable) {
                                System.out.println("Smashed.");
                                ((Destroyable)item).destroy();
                                item.setDescription("broken " + item.toString());
                                item.setDetailDescription("broken " + item.detailDescription());
                                if (((Destroyable)item).disappears()) {
                                    this.player.drop(item);
                                    this.player.currentRoom().remove(item);
                                    // Get points!
                                    this.player.score(item.value());
                                }

                                if(item instanceof Hostable) {
                                    this.player.currentRoom().putItem(((Hostable)item).installedItem());
                                    ((Hostable)item).uninstall(((Hostable)item).installedItem());
                                }
                            }
                            else {
                                System.out.println("You cannot break this item.");
                            }
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;
                    }
                    case ActionInspect: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Inspectable) {
                                ((Inspectable)item).inspect();
                            }
                            else {
                                System.out.println("You cannot inspect this item.");
                            }
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;
                    }
                    case ActionDrop: {
                        Item item = a.directObject();
                        if(this.player.hasItem(item)) {
                            if(item instanceof Holdable) {
                                System.out.println("Dropped.");
                                this.player.drop(item);
                                this.player.currentRoom().putItem(item);
                            }
                            else {
                                System.out.println("You cannot drop this item.");
                            }
                        }
                        else {
                            System.out.println("You don't have that item to drop.");
                        }
                        if(this.player.currentRoom() instanceof RoomRequiredItem) {
                            RoomRequiredItem r = (RoomRequiredItem)this.player.currentRoom();
                            r.playerDidDropRequiredItem();
                        }
                        break;
                    }
                    case ActionThrow: {
                        Item item = a.directObject();
                        if(this.player.hasItem(item)) {
                            if(item instanceof Chuckable) {
                                System.out.println("Thrown.");
                                ((Chuckable)item).chuck();
                                this.player.drop(item);
                                this.player.currentRoom().putItem(item);
                            }
                            else {
                                System.out.println("You cannot throw this item.");
                            }
                        }
                        else {
                            System.out.println("You don't have that item to throw.");
                        }
                        break;
                    }
                    case ActionShake: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Shakeable) {
                                ((Shakeable)item).shake();
                                if(((Shakeable)item).deadly()) {
                                    this.player.die();
                                }
                            }
                            else {
                                System.out.println("I don't know how to do that.");
                            }
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;
                    }
                    case ActionEnable: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Startable) {
                                System.out.println("Done.");
                                ((Startable)item).start();
                            }
                            else {
                                System.out.println("I don't know how to do that.");
                            }
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;

                    }
                    case ActionPush: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Pushable) {

                                // Pushing the button is worth points
                                Pushable p = (Pushable) item;
                                p.push();
                                this.player.score(item.value());

                                if(item.relatedRoom() instanceof RoomElevator) { // player is next to an elevator
                                    ((RoomElevator)item.relatedRoom()).call(this.player.currentRoom());
                                }
                                else if(this.player.currentRoom() instanceof RoomElevator) { // player is in an elevator
                                    ((RoomElevator)this.player.currentRoom()).call(Integer.parseInt(item.getAliases()[0])-1);
                                }
                            }
                            else {
                                System.out.println("Nothing happens.");
                            }
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;
                    }
                    case ActionEat: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item) || this.player.hasItem(item)) {
                            if(item instanceof Edible) {
                                // eating something gives scores
                                Edible e = (Edible)item;
                                e.eat();
                                player.score(item.value());
                                // Once we eat it, then it's gone
                                this.player.currentRoom().remove(item);
                            }
                            else {
                                if(item instanceof Holdable) {
                                    System.out.println("As you  shove the " + a.directObject() + " down your throat, you begin to choke.");
                                    this.player.die();
                                }
                                else {
                                    System.out.println("That cannot be consumed.");
                                }
                            }
                        }
                        break;
                    }
                    case ActionOpen: {
                        Item item = a.directObject();
                        if(this.player.hasItem(item) || this.player.currentRoom().hasItem(item)) {
                            if(item instanceof Openable) {
                                Openable o = ((Openable)item);
                                // if you can open the item , you score!
                                if (o.open() == true) {
                                    player.score(item.value());
                                    this.player.currentRoom().remove(item);
                                }
                            }
                            else {
                                System.out.println("You cannot open this.");
                            }
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;
                    }
                    case ActionExplode: {
                        Item dynamite = a.directObject();
                        if(this.player.hasItem(dynamite) || this.player.currentRoom().hasItem(dynamite)) {
                            if(dynamite instanceof Explodable) {
                                if(this.player.currentRoom().isAdjacentToRoom(dynamite.relatedRoom())) {
                                    Explodable explode = (Explodable)dynamite;
                                    explode.explode();
                                    this.player.score(explode.value());
                                }
                                else {
                                    System.out.println("There isn't anything to blow up here.");
                                }
                            }
                            else {
                                System.out.println("That item is not an explosive.");
                            }
                        }
                        else {
                            System.out.println("You do not have that item in your inventory.");
                        }
                        break;
                    }

                }
            case TYPE_HASINDIRECTOBJECT:
                switch(a) {
                    case ActionPut: {
                        Item itemToPut = a.directObject();
                        Item itemToBePutInto = a.indirectObject();
                        if(!this.player.hasItem(itemToPut)) {
                            System.out.println("You don't have that object in your inventory.");
                            break;
                        }
                        else if(itemToBePutInto == null) {
                            System.out.println("You must supply an indirect object.");
                            break;
                        }
                        else if(!this.player.currentRoom().hasItem(itemToBePutInto)) {
                            System.out.println("That object doesn't exist in this room.");
                            break;
                        }
                        else if(itemToBePutInto instanceof ItemMagicBox && !(itemToPut instanceof Valuable)) {
                            System.out.println("This item has no value--putting it in this " + itemToBePutInto + " will not score you any points.");
                        }
                        else if(!(itemToBePutInto instanceof Hostable) || !(itemToPut instanceof Installable)) {
                            System.out.println("You cannot put a " + itemToPut + " into this " + itemToBePutInto);
                        }
                        else {
                            System.out.println("Done.");
                            this.player.drop(itemToPut);
                            this.player.putItemInItem(itemToPut, itemToBePutInto);
                        }
                        break;
                    }
                    case ActionTake: {
                        Item contents = a.directObject();
                        Item container = a.indirectObject();
                        if(!this.player.currentRoom().hasItem(container)) {
                            System.out.println("I don't see that here.");
                        }
                        else if(!(container instanceof Hostable)) {
                            System.out.println("You can't have an item inside that.");
                        }
                        else {
                            if(((Hostable)container).installedItem() == contents) {
                                ((Hostable)container).uninstall(contents);
                                this.player.pickup(contents);
                                System.out.println("Taken.");
                            }
                            else {
                                System.out.println("That item is not inside this " + container);
                            }
                        }
                        break;
                    }
                }
            case TYPE_HASNOOBJECT: {
                switch(a) {
                    case ActionLook:
                        this.player.lookAround();
                        break;
                    case ActionDig:
                        if(this.player.currentRoom() instanceof RoomExcavatable) {
                            RoomExcavatable curr = (RoomExcavatable)this.player.currentRoom();
                            curr.dig();
                        }
                        else {
                            System.out.println("You are not allowed to dig here");
                        }
                        break;
                    case ActionClimb:
                        move(Action.ActionGoUp);
                        break;
                    case ActionJump:
                        move(Action.ActionGoDown);
                        break;
                    case ActionViewItems:
                        Vector<Item> items = this.player.getCollectedItems();
                        if (items.size() == 0) {
                            System.out.println("You don't have any items.");
                        }
                        else {
                            for(Item item : this.player.getCollectedItems()) {
                                System.out.println("You have a " + item.description() + ".");
                            }
                        }
                        break;
                    case ActionDie:
                        this.player.die();
                        break;
                    case ActionHelp:
                        help(this.player);
                        break;
                }
                break;
            }
            case TYPE_UNKNOWN: {
                switch(a) {
                    case ActionPass: {
                        // intentionally blank
                        break;
                    }
                    case ActionError: {
                        System.out.println("I don't understand that.");
                        break;
                    }
                    case ActionUnknown: {
                        System.out.println("I don't understand that.");
                        break;
                    }
                }
                break;
            }
            default:
                System.out.println("I don't understand that");
                break;
        }
    }

    /**
     *
     * @throws NullPointerException
     */
    public void start() throws NullPointerException {

        this.player.lookAround();

        try {
            String input = null;
            while(true) {
                System.out.print("> ");

                input = this.scanner.nextLine();

                if (input.compareTo("quit") == 0) {
                    System.out.println("Quitting game");
                    break;
                }
                else if (input.compareTo("look") == 0) {
                    this.player.lookAround();
                }
                else if (input.compareTo("help") == 0) {
                    help(this.player);
                }
                else if (input.compareTo("status") == 0) {
                    System.out.println("The current game is '" + gameName + "'");
                    System.out.println("The game objective is '" + gameDescription + "'");
                    System.out.println("---\nCurrent room:  " + player.currentRoom());
                    System.out.println("---\nCurrent score: " + player.getScore());

                    System.out.println("---\nCurrent inventory: ");
                    if (player.getCollectedItems().size() == 0) {
                        System.out.println("You don't have any items.");
                    } else {
                        for (Item i : player.getCollectedItems()) {
                            System.out.println(i.toString() + " ");
                        }
                    }
                    System.out.println("---\nRooms visited: ");
                    Vector<Room> rooms = player.getRoomsVisited();
                    if (rooms.size() == 0) {
                        System.out.println("You have not been to any rooms.");
                    } else {
                        for (Room r : rooms) {
                            System.out.println(r.description() + " ");
                        }
                    }
                }
                else if (input.compareTo("save") == 0) {
                    // TODO: Save the state of the game
                }
                else {
                    executeAction(this.interpreter.interpretString(input));
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

        displayAsciiArt("Game Over");
    }

    private void winGame() {

        displayAsciiArt("CONGRATS");

        System.out.println("---\nYou've won the '" + gameName + "' game!" );
        System.out.println("---\nFinal score: " + player.getScore());
        System.out.println("---\nFinal inventory: ");
        if (player.getCollectedItems().size() == 0) {
            System.out.println("You don't have any items.");
        }
        else {
            for (Item i : player.getCollectedItems()) {
                System.out.println(i.toString() + " ");
            }
        }
        System.out.println("---\n");
    }

    private Boolean evaluateGame() {
        Vector<GameGoal> goals = player.getGoals();
        for (Iterator<GameGoal> iterator = goals.iterator(); iterator.hasNext(); ) {
            GameGoal g = iterator.next();
            if (g.isAchieved()) {
                iterator.remove();
            }
        }
        return goals.isEmpty();
    }

    private void move(Action a) {
        this.player.move(a);
    }

    public Player getPlayer() {
        return player;
    }
    private Item containerForItem(Item item) {
        for(Item i : this.player.currentRoom().items) {
            if (i instanceof Hostable) {
                if(item == ((Hostable)i).installedItem() && item.isVisible()) {
                    return i;
                }
            }
        }
        return null;
    }
    private void help(Player player) {

        System.out.println(" -- TartanAdventure RPG Help Menu -- ");
        System.out.println("To view your current items: type \"inventory\"");
        System.out.println("Actions available:");

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

        System.out.println(directions.toString());
        System.out.println(dirobj.toString());
        System.out.println(indirobj.toString());
        System.out.println(misc.toString());
        System.out.println("You can inspect an inspectable item by typing \"Inspect <item>\"");
        System.out.println("You can quit by typing \"quit\"");

    }

    public void addGoal(GameGoal g) {
       goals.add(g);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void displayAsciiArt(String msg) {
        BufferedImage image = new BufferedImage(144, 32, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Dialog", Font.PLAIN, 18));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.drawString(msg, 6, 24);

        for (int y = 0; y < 32; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < 144; x++)
                sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
            if (sb.toString().trim().isEmpty()) continue;

            System.out.println(sb);
        }
    }

    public void showIntro() {
        displayAsciiArt(gameName);
        System.out.println("---\n" + gameDescription + " ... let's begin\n ---\n");
    }

    public void setDescription(String description) {
        this.gameDescription = description;
    }

    /**
     * Ensure that the game parameters are all set
     * @return true if valid, false otherwise
     */
    public boolean validate() {
        return (gameName!= null &&gameDescription != null && !goals.isEmpty() && player != null);
    }
}
