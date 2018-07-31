package edu.cmu.tartan.server;

import edu.cmu.tartan.GameInterface;

public class ServerInterface {

	/**
	 * Game interface for message
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	public enum Command {
		UNDECIDED, EXIT
	}

	public ServerInterface() {
	}

	public Command getCommand() {
		Command serverCommand = Command.UNDECIDED;

		do {
			gameInterface.println("");
			gameInterface.print("> ");

			String command = gameInterface.getCommand(GameInterface.USER_ID_NONE);

			if (command.equals("exit")) {
				serverCommand = Command.EXIT;
			}
		} while(serverCommand == Command.UNDECIDED);

		return serverCommand;
	}
}
