package edu.cmu.tartan.client;

import java.io.File;
import java.util.logging.Logger;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.LocalGame;
import edu.cmu.tartan.manager.TartanGameManagerClient;
import edu.cmu.tartan.xml.GameMode;
import edu.cmu.tartan.xml.XmlLoginRole;

public class Client {

	/**
	 * Game logger for game log
	 */
	private Logger logger = Logger.getGlobal();

	/**
	 * Client interface instance
	 */
	private ClientInterface clientInterface;

	/**
	 * Client game manager
	 */
	private TartanGameManagerClient gameManager;

	/**
	 * User ID
	 */
	private String userId;

	public Client() {
		clientInterface = new ClientInterface();
	}

	/**
	 * @return
	 */
	public boolean start() {
		boolean running = true;

		do {
			clientInterface.printGameModeMessage();

			switch (clientInterface.getRunningMode()) {
			case LOCAL:
				runLocalMode();
				break;
			case NETWORK:
				runNetworkMode(false);
				break;
			case DESIGNER:
				runNetworkMode(true);
				break;
			case EXIT:
				running = false;
				break;
			default:
				logger.severe("Unknown running mode");
				return false;
			}
		} while (running);

		return true;
	}

	private boolean runLocalMode() {
		clientInterface.printLocalModeMessage();
		ClientInterface.LocalModeCommand command = clientInterface.getLocalModeCommand();

		switch (command) {
		case CONTINUE:
			return continueGame();
		case NEW:
			return newGame();
		case QUIT:
			return true;
		default:
			logger.severe("Unknow local mode command");
			break;
		}

		return false;
	}

	private boolean continueGame() {
		LocalGame localGame = new LocalGame(GameInterface.USER_ID_LOCAL_USER);
		if (!localGame.loadAndStart(GameInterface.USER_ID_LOCAL_USER, GameInterface.SAVE_FILE_NAME)) {
			clientInterface.printLoadFailMessage();
			return false;
		}

		return true;
	}

	private boolean newGame() {
		LocalGame localGame = new LocalGame(GameInterface.USER_ID_LOCAL_USER);
		if(localGame.configureGame(GameMode.LOCAL)) {
			localGame.start();
		}

		return true;
	}

	private boolean connectServer(boolean isDesigner) {

		gameManager = new TartanGameManagerClient(isDesigner);
		Thread gameManagerThread = new Thread(gameManager);
		gameManagerThread.start();

		return true;
	}

	private boolean login(boolean isDesigner) {
		String id = null;

		String[] loginInfo = clientInterface.getLoginInfo();
		id = loginInfo[0];

		XmlLoginRole role = XmlLoginRole.PLAYER;
		if (isDesigner)
			role = XmlLoginRole.DESIGNER;

		if (gameManager.login("", loginInfo[0], loginInfo[1], role)) {
			userId = id;
			return true;
		}

		return false;
	}

	private String getUserIdForRegister() {
		String id = "";

		do {
			id = clientInterface.getUserIdForRegister();
			if (gameManager.validateUserId(id))
				break;
			else
				clientInterface.printInvalidIdMessageForRegister();
		} while(true);

		return id;
	}

	private String getUserPwForRegister() {
		String pw = "";
		String confirmPw = "";

		do {
			do {
				pw = clientInterface.getUserPwForRegister();
				if (gameManager.validateUserPw(pw))
					break;
				else
					clientInterface.printInvalidPwMessageForRegister();
			} while(true);

			confirmPw = clientInterface.getUserMatchPwForRegister();
			if (confirmPw.equals(pw))
				break;
			else
				clientInterface.printUnmatchPwMessageForRegister();
		} while(true);

		return pw;
	}

	private boolean register() {
		String id = getUserIdForRegister();
		String pw = getUserPwForRegister();

		if (gameManager.register("threadName", id, pw)) {
			clientInterface.printSuccessMessageForRegister();
			return true;
		}

		clientInterface.printFailMessageForRegister();

		return false;
	}

	private boolean runNetworkCommander(boolean isDesigner) {
		boolean running = true;

		do {
			String command = clientInterface.getNetworkCommand();

			if (command.equals("quit")) {
				clientInterface.printByeMessage();
				running = false;
			} else if (isDesigner) {
				uploadMap(command);
			} else if (command.equals("start")) {
				if (!gameManager.startGame(userId)) {
					clientInterface.printCannotStartMessage();
				}
			} else {
				gameManager.updateGameState(userId, command);
			}
		} while (running);

		return true;
	}

	private boolean uploadMap(String fileName) {
		File file = new File(fileName);

		if (file.exists()) {
			clientInterface.printValidateMessageForMapUpload();

			if (gameManager.uploadMap(fileName)) {
				clientInterface.printValidateSucceessMessageForMapUpload();
				return true;
			} else {
				clientInterface.printValidateFailMessageForMapUpload();
			}
		} else {
			clientInterface.printNoMap();
		}

		return false;
	}

	private boolean runNetworkMode(boolean isDesigner) {
		connectServer(isDesigner);

		if (gameManager.waitForConnection()) {
			boolean runNetwork = true;
			boolean result = true;

			do {
				ClientInterface.NetworkModeCommand networkCommand = ClientInterface.NetworkModeCommand.LOGIN;

				if (!isDesigner)
					networkCommand = clientInterface.getNetworkModeCommand();

				switch (networkCommand) {
				case LOGIN:
					if (login(isDesigner)) {
						clientInterface.printWelcomMessage(isDesigner);
						runNetworkCommander(isDesigner);
						runNetwork = false;
					}
					break;
				case REGISTER:
					register();
					break;
				case QUIT:
					runNetwork = false;
					break;
				default:
					logger.severe("Unknown network command");
					runNetwork = false;
					result = false;
					break;
				}
			} while (runNetwork);

			gameManager.endGame("", userId);

			return result;
		} else {
			clientInterface.printServerBusyMessage();
		}

		return false;
	}
}
