package edu.cmu.tartan.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

	private static final int DEFAULT_PORT = 10015;

	/**
	 * Game interface for game message and log
	 */
	private Logger gameLogger = Logger.getGlobal();

	private static Properties properties = new Properties();

	private String fileName;

	/**
	 * Application running mode
	 */
	public enum RunningMode {
		UNKNOWN, SERVER, CLIENT
	}

	public Config (String fileName) {
		this.fileName = fileName;
	}

	public boolean readPropertyFile() {

		boolean returnValue = true;
		FileInputStream file = null;

		try {
			file = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			gameLogger.log(Level.SEVERE, "FileNotFound : {0}", e.getMessage());
			return false;
		}

		try {
			properties.load(file);
		} catch (IOException e) {
			gameLogger.log(Level.SEVERE, "Exception : {0}", e.getMessage());
			returnValue = false;
		}

		return returnValue;
	}

	public static RunningMode getMode() {
		String mode = properties.getProperty("tartan.operation.mode").toUpperCase();

		if (mode.equals(RunningMode.SERVER.name())) {
			return RunningMode.SERVER;
		} else
		if (mode.equals(RunningMode.CLIENT.name())) {
			return RunningMode.CLIENT;
		} else {
			return RunningMode.UNKNOWN;
		}
	}

	public static String getServerIp() {
		return properties.getProperty("tartan.server.ip");
	}

	public static int getServerPort() {
		int port = DEFAULT_PORT;

		if (properties.containsKey("tartan.server.port"))
			port = Integer.parseInt(properties.getProperty("tartan.server.port"));

		return port;
	}
}
