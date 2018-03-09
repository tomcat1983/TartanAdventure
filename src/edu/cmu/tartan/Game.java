package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.action.Type;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.item.ItemWatchMenu;
import edu.cmu.tartan.properties.*;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomElevator;
import edu.cmu.tartan.room.RoomExcavatable;
import edu.cmu.tartan.room.RoomRequiredItem;

import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class Game {

    protected Scanner scanner;
    protected PlayerInterpreter interpreter;
    protected Player player;
    private File savedGameFile = null;
    private String gameName = "";
    private Vector<GameGoal> goals = new Vector<>();

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

    private void configureGame() {

        ItemWatchMenu menu = new ItemWatchMenu("Choose a game from the options to below: \n");

        ItemWatchMenu m1 = new ItemWatchMenu("Collect");
//        ItemWatchMenu m2 = new ItemWatchMenu("NJIT");
        ItemWatchMenu m3 = new ItemWatchMenu("Points");
        ItemWatchMenu m4 = new ItemWatchMenu("Explore");

        menu.add(m1);
        menu.add(m3);
        menu.add(m4);

        System.out.println(menu.toString());

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

            switch(choice) {
                case 0:
                    gameName = "Collect";
                    Map.collectGame(this);
                    return;
                case 1:
                    gameName = "Points";
                    Map.pointsGame( this );
                    return;
                case 2:
                    gameName = "Explore";
                    Map.exploreGame(this);
                    return;
                default:
                    System.out.println("Unknown game.");
            }
        }
    }

    private void executeAction(Action a) {

        switch(a.type()) {

            case TYPE_DIRECTIONAL:
                move(a);
                break;
            case TYPE_HASDIRECTOBJECT:
                Item val = a.directObject();
                if (val instanceof Valuable) {
                    player.score((Valuable) val);
                }
                switch(a) {
                    case ActionPickUp: {
                        Item o = a.directObject();
                        Item container = null;
                        if(this.player.currentRoom().hasItem(o)) {
                            if(o instanceof Holdable) {
                                System.out.println("Taken.");
                                this.player.currentRoom().remove(o);
                                this.player.pickup(o);

                            }
                            else {
                                System.out.println("You cannot pick up this item.");
                            }
                        }
                        else if((container = containerForItem(o)) != null) {

                            System.out.println("Taken.");
                            ((Hostable)container).uninstall(o);
                            this.player.pickup(o);
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
                                ((Pushable)item).push();
                                if(item.relatedRoom() instanceof RoomElevator) { // player is next to an elevator
                                    ((RoomElevator)item.relatedRoom()).call(this.player.currentRoom());
                                }
                                else if(this.player.currentRoom() instanceof RoomElevator) { // player is in an elevator
                                    ((RoomElevator)this.player.currentRoom()).call(Integer.parseInt(item.getAliases()[0])-1);
                                }
                                else {
                                    // for teleportation machine
                                    this.player.move(item.relatedRoom());
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
                                ((Edible)item).eat();
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
                    case ActionWear: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item)) {
                            if(item instanceof Wearable) {
                                System.out.println("Worn.");
                                this.player.currentRoom().remove(item);
                                this.player.wearDisguise(item);
                            }
                            else {
                                System.out.println("You can not wear this item.");
                            }
                        }
                        else if(this.player.disguise() == item) {
                            System.out.println("You are already wearing that.");
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;
                    }
                    case ActionKill: {
                        Item item = a.directObject();
                        if(this.player.currentRoom().hasItem(item)) {
                            if(item instanceof Killable) {
                                ((Killable)item).kill();
                            }
                            else {
                                System.out.println("You cannot kill this.");
                            }
                        }
                        else {
                            System.out.println("I don't see that here.");
                        }
                        break;
                    }
                    case ActionOpen: {
                        Item item = a.directObject();
                        if(this.player.hasItem(item) || this.player.currentRoom().hasItem(item)) {
                            if(item instanceof Openable) {
                                Openable o = ((Openable)item);
                                player.score(o.value());
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
                                    ((Explodable)dynamite).explode();
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
                        if(this.player.disguise() != null) {
                            System.out.println("You are wearing a " + this.player.disguise() + ".");
                        }
                        if (items.size() == 0) {
                            System.out.println("You don't have any items.");
                        }
                        else {
                            for(Item item : this.player.getCollectedItems()) {
                                System.out.println("You have a " + item.description() + ".");
                            }
                        }
                        break;
                    case ActionSuicide:
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

        System.out.println("Game Over :)");
    }

    private void winGame() {
        System.out.println("You've won the '" + gameName + "' game!" );
        System.out.println("---\nFinal score: " + player.getScore());

        System.out.println("---\nFinal inventory: ");
        if (player.getCollectedItems().size() == 0) {
            System.out.println("You don't have any items.");
        } else {
            for (Item i : player.getCollectedItems()) {
                System.out.println(i.toString() + " ");
            }
        }
    }

    private Boolean evaluateGame() {
        Vector<GameGoal> goals = player.getGoals();
        for (GameGoal g : goals) {
            // Now assess the goal
            if (g.isAchieved()) {
                goals.remove(g);
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

        System.out.println(" -- TartanAdvaenture RPG Help Menu -- ");
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
    }

    public void addGoal(GameGoal g) {
       goals.add(g);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
