package edu.cmu.tartan;

import java.io.*;

import edu.cmu.tartan.client.Client;
import edu.cmu.tartan.server.Server;

/**
 * A simple driver for the game
 */
public class Main {
	/**
	 * Game interface for game message and log
	 */
	private static GameInterface gameInterface = GameInterface.getInterface();
	
	/*
	 * Game running mode
	 */
	static String mode;
	
	/*
	 * IP
	 */
	static String ip;
	
	/*
	 * Port
	 */
	static String port;
	
	/*
	 * Setting file reader
	 */
	static BufferedReader bufferReader;

	public static void main(String[] args) {
		String fileUri = "settings.xml";
		
		if (args.length == 1) {
			fileUri = args[0] + File.separator + fileUri;
		} else {
			gameInterface.info("Use default setting.xml location");
			fileUri = System.getProperty("user.dir") + File.separator
					+ "resources" + File.separator + fileUri;
		}
		
		File settingFile = new File(fileUri);
		
		try {
			bufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(settingFile), "UTF-8"));

			mode = bufferReader.readLine().toLowerCase();
			ip = bufferReader.readLine();
			port = bufferReader.readLine();
		} catch (IOException exception) {
			gameInterface.severe("Setting xml error : " + exception.getMessage());
			return;
		}
		
		if (mode.equals("server")) {
			Server server = new Server(ip, port);
			server.start();
		} else
		if (mode.equals("client")) {
			Client client = new Client(ip, port);
			client.start();
		} else {
			gameInterface.severe("Unknown mode : " + mode);
		}		
	}
}