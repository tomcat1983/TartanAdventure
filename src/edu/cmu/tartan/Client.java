package edu.cmu.tartan;

public class Client {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	public Client(String ip, String port) {
		gameInterface.info("Run Client : " + ip + ":" + port);
	}
	
	public boolean start() {
		int gameMode = getGameMode();
		return true;
	}
	
	private int getGameMode() {
		int gameMode = 0;
		
		gameInterface.println("[Tartan Adventure]");
		gameInterface.println("");
		gameInterface.println("Room Escape");
		gameInterface.println("");
		gameInterface.println("Select the game mode");
		gameInterface.println("> 1       Local mode");
		gameInterface.println("> 2       Network mode");
		gameInterface.println("> help  What are local mode and network mode?");
		gameInterface.println("");
		gameInterface.println("Choose the mode");
		
		do {
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
			case "2":
			case "99":
				gameMode = Integer.parseInt(command);
				break;
			default:
				gameInterface.println("Invalid command : " + command);
				break;
			}
		} while (gameMode == 0);
		
		return gameMode;
	}

}
