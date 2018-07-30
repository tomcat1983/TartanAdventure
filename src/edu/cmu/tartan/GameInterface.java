package edu.cmu.tartan;

import java.util.Scanner;

import edu.cmu.tartan.manager.TartanGameManager;

public class GameInterface {

	/**
	 * Static variable for singleton
	 */
	private static GameInterface instance = null;

    /**
     * Reads input from the command line.
     */
	private Scanner scanner;

    /**
     * Tartan Game manager for server.
     */
	private static TartanGameManager tartanManager;

	public GameInterface() {
        scanner = new Scanner(System.in, "UTF-8");
	}

	public static GameInterface getInterface() {
		if (instance == null) {
			instance = new GameInterface();
		}

		return instance;
	}

	public static boolean setGameManager(TartanGameManager manager) {
		tartanManager = manager;

		return true;
	}

	public void initialize() {
		instance = new GameInterface();
	}

	public void resetInterface() {
		scanner = new Scanner(System.in, "UTF-8");
	}

	// For game message
	public void print(String message) {
		System.out.print(message);
	}

	public void println(String message) {
		print(message + "\n");
	}

	public void print(String userId, String message) {
		if (tartanManager == null)
			print(message);
		else
			tartanManager.sendToAll(userId, message);
	}

	public void println(String userId, String message) {
		if (tartanManager == null)
			print(message + "\n");
		else
			tartanManager.sendToAll(userId, message + "\n");
	}

	public String getCommand() {
		return scanner.nextLine();
	}
}
