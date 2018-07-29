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
		UNDECIDED, LOCAL, NETWORK, DESIGNER, EXIT
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
		gameInterface.println("> help    What are local mode and network mode?");
		gameInterface.println("> exit    The game will be terminated.");

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
			case "exit":
				gameMode = RunningMode.EXIT;
				break;
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
		UNDECIDED, CONTINUE, NEW, QUIT
	}

	public boolean printLocalModeMessage() {
		gameInterface.println("[Local mode]");
		gameInterface.println("");
		gameInterface.println("Let's play :");
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
			} else
			if (command.equals("quit")) {
				localCommand = LocalModeCommand.QUIT;
			}
		} while(localCommand == LocalModeCommand.UNDECIDED);
		
		return localCommand;
	}

	/**
	 * Local mode command
	 */
	public enum NetworkModeCommand {
		UNDECIDED, LOGIN, REGISTER, QUIT
	}

	public NetworkModeCommand getNetworkModeCommand() {
		NetworkModeCommand networkCommand = NetworkModeCommand.UNDECIDED;
		
		gameInterface.println("Log in");
		gameInterface.println("");
		gameInterface.println("> login      Log in and play game.");
		gameInterface.println("> register   Register an account for playing game");
	
		do {
			gameInterface.println("");
			gameInterface.println("Please select the menu");
			gameInterface.print("> ");
			
			String command = gameInterface.getCommand();
			
			if (command.equals("login")) {
				networkCommand = NetworkModeCommand.LOGIN;
			} else
			if (command.equals("register")) {
				networkCommand = NetworkModeCommand.REGISTER;
			} else
			if (command.equals("quit")) {
				networkCommand = NetworkModeCommand.QUIT;
			}
		} while(networkCommand == NetworkModeCommand.UNDECIDED);
		
		return networkCommand;
	}
	
	public String[] getLoginInfo() {
		String[] loginInfo = new String[2];
		
		gameInterface.println("Please enter your ID :");
		gameInterface.print("> ");
		
		loginInfo[0] = gameInterface.getCommand();

		gameInterface.println("Please enter your Password :");
		gameInterface.print("> ");
		
		loginInfo[1] = gameInterface.getCommand();

		return loginInfo;
	}
	
	public boolean printWelcomMessage(boolean isDesigner) {
		if (isDesigner) {
			gameInterface.println("");
			gameInterface.println("Login as designer is succeeded.");
			gameInterface.println("");
			gameInterface.println("* Map upload guide *");
			gameInterface.println("1) Save the file you want to upload in the folder where the game execution file is saved.");
			gameInterface.println("2) Enter the file name you want to upload.");
			gameInterface.println("");
		} else {
			gameInterface.println("");
			gameInterface.println("Welcome to Room Escape Network mode!");
			gameInterface.println("");
		}
		return true;
	}
	
	public String getUserIdForRegister() {
		gameInterface.println("");
		gameInterface.println("Please enter the ID you want to use :");
		gameInterface.println("/Rule of ID/");
		gameInterface.print("> ");
		
		String id = gameInterface.getCommand();

		return id;
	}
	
	public boolean printInvalidIdMessageForRegister() {
		gameInterface.println("");
		gameInterface.println("The ID you entered is invalid.");
		gameInterface.println("Please enter valid ID.");

		return true;
	}
	
	public String getUserPwForRegister() {
		gameInterface.println("");
		gameInterface.println("Please enter the password you want.");
		gameInterface.println("/Rule for password/");
		gameInterface.print("> ");
		
		String pw = gameInterface.getCommand();

		return pw;
	}

	public boolean printInvalidPwMessageForRegister() {
		gameInterface.println("");
		gameInterface.println("The password you entered is invalid.");
		gameInterface.println("Please enter valid password.");

		return true;
	}

	public String getUserMatchPwForRegister() {
		gameInterface.println("");
		gameInterface.println("Re-enter the password to confirm.");
		gameInterface.print("> ");
		
		String pw = gameInterface.getCommand();

		return pw;
	}

	public boolean printUnmatchPwMessageForRegister() {
		gameInterface.println("");
		gameInterface.println("The password is different with the previous one.");

		return true;
	}
	
	public boolean printSuccessMessageForRegister() {
		gameInterface.println("");
		gameInterface.println("Your account is registered successfully.");
		gameInterface.println("You will go to the game mode selection menu.");

		return true;
	}

	public boolean printFailMessageForRegister() {
		gameInterface.println("");
		gameInterface.println("I¡¯m sorry. The ID is already occupied. Please retry registration.");

		return true;
	}
	
	String getNetworkCommand() {
		String command = "";
		
		do {
			gameInterface.println("");
			gameInterface.print("> ");
	
			command = gameInterface.getCommand();
		} while (command.equals(""));
		
		return command;
	}
	
	public boolean printInvalidMap() {
		gameInterface.println("There is no file which of the name is what you entered.");
		gameInterface.println("Please check whether the file is in the folder which the game is saved or not.");
		gameInterface.println("");
		gameInterface.println("Re-enter the file name.");
		
		return true;
	}
}
