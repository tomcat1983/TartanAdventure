package edu.cmu.tartan.server;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.Player;

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

			String command = gameInterface.getCommand(Player.DEFAULT_USER_NAME);

			if (command.equals("exit")) {
				serverCommand = Command.EXIT;
			}
		} while(serverCommand == Command.UNDECIDED);

		return serverCommand;
	}
}
