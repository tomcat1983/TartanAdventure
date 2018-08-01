package edu.cmu.tartan;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import edu.cmu.tartan.manager.TartanGameManager;

public class GameInterface {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	/**
	 * User ID for LOCAL_USER
	 */
	public static final String USER_ID_LOCAL_USER = "_LOCAL_USER_";

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
	private TartanGameManager tartanManager;

	/**
	 * Command list map
	 */
	private Map<String, LinkedList<String>> commandMap = new HashMap<>();

	/**
	 * Wait map
	 */
	private Map<String, Boolean> waitMap = new HashMap<>();

	/**
	 * Output stream
	 */
	private PrintStream systemOut = System.out;

	public GameInterface() {
        scanner = new Scanner(System.in, "UTF-8");
	}

	public static GameInterface getInterface() {
		if (instance == null) {
			instance = new GameInterface();
		}

		return instance;
	}

	public void setGameManager(TartanGameManager manager) {
		tartanManager = manager;
	}

	public void setSystemOut(PrintStream printStream) {
		systemOut = printStream;
	}

	public void resetInterface() {
		scanner = new Scanner(System.in, "UTF-8");
		commandMap.clear();
	}

	public enum MessageType {
		SYSTEM, PRIVATE, PUBLIC, OTHER, WIN, LOSE
	}

	// For game message
	public void print(String message) {
		print(USER_ID_LOCAL_USER, MessageType.SYSTEM, message);
	}

	public void println(String message) {
		print(USER_ID_LOCAL_USER, MessageType.SYSTEM, message + "\n");
	}

	public void print(String userId, MessageType type, String message) {
		if (tartanManager == null) {
			if (type == MessageType.OTHER) {
				return;
			}

			type = MessageType.SYSTEM;
		}

		switch (type) {
		case SYSTEM:
			systemOut.print(message);
			break;
		case PRIVATE:
			tartanManager.sendToClient(userId, message);
			break;
		case PUBLIC:
			tartanManager.sendToAll(userId, message);
			break;
		case OTHER:
			tartanManager.sendToOthers(userId, message);
			break;
		case WIN:
			tartanManager.winTheGame(userId, message);
			break;
		case LOSE:
			tartanManager.loseTheGame(userId, message);
			break;
		default:
			break;
		}
	}

	public void println(String userId, MessageType type, String message) {
		print (userId, type, message + "\n");
	}

	public String getCommand(String userId) {
		LinkedList<String> commandQueue = getCommandQueue(userId);

		if ((tartanManager == null || userId.equals(USER_ID_LOCAL_USER))
			&& (!userId.equals(USER_ID_LOCAL_USER) || commandQueue.isEmpty())) {
				putCommand(userId, scanner.nextLine());
		}

		while (commandQueue.isEmpty()) {
			synchronized(commandQueue) {
				waitMap.replace(userId, false, true);

				try {
					commandQueue.wait();
				} catch (Exception e) {
					gameLogger.info(e.getMessage());
				}
			}
		}

		return commandQueue.poll();
	}

	public boolean putCommand(String userId, String command) {
		LinkedList<String> commandQueue = getCommandQueue(userId);

		boolean result = commandQueue.offer(command);

		synchronized(commandQueue) {
			if (waitMap.get(userId)) {
				commandQueue.notifyAll();
				waitMap.replace(userId, true, false);
			}
		}

		return result;
	}

	private LinkedList<String> getCommandQueue(String userId) {
		return commandMap.computeIfAbsent(userId, k -> {
			LinkedList<String> list = new LinkedList<>();
			commandMap.put(userId, list);
			synchronized(waitMap) {
				waitMap.put(userId, false);
			}

			return list;
		});
	}
}
