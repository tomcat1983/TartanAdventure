package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.room.*;

import java.util.HashMap;
import java.util.Vector;

public class Player {

    private Vector<Room> roomsVisited = new Vector<>();

    public int getScore() {
        return score;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    private int score=0;

    private int possiblePoints=0;

    private Vector<Item> items = new Vector<>();

    private Vector<GameGoal> goals = new Vector<>();

    private Room currentRoom = null;

    public Player(Room currentRoom) {
        this(currentRoom, new Vector<Item>());
    }

    public Player(Room currentRoom, Vector<Item> items) {
        this.items = items;
        this.score = 0;

        //this.currentRoom = currentRoom;
        this.currentRoom = currentRoom;
        this.currentRoom.player = this;
    }

    public Item drop(Item item) {
        if(this.items.remove(item)) {
            return item;
        }
        else {
            return null;
        }
    }

    public boolean dropItem(Item item) {

        Item dropped = drop(item);
        if (dropped == null) {
            System.out.println("You don't have this item to drop");
            return false;
        }
        this.currentRoom.putItem(dropped);
        return true;
    }

    public boolean pickup(Item item){

        this.pick(item);
        return true;
    }

    public void pick(Item item) {
        this.items.add(item);
    }

    public boolean hasItem(Item item) {
        if(item == null) return false;
        return this.items.contains(item);
    }

    public boolean hasLuminousItem() {
        for (Item item : this.items) {
            if (item instanceof Luminous) {
                return true;
            }
        }
        return false;
    }
    public Vector<Item> getCollectedItems() {
        return this.items;
    }

    public void putItemInItem(Item direct, Item indirect) {
        ((Hostable)indirect).install(direct);
        if(indirect instanceof ItemMagicBox && direct instanceof Valuable) {
            score((Valuable)direct);
        }
    }

    public void move(Room nextRoom) {

        nextRoom.setPlayer(this);
        if(this.currentRoom != null && nextRoom.compareTo(this.currentRoom) != 0) {
            Action directionOfTravel = this.currentRoom.getDirectionForRoom(nextRoom);
            HashMap<Action, String> messages = this.currentRoom.transitionMessages();
            String message = messages.get(directionOfTravel);
            int delay = this.currentRoom.transitionDelay();
            if(message != null) {
                if(delay != 0) {
                    for(int i=0; i < 3; i++) {
                        System.out.println("...");
                        try{
                            Thread.sleep(delay);
                        }
                        catch(Exception e1) {
                            // pass
                        }
                    }
                }
                System.out.println(message);
            }
        }
        if(nextRoom instanceof RoomRequiredItem) {
            RoomRequiredItem r = (RoomRequiredItem)nextRoom;
            if(r.diesOnEntry()) {
                System.out.println(r.deathMessage());
                this.die();
            }
        }

        this.currentRoom = nextRoom;
        saveRoom(currentRoom);
        System.out.println(this.currentRoom.description());
    }

    private void saveRoom(Room room) {
        roomsVisited.add(room);
    }

    public Vector<Room> getRoomsVisited() {
        return roomsVisited;
    }

    public void move(Action a) {

        if(this.currentRoom instanceof RoomRequiredItem) {
            RoomRequiredItem room = (RoomRequiredItem)this.currentRoom;

            if(room.shouldDieForAction(a)) {
                System.out.println(room.deathMessage());
                this.die();
            }
        }
        else if(this.currentRoom instanceof RoomDark) {
            RoomDark room = (RoomDark)this.currentRoom;
            if(room.isDark() && !this.hasLuminousItem()) {
                System.out.println(room.deathMessage());
                this.die();
            }
        }

        if(this.currentRoom.canMoveToRoomInDirection(a)) {
            Room nextRoom = this.currentRoom.getRoomForDirection(a);
            // test if requires key
            if(nextRoom instanceof RoomLockable) {
                RoomLockable lockedRoom = (RoomLockable)nextRoom;
                if(lockedRoom.isLocked()) {
                    if(lockedRoom.causesDeath()) {
                        System.out.println(lockedRoom.deathMessage());
                        this.die();
                    }
                    System.out.println("This door is locked.");
                    return;
                }
            }
            else if(nextRoom instanceof RoomObscured) {
                RoomObscured obscuredRoom = (RoomObscured)nextRoom;
                if(obscuredRoom.isObscured()) {
                    System.out.println("You can't move that way.");
                    return;
                }
            }

            move(nextRoom);
        }
        else {
            System.out.println("You can't move that way.");
        }
    }

    public Room currentRoom() {
        return this.currentRoom;
    }

    public void addGoal(GameGoal g) {
        goals.add(g);
    }

    public void lookAround() {
        System.out.println(this.currentRoom.toString());
    }

    public void score(Valuable object) {
        int score = object.value();
        score(score);
    }
    public void score(int s) {
        System.out.println("You scored " + s + " points.");
        score += s;
    }
    public void die() {
        System.out.println("You scored " + this.score + " out of  " + possiblePoints + "possible points. You are dead.");
        System.exit(0);
    }

    public void addPossiblePoints(int p) {
        possiblePoints += p;
    }

    public int getPossiblePoints() {
        return possiblePoints;
    }

    public Vector<GameGoal> getGoals() {
        return goals;
    }
}
