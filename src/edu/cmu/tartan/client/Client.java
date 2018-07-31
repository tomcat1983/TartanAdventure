package edu.cmu.tartan.client;

import java.io.File;
import java.util.logging.Logger;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.LocalGame;
import edu.cmu.tartan.manager.IQueueHandler;
import edu.cmu.tartan.manager.MessageQueue;
import edu.cmu.tartan.manager.ResponseMessage;
import edu.cmu.tartan.manager.TartanGameManagerClient;
import edu.cmu.tartan.socket.SocketClient;
import edu.cmu.tartan.xml.XmlLoginRole;

public class Client {

	/**
	 * Game logger for game log
	 */
	private Logger logger = Logger.getGlobal();

	/**
	 * Game interface for game log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

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

	public boolean start() {
		boolean running = true;

		do {
			clientInterface.printGameModeMessage();

			switch (clientInterface.getRunningMode()) {
			case LOCAL:
				if (!runLocalMode())
					running = false;
				break;
			case NETWORK:
				if (!runNetworkMode(false))
					running = false;
				break;
			case DESIGNER:
				if (!runNetworkMode(true))
					running = false;
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
		gameInterface.println("TBD");
		LocalGame localGame = new LocalGame(GameInterface.USER_ID_NONE);
		if(localGame.loadAndStart(GameInterface.USER_ID_NONE)) {

		}
		return true;
	}

	private boolean newGame() {
		LocalGame localGame = new LocalGame(GameInterface.USER_ID_NONE);
		if(localGame.configureGame()) {
			localGame.start();
		}

		return true;
	}

	private boolean connectServer(int timeout, boolean isDesigner) {
		IQueueHandler messageQueue = new MessageQueue();
		ResponseMessage responseMessage = new ResponseMessage("");

		SocketClient socketClient = new SocketClient(responseMessage, messageQueue, isDesigner);
		Thread socketClientThread = new Thread(socketClient);
		socketClientThread.start();

		gameManager = new TartanGameManagerClient(socketClient, responseMessage, messageQueue);
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
			role = XmlLoginRole.PLAYER;

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
		boolean gameStarted = false;

		do {
			String command = clientInterface.getNetworkCommand();

			if (command.equals("quit")) {
				running = false;
			}
			else if (isDesigner) {
				File file = new File(command);

				if (file.exists()) {
					clientInterface.printValidateMessageForMapUpload();

					if (gameManager.uploadMap(command))
						clientInterface.printValidateSucceessMessageForMapUpload();
					else
						clientInterface.printValidateFailMessageForMapUpload();
				}
				else
				{
					clientInterface.printNoMap();
				}
			}
			else if (command.equals("start")){
				if (gameManager.startGame(userId))
					gameStarted = true;
				else
					clientInterface.printCannotStartMessage();
			}
			else if (gameStarted) {
				gameManager.updateGameState(userId, command);
			}
		} while (running);

		return true;
	}

	private boolean runNetworkMode(boolean isDesigner) {
		connectServer(1000, isDesigner);

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
