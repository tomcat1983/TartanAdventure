package edu.cmu.tartan;

import java.util.Scanner;

public class GameInterface {

	/**
	 * Static variable for singleton
	 */
	private static GameInterface instance = null;

    /**
     * Reads input from the command line.
     */
    private Scanner scanner;

	public GameInterface() {
        scanner = new Scanner(System.in, "UTF-8");
	}
	
	public static GameInterface getInterface() {
		if (instance == null) {
			instance = new GameInterface();
		}
		
		return instance;
	}
	
	public void resetInterface() {
		scanner = new Scanner(System.in, "UTF-8");
	}
	
	// For game message
	public void print(String msg) {
		System.out.print(msg);
	}
	
	public void println(String msg) {
		print(msg + "\n");
	}
	
	public String getCommand() {
		return scanner.nextLine();
	}
}
