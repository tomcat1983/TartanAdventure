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

	/**
	 *
	 */
	public ServerInterface() {
		// Do nothing because there is no member variable.
	}

	/**
	 * @return
	 */
	public Command getCommand() {
		Command serverCommand = Command.UNDECIDED;

		do {
			gameInterface.println("");
			gameInterface.print("> ");

			String command = gameInterface.getCommand(GameInterface.USER_ID_LOCAL_USER);

			if (command.equals("exit")) {
				serverCommand = Command.EXIT;
			} else {
				gameInterface.println("Invalid command : " + command);
			}
		} while(serverCommand == Command.UNDECIDED);

		return serverCommand;
	}
}
