package edu.cmu.tartan.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;

import edu.cmu.tartan.GameInterface;

public class Commander {
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	/**
	 * Output buffers
	 */
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);

    /**
     * Input buffer
     */
    BufferedReader bfReader;

	final String newLine = System.getProperty("line.separator").toString();
	LinkedList<String> commandList = new LinkedList<>();

	public Commander() {
	}

	public void destory() {
		gameInterface.setGameManager(null);
		gameInterface.setSystemOut(System.out);
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
        gameInterface.setSystemOut(printStream);
	}

	public String getResultNextLine() {
		if (bfReader == null) {
			ByteArrayInputStream input = new ByteArrayInputStream(outputStream.toByteArray());
			bfReader = new BufferedReader(new InputStreamReader(input));
		}

		try {
			return bfReader.readLine();
		} catch (IOException e) {
			return "";
		}
	}
}
