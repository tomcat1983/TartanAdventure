package edu.cmu.tartan.action;

/**
 * Enumeration of action types.
 */
public enum Type {
    /**
     * Directional actions move the player through the game.
     */
	TYPE_DIRECTIONAL,

    /**
     * Some actions require direct objects to perform
     */
    TYPE_HASDIRECTOBJECT,

    /**
     * Indirect objects are things that other objects need/use
     */
    TYPE_HASINDIRECTOBJECT,

    /**
     * No object and unknown are self-explanatory
     */
    TYPE_HASNOOBJECT,
	TYPE_UNKNOWN
}
