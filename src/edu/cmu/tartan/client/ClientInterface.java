package edu.cmu.tartan.client;

import edu.cmu.tartan.GameInterface;

public class ClientInterface {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	/**
	 * Game running mode
	 */
	public enum RunningMode {
		UNDECIDED, LOCAL, NETWORK, DESIGNER
	}

	/**
	 * Print game mode message
	 */
	public boolean printGameModeMessage() {
		gameInterface.println("[Tartan Adventure]");
		gameInterface.println("");
		gameInterface.println("Room Escape");
		gameInterface.println("");
		gameInterface.println("Select the game mode");
		gameInterface.println("> 1       Local mode");
		gameInterface.println("> 2       Network mode");
		gameInterface.println("> help  What are local mode and network mode?");

		return true;
	}
	
	public RunningMode getRunningMode() {
		RunningMode gameMode = RunningMode.UNDECIDED;
		
		do {
			gameInterface.println("");
			gameInterface.println("Choose the mode");
			gameInterface.print("> ");

			String command = gameInterface.getCommand();
			
			switch (command) {
			case "help":
				gameInterface.println("In local mode, you can enjoy the game without internet connection.");
				gameInterface.println("You can save the game state during the game and load the game states saved latest.");
				gameInterface.println("");
				gameInterface.println("In network mode, you can enjoy the game with other players at the same time through internet connection.");
				break;
			case "1":
				gameMode = RunningMode.LOCAL;
				break;
			case "2":
				gameMode = RunningMode.NETWORK;
				break;
			case "99":
				gameMode = RunningMode.DESIGNER;
				break;
			default:
				gameInterface.println("Invalid command : " + command);
				break;
			}
		} while (gameMode == RunningMode.UNDECIDED);
		
		return gameMode;
	}

	/**
	 * Local mode command
	 */
	public enum LocalModeCommand {
		UNDECIDED, CONTINUE, NEW
	}

	public boolean printLocalModeMessage() {
		gameInterface.println("[Local mode]");
		gameInterface.println("");
		gameInterface.println("Let¡¯s play :");
		gameInterface.println("> continue    Play the latest saved game continuously");
		gameInterface.println("> new         Play a new game.");
		gameInterface.println("");

		return true;
	}
	
	public LocalModeCommand getLocalModeCommand() {
		LocalModeCommand localCommand = LocalModeCommand.UNDECIDED;
		
		do {
			gameInterface.println("");
			gameInterface.println("Please select the game play mode.");
			gameInterface.print("> ");
			
			String command = gameInterface.getCommand();
			
			if (command.equals("continue")) {
				localCommand = LocalModeCommand.CONTINUE;
			} else
			if (command.equals("new")) {
				localCommand = LocalModeCommand.NEW;
			}
		} while(localCommand == LocalModeCommand.UNDECIDED);
		
		return localCommand;
	}

}
