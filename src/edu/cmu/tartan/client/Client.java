package edu.cmu.tartan.client;

import java.util.logging.Logger;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.LocalGame;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.socket.SocketClient;
import network.NetworkInterface;

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
	 * Socket to server
	 */
	SocketClient socket;

	/**
	 * Socket thread
	 */
	Thread socketClientThread;

	/**
	 * User ID
	 */
	String userId;

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
		LocalGame localGame = new LocalGame(Player.DEFAULT_USER_NAME);
		if(localGame.loadAndStart(Player.DEFAULT_USER_NAME)) {

		}
		return true;
	}

	private boolean newGame() {
		LocalGame localGame = new LocalGame(Player.DEFAULT_USER_NAME);
		if(localGame.configureGame()) {
			localGame.start();
		}

		return true;
	}

	private boolean connectServer(int timeout) {
		socket = new SocketClient();
		socketClientThread = new Thread(socket);
		socketClientThread.start();

		return socket.waitToConnection(timeout);
	}

	private boolean disconnectServer() {
		return socket.stopSocket();
	}

	private boolean sendMessage(String message) {
		return socket.sendMessage(message);
	}

	private boolean login(boolean isDesigner) {
		String id;

		do {
			String[] loginInfo = clientInterface.getLoginInfo();
			NetworkInterface.PacketType packetType = NetworkInterface.PacketType.LOGIN;
			if (isDesigner)
				packetType = NetworkInterface.PacketType.DESIGNER;

			id = loginInfo[0];
			String[] data = { loginInfo[1] };
			String loginPacket = NetworkInterface.makePacket(packetType, id, data);

			sendMessage(loginPacket);
		} while (false); //TODO: need listener;

		userId = id;

		return true;
	}

	private String getUserIdForRegister() {
		String id = "";

		do {
			id = clientInterface.getUserIdForRegister();
			if (id.equals("")) //TODO: validate id
				clientInterface.printInvalidIdMessageForRegister();
			else
				break;
		} while(true);

		return id;
	}

	private String getUserPwForRegister() {
		String pw = "";
		String confirmPw = "";

		do {
			do {
				pw = clientInterface.getUserPwForRegister();
				if (pw.equals("")) //TODO: validate pw
					clientInterface.printInvalidPwMessageForRegister();
				else
					break;
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
		String id = "";
		String pw = "";

		do {
			id = getUserIdForRegister();
			pw = getUserPwForRegister();

			NetworkInterface.PacketType packetType = NetworkInterface.PacketType.REGISTER;
			String[] data = { pw };

			String registerPacket = NetworkInterface.makePacket(packetType, id, data);

			if (sendMessage(registerPacket)) //TODO: check register result
				break;
			else
				clientInterface.printFailMessageForRegister();
		} while (true);

		clientInterface.printSuccessMessageForRegister();

		userId = id;

		return true;
	}

	private boolean runNetworkCommander(boolean isDesigner) {
		boolean running = true;

		do {
			String command = clientInterface.getNetworkCommand();

			if (command.equals("quit")) {
				running = false;
			}
			else if (isDesigner) {
				if (command.equals("")) { //TODO: map file check
					clientInterface.printNoMap();
				} else {
					NetworkInterface.PacketType packetType = NetworkInterface.PacketType.MAP;
					String[] data = { command };
					String loginPacket = NetworkInterface.makePacket(packetType, userId, data);

					clientInterface.printValidateMessageForMapUpload();

					if (sendMessage(loginPacket)) //TODO: map check
						clientInterface.printValidateSucceessMessageForMapUpload();
					else
						clientInterface.printValidateFailMessageForMapUpload();
				}
			}
			else {
				NetworkInterface.PacketType packetType = NetworkInterface.PacketType.COMMAND;
				String[] data = { command };
				String loginPacket = NetworkInterface.makePacket(packetType, userId, data);

				sendMessage(loginPacket);
			}
		} while (running);

		return true;
	}

	private boolean runNetworkMode(boolean isDesigner) {
		if (connectServer(1000)) {
			ClientInterface.NetworkModeCommand networkCommand = ClientInterface.NetworkModeCommand.LOGIN;

			if (!isDesigner)
				networkCommand = clientInterface.getNetworkModeCommand();

			switch (networkCommand) {
			case LOGIN:
				login(isDesigner);
				clientInterface.printWelcomMessage(isDesigner);
				runNetworkCommander(isDesigner);
				disconnectServer();
				break;
			case REGISTER:
				register();
				break;
			case QUIT:
				break;
			default:
				logger.severe("Unknown network command");
				return false;
			}

			return true;
		} else {
			clientInterface.printServerBusyMessage();
		}

		return false;
	}
}
