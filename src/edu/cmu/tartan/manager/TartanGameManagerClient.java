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


public class TartanGameManagerClient implements Runnable, IUserCommand{

	/**
	 * Game interface for game message
	 */
	protected static final GameInterface gameInterface = GameInterface.getInterface();

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	private SocketClient socket;
	private IQueueHandler messageQueue;
	private ResponseMessage responseMessage;
	private AccountManager accountManager;

	private boolean isLoop = true;

	public TartanGameManagerClient(SocketClient socket, ResponseMessage responseMessage, IQueueHandler messageQueue) {
		this.socket = socket;
		this.responseMessage = responseMessage;
		this.messageQueue = messageQueue;
		accountManager = new AccountManager();
	}

	public boolean sendMessage(String message) {
		socket.sendMessage(message);
		return false;
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

		if ("SUCCESS".equals((responseMessage).getMessage())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean register(String threadName, String userId, String userPw) {

		String message = null;
		
		String encryptionPw = encryptPassword(userPw);
		if (encryptionPw == null) {
			return false;
		}

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForAddUser(userId, encryptionPw);

		sendMessage(message);

		waitResponseMessage();

		if ("SUCCESS".equals((responseMessage).getMessage())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean validateUserId(String userId) {
		gameLogger.info("validUserId : " + userId );
		ReturnType returnValue = accountManager.validateId(userId);
		if (ReturnType.SUCCESS == returnValue) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateUserPw(String userPw) {
		ReturnType returnValue = accountManager.validatePassword(userPw);
		if (ReturnType.SUCCESS == returnValue) {
			return true;
		}
		return false;
	}

	@Override
	public boolean startGame(String userId) {

		String message = null;

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_START, userId);

		sendMessage(message);

		waitResponseMessage();

		if ("SUCCESS".equals((responseMessage).getMessage())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean endGame(String threadName, String userId) {

		String message = null;

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForGameStartEnd(XmlMessageType.REQ_GAME_END, userId);

		sendMessage(message);


		// TODO Sequence of an end game
		socket.stopSocket();

		isLoop = false;
		messageQueue.produce(new SocketMessage(Thread.currentThread().getName(), userId));
//		int returnValue = messageQueue.clearQueue();

		return true;
	}

	@Override
	public boolean updateGameState(String userId, String command) {

		boolean returnValue = false;

		String message = null;

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForCommand(userId, command);

		returnValue = sendMessage(message);

		return returnValue;
	}

	@Override
	public boolean uploadMap(String mapFile) {

		String message = null;

		XmlWriterClient xw = new XmlWriterClient();
		message = xw.makeXmlForUploadMap(mapFile);

		sendMessage(message.replaceAll("\\n", ""));

		waitResponseMessage();

		if ("SUCCESS".equals((responseMessage).getMessage())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Wait for socket connection
	 * @return
	 */
	public boolean waitForConnection() {
		return socket.waitToConnection(1000);
	}

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

	public void dequeue() {
		SocketMessage socketMessage = null;
		String message = null;

        while(isLoop){
        	socketMessage = messageQueue.consume();
            message = socketMessage.getMessage();

            if (message != null && !message.isEmpty()) {
            	gameInterface.println(message);

            }
        }
	}
	
	public String encryptPassword(String userPw){

		String encryptionPw = ""; 

		try{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); 

			byte byteData[] = messageDigest.digest(userPw.getBytes(StandardCharsets.UTF_8));
			
			StringBuffer sb = new StringBuffer(); 
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
