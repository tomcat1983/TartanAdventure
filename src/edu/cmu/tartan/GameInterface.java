package edu.cmu.tartan;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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

	final static PipedOutputStream pipedOut = new PipedOutputStream();
	final static PipedInputStream pipedIn = new PipedInputStream();

	public GameInterface() {
        scanner = new Scanner(System.in, "UTF-8");
        try {
        	pipedOut.connect(pipedIn);
        } catch (IOException e) {
        }
	}

	public static GameInterface getInterface() {
		if (instance == null) {
			instance = new GameInterface();
		}

		return instance;
	}

	public boolean setGameManager(TartanGameManager manager) {
		tartanManager = manager;
		scanner = new Scanner(pipedIn, "UTF-8");

		return true;
	}

	public void initialize() {
		instance = new GameInterface();
	}

	public void resetInterface() {
		scanner = new Scanner(System.in, "UTF-8");
	}

	public enum MessageType {
		PRIVATE, PUBLIC, WIN
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
			tartanManager.sendToAll(message);
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
			tartanManager.sendToAll(message);
		else
			tartanManager.achievedGoal(userId);
	}

	public String getCommand() {
		return scanner.nextLine();
	}

	public boolean putCommand(String command) {
		try {
			pipedOut.write(command.getBytes());
		} catch (IOException e) {
		}

		return true;
	}
}
