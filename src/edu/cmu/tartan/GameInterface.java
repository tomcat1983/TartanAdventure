package edu.cmu.tartan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	List<String> commandList1 = new ArrayList<>();

	private static Map<String, LinkedList<String>> commandMap = new HashMap<>();
	private static Map<String, Boolean> waitMap = new HashMap<>();

	/**
	 * Check command list empty
	 */
	boolean wait1 = false;

	public GameInterface() {
        scanner = new Scanner(System.in, "UTF-8");
        init();
	}

	private synchronized void init() {
		tartanManager = null;
		commandList1.clear();
		wait1 = false;
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

	public synchronized String getCommand(String userId) {
		if (tartanManager == null) {
			return scanner.nextLine();
		} else {
			LinkedList<String> commandList = commandMap.get(userId);
			if (commandList == null) {
				System.out.println("new1");
				commandList = new LinkedList<>();
				commandMap.put(userId, commandList);
				waitMap.put(userId, false);
			}

			if (commandList.isEmpty()) {
				waitMap.replace(userId, false, true);

				try {
					commandList.wait();
				} catch (Exception e) {
				}
			}

			return commandList.poll();
		}
	}

	public synchronized boolean putCommand(String userId, String command) {
		LinkedList<String> commandQueue = commandMap.get(userId);
		if (commandQueue == null) {
			System.out.println("new");
			commandQueue = new LinkedList<>();
			commandMap.put(userId, commandQueue);
			waitMap.put(userId, false);
		}

		boolean result = commandQueue.offer(command);

		if (waitMap.get(userId)) {
			commandQueue.notifyAll();
			waitMap.replace(userId, true, false);
		}

		return result;
	}
}
