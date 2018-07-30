package edu.cmu.tartan;

import java.util.ArrayList;
import java.util.List;
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

	/**
	 * Command List
	 */
	List<String> commandList = new ArrayList<>();

	/**
	 * Check command list empty
	 */
	boolean wait = false;

	public GameInterface() {
        scanner = new Scanner(System.in, "UTF-8");
        init();
	}

	private synchronized void init() {
		tartanManager = null;
		commandList.clear();
		wait = false;
	}

	public static GameInterface getInterface() {
		if (instance == null) {
			instance = new GameInterface();
		}

		return instance;
	}

	public boolean setGameManager(TartanGameManager manager) {
		tartanManager = manager;

		return true;
	}

	public void initialize() {
		instance = new GameInterface();
	}

	public void resetInterface() {
		scanner = new Scanner(System.in, "UTF-8");
	}

	public enum MessageType {
		PRIVATE, PUBLIC, WIN, LOSE
	}

	// For game message
	public void print(String message) {
		System.out.print(message);
	}

	public void println(String message) {
		print(message + "\n");
	}

	public void print(String userId, MessageType type, String message) {
		if (tartanManager == null)
			print(message);
		else if (type == MessageType.PRIVATE)
			tartanManager.sendToClient(userId, message);
		else if (type == MessageType.PUBLIC)
			tartanManager.sendToAll(userId, message);
		else
			tartanManager.achievedGoal(userId);
	}

	public void println(String userId, MessageType type, String message) {
		message = message + "\n";

		if (tartanManager == null)
			print(message);
		else if (type == MessageType.PRIVATE)
			tartanManager.sendToClient(userId, message);
		else if (type == MessageType.PUBLIC)
			tartanManager.sendToAll(userId, message);
		else
			tartanManager.achievedGoal(userId);
	}

	public synchronized String getCommand() {
		if (tartanManager == null) {
			return scanner.nextLine();
		} else {
			if (commandList.isEmpty()) {
				wait = true;

				try {
					commandList.wait();
				} catch (Exception e) {
				}
			}

			String command = commandList.get(0);
			commandList.remove(0);
			return command;
		}
	}

	public synchronized boolean putCommand(String command) {
		boolean result = commandList.add(command);

		if (wait)
			commandList.notify();

		return result;
	}
}
