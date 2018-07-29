package edu.cmu.tartan.server;

import edu.cmu.tartan.GameInterface;

public class ServerInterface {

	/**
	 * Game interface for message
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	public enum Command {
		UNDECIDED, QUIT
	}

	public ServerInterface() {
	}

	public Command getCommand() {
		Command serverCommand = Command.UNDECIDED;

		do {
			gameInterface.println("");
			gameInterface.print("> ");

			String command = gameInterface.getCommand();

			if (command.equals("quit")) {
				serverCommand = Command.QUIT;
			}
		} while(serverCommand == Command.UNDECIDED);

		return serverCommand;
	}
}
