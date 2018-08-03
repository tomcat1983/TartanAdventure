package edu.cmu.tartan.manager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.account.AccountManager;
import edu.cmu.tartan.account.ReturnType;
import edu.cmu.tartan.socket.SocketClient;
import edu.cmu.tartan.xml.XmlLoginRole;
import edu.cmu.tartan.xml.XmlMessageType;
import edu.cmu.tartan.xml.XmlWriterClient;

enum ConnectionState {
	ENDED(0),
	CONNECTED(1),
	LOGGED_IN(2),
	STARTED(3)
	;

	private int state;

	public int getConnectionState() {
		return state;
	}

	ConnectionState(int state) {
		this.state = state;
	}
}

public class TartanGameManagerClient implements Runnable, IUserCommand{

	/**
	 * Game interface for game message
	 */
	protected static final GameInterface gameInterface = GameInterface.getInterface();

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();
	private static final String SUCCESS = "SUCCESS";

	private SocketClient socket;
	private IQueueHandler messageQueue;
	private ResponseMessage responseMessage;
	private AccountManager accountManager;

	private boolean isLoop = true;

	private ConnectionState connectionState;

	/**
	 * @param isDesigner
	 */
	public TartanGameManagerClient(boolean isDesigner) {
		initialize(isDesigner);
	}

	/**
	 * @param isDesigner
	 */
	public void initialize(boolean isDesigner) {
		responseMessage = new ResponseMessage();
		messageQueue = new MessageQueue();
		accountManager = new AccountManager();

		socket = new SocketClient(responseMessage, messageQueue, isDesigner);
		Thread socketThread = new Thread(socket);
		socketThread.start();

		connectionState = ConnectionState.CONNECTED;
	}

	/**
	 *
	 */
	public void stopGameManager() {

		connectionState = ConnectionState.ENDED;

		isLoop = false;
		socket.stopSocket();
		messageQueue.produce(new SocketMessage("",""));
	}

	/**
	 * @param message
	 * @return
	 */
	public boolean sendMessage(String message) {
		socket.sendMessage(message);
		return true;
	}

	@Override
	public boolean login(String threadName, String userId, String userPw, XmlLoginRole role) {

		String message = null;

		String encryptionPw = encryptPassword(userPw);
		if (encryptionPw == null) {
			return false;
		}

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForLogin(userId, encryptionPw, role);

		sendMessage(message);

		waitResponseMessage();

		if (SUCCESS.equals((responseMessage).getMessage())) {
			connectionState = ConnectionState.LOGGED_IN;
			return true;
		}
		return false;
	}

	@Override
	public boolean register(String threadName, String userId, String userPw) {

		boolean returnValue = false;
		String message = null;

		String encryptionPw = encryptPassword(userPw);
		if (encryptionPw == null) {
			return false;
		}

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForAddUser(userId, encryptionPw);

		sendMessage(message);

		waitResponseMessage();

		if (SUCCESS.equals((responseMessage).getMessage())) {
			returnValue = true;
		}

		return returnValue;
	}

	/**
	 * @param userId
	 * @return
	 */
	public boolean validateUserId(String userId) {

		boolean returnValue = false;

		ReturnType retValue = accountManager.validateId(userId);
		if (ReturnType.SUCCESS == retValue) {
			returnValue = true;
		}
		return returnValue;
	}

	/**
	 * @param userPw
	 * @return
	 */
	public boolean validateUserPw(String userPw) {
		boolean returnValue = false;

		ReturnType retValue = accountManager.validatePassword(userPw);
		if (ReturnType.SUCCESS == retValue) {
			returnValue = true;
		}
		return returnValue;
	}

	@Override
	public boolean startGame(String userId) {

		boolean returnValue = false;

		if (connectionState != ConnectionState.LOGGED_IN) return false;

		String message = null;

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_START, userId);

		sendMessage(message);

		waitResponseMessage();

		if (SUCCESS.equals((responseMessage).getMessage())) {
			connectionState = ConnectionState.STARTED;
			returnValue = true;
		}

		return returnValue;
	}

	@Override
	public boolean endGame(String threadName, String userId) {

		if (connectionState == ConnectionState.ENDED) return false;

		socket.setQuitFromCli(true);
		String message = null;

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_END, userId);

		sendMessage(message);

		waitResponseMessage();

		gameInterface.println(responseMessage.getMessage());


		stopGameManager();

		return true;
	}

	@Override
	public boolean updateGameState(String userId, String command) {

		//if (connectionState != ConnectionState.STARTED) return false;
		
		boolean returnValue = false;

		String message = null;

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForCommand(userId, command);

		returnValue = sendMessage(message);

		return returnValue;
	}

	@Override
	public boolean uploadMap(String mapFile) {

		boolean returnValue = false;

		String message = null;

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForUploadMap(mapFile);

		sendMessage(message);

		waitResponseMessage();

		if (SUCCESS.equals((responseMessage).getMessage())) {
			returnValue = true;
		}
		return returnValue;
	}

	/**
	 * Wait for socket connection
	 * @return
	 */
	public boolean waitForConnection() {
		return socket.waitToConnection(1000);
	}

	/**
	 *
	 */
	public void waitResponseMessage() {

		synchronized (responseMessage) {
			try {
				responseMessage.wait(10 * 1000);
			} catch(InterruptedException e){
                gameLogger.severe("InterruptedException : " + e.getMessage());
                Thread.currentThread().interrupt();
            }
		}
	}

	@Override
	public void run() {
		dequeue();
	}

	/**
	 *
	 */
	public void dequeue() {
		SocketMessage socketMessage = null;
		String message = null;

        while(isLoop){
        	socketMessage = messageQueue.consume();
            message = socketMessage.getMessage();

            if (message != null && !message.isEmpty()) {
            	if("quit".equals(message)) {
            		gameInterface.putCommand(GameInterface.USER_ID_LOCAL_USER, message);
            		stopGameManager();
            	} else {
            		gameInterface.println(message);
            	}

            }
        }
	}

	/**
	 * @param userPw
	 * @return
	 */
	public String encryptPassword(String userPw){

		String encryptionPw = "";

		try{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			byte[] byteData = messageDigest.digest(userPw.getBytes(StandardCharsets.UTF_8));

			StringBuilder sb = new StringBuilder();
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}

			encryptionPw = sb.toString();
		}catch(NoSuchAlgorithmException e){
			gameLogger.warning("NoSuchAlgorithmException : " + e.getMessage());
			encryptionPw = null;
		}

		return encryptionPw;
	}

}
