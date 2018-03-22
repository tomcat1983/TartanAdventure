package edu.cmu.tartan.room;

import edu.cmu.tartan.action.Action;

import java.util.LinkedList;

// represents a dark room
public class RoomDark extends Room {

	protected String darkDescription;
	protected String darkShortDescription;
	protected boolean isDark;
	protected String deathMessage;

	public RoomDark(String description, String shortDescription, String darkDescription, String darkShortDescription) {

		this(description, shortDescription, darkDescription, darkShortDescription, true);
	}
	public RoomDark(String description, String shortDescription, String darkDescription, String darkShortDescription, boolean isDark){
		super(description, shortDescription);

		this.isDark = isDark;
		this.darkDescription = darkDescription;
		this.darkShortDescription = darkShortDescription;
		this.deathMessage = null;
	}
	public boolean isDark() {
		return this.isDark;
	}
	public void setDark(boolean isDark) {
		this.isDark = isDark;
	}
	public String deathMessage() {
		return this.deathMessage;
	}	
	public void setDeathMessage(String s) {
		this.deathMessage = s;
	}

	public String toString() {

		if(this.isDark) {
			if(this.player.hasLuminousItem()) {
				return super.toString();
			}
			else {
				return this.darkDescription;
			}
		}
		else {
			return super.toString();
		}
	}
	public String description() {
		if (this.isDark) {
			if (this.player.hasLuminousItem()) {
				String s = this.roomWasVisited ? this.shortDescription : this.description + "\n" + visibleItems();
				this.roomWasVisited = true;
				return s;
			} else {
				String s = this.roomWasVisited ? this.darkShortDescription : this.darkDescription;
				this.roomWasVisited = true;
				return s;
			}
		} else {
			String s = this.roomWasVisited ? this.shortDescription : this.description + "\n" + visibleItems();
			this.roomWasVisited = true;
			return s;
		}
	}
}
