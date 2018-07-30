package edu.cmu.tartan.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;

import edu.cmu.tartan.GameInterface;

public class Commander {
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	final String newLine = System.getProperty("line.separator").toString();
	LinkedList<String> commandList = new LinkedList<>();

	public Commander() {
	}

	public void add(String command) {
		commandList.offer(command);
	}

	public void apply() {
		String commandString = "";

		while (!commandList.isEmpty()) {
			commandString += commandList.poll() + newLine;
		}

		InputStream in = new ByteArrayInputStream(commandString.getBytes());
		System.setIn(in);

		gameInterface.resetInterface();
	}
}
