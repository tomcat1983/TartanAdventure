package edu.cmu.tartan.action;

/**
 * This enumeration of actions available to a game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public enum Action {

    // Actions that do not rely on an object
    ACTION_LOOK(new String[]{"lookaround", "l"}, Type.TYPE_HASNOOBJECT),

    ACTION_JUMP(new String[]{"jump"}, Type.TYPE_HASNOOBJECT),
    ACTION_CLIMB(new String[]{"climb"}, Type.TYPE_HASNOOBJECT),
    ACTION_VIEW_ITEMS(new String[]{"inventory", "items", "i"}, Type.TYPE_HASNOOBJECT),
    ACTION_DIE(new String[]{"terminate"}, Type.TYPE_HASNOOBJECT),

    // Directional actions; for movement
    ACTION_GO(new String[]{"go","travel","move"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_EAST(new String[]{"east", "e"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_WEST(new String[]{"west", "w"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_SOUTH(new String[]{"south", "s"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_NORTH(new String[]{"north", "n"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_NORTHEAST(new String[]{"northeast", "ne"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_NORTHWEST(new String[]{"northwest", "nw"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_SOUTHEAST(new String[]{"southeast", "se"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_SOUTHWEST(new String[]{"southwest", "sw"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_DOWN(new String[]{"down", "d"}, Type.TYPE_DIRECTIONAL),
    ACTION_GO_UP(new String[]{"up", "u"}, Type.TYPE_DIRECTIONAL),

    // Direct Object. Has one direct object e.g. Break shovel, throw lamp
    ACTION_ACQUIRE(new String[]{"acquire"}, Type.TYPE_HASNOOBJECT),
    ACTION_BURN(new String[]{"burn"}, Type.TYPE_HASNOOBJECT),
    ACTION_DIG(new String[]{"dig"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_PICKUP(new String[]{"pickup", "get", "take", "acquire", "grab"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_DESTROY(new String[]{"break", "smash", "destroy", "obliterate"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_INSPECT(new String[]{"inspect", "examine", "read", "view"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_DROP(new String[]{"drop"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_THROW(new String[]{"throw", "chuck"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_SHAKE(new String[]{"shake", "chickendance"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_ENABLE(new String[]{"enable", "hit", "start", "use", "deploy"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_PUSH(new String[]{"push", "call"}, Type.TYPE_HASDIRECTOBJECT), // used with elevator
    ACTION_EAT(new String[]{"eat", "chew", "consume", "bite", "swallow", "drink"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_OPEN(new String[]{"open", "unlock"}, Type.TYPE_HASDIRECTOBJECT),
    ACTION_EXPLODE(new String[]{"detonate", "explode"}, Type.TYPE_HASDIRECTOBJECT),

    // Indirect Object. Has one direct object and one indirect object, e.g. Put cpu in computer
    ACTION_PUT(new String[]{"put", "install"}, Type.TYPE_HASINDIRECTOBJECT),
    ACTION_REMOVE(new String[]{"remove"}, Type.TYPE_HASINDIRECTOBJECT),

    // Misc
    ACTION_UNKNOWN(new String[]{}, Type.TYPE_UNKNOWN),
    ACTION_ERROR(new String[]{}, Type.TYPE_UNKNOWN),
    ACTION_PASS(new String[]{"pass", "\n"}, Type.TYPE_UNKNOWN);

    /**
     * Create an new action
     * @param aliases THe set of alias for the action
     * @param type the type of action
     */
    Action(String[] aliases, Type type) {
        this.aliases = aliases;
        this.type = type;
    }

    // Shortcuts to reverse movement
    static {
        ACTION_GO_EAST.opposite = ACTION_GO_WEST;
        ACTION_GO_WEST.opposite = ACTION_GO_EAST;
        ACTION_GO_NORTH.opposite = ACTION_GO_SOUTH;
        ACTION_GO_SOUTH.opposite = ACTION_GO_NORTH;
        ACTION_GO_NORTHEAST.opposite = ACTION_GO_SOUTHWEST;
        ACTION_GO_SOUTHEAST.opposite = ACTION_GO_NORTHWEST;
        ACTION_GO_NORTHWEST.opposite = ACTION_GO_SOUTHEAST;
        ACTION_GO_SOUTHWEST.opposite = ACTION_GO_NORTHEAST;
        ACTION_GO_UP.opposite = ACTION_GO_DOWN;
        ACTION_GO_DOWN.opposite = ACTION_GO_UP;
    }

    // Getters and Setters
    public String[] getAliases() {
        return this.aliases;
    }

    /**
     * @return
     */
    public Type type() {
        return this.type;
    }


    // opposite directions are used for the directional enumeration constants.
    public Action getOppositeDirection() {

        if (this.type() == Type.TYPE_DIRECTIONAL) {
            return this.opposite;
        } else {
            return null;
        }
    }

    /**
     * Fields to describe actions
     */
    private Action opposite;
    private String[] aliases;
    private Type type;
}