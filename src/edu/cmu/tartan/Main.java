package edu.cmu.tartan;

import java.io.BufferedReader;
import java.io.File;
import java.util.logging.Logger;

import edu.cmu.tartan.client.Client;
import edu.cmu.tartan.config.Config;
import edu.cmu.tartan.server.Server;

/**
 * A simple driver for the game
 */
public class Main {

	/**
	 * Game logger for game log
	 */
	protected static final Logger gameLogger = Logger.getGlobal();

	/*
	 * Setting file reader
	 */
	static BufferedReader bufferReader;

	public static void main(String[] args) {
		String fileUri = "config.properties";

		if (args.length == 1) {
			fileUri = args[0] + File.separator + fileUri;
		} else {
			gameLogger.info("Use default config.properties location");
			fileUri = System.getProperty("user.dir") + File.separator + fileUri;
		}

		new Config(fileUri);

		Config.RunningMode mode = Config.getMode();

		switch (mode) {
		case SERVER:
			Server server = new Server();
			server.start();
			break;
		case CLIENT:
			Client client = new Client();
			client.start();
			break;
		case UNKNOWN:
			gameLogger.severe("Unknown mode");
			break;
		}
	}
}