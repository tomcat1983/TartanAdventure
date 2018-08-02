package edu.cmu.tartan.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

	private static final int DEFAULT_PORT = 10015;
	private static final int DEFAULT_DESIGNER_PORT = 10016;

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

	/**
	 * @param fileName
	 */
	public Config (String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return
	 */
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

		try {
			file.close();
		} catch (IOException e) {
			// Do nothing
			gameLogger.log(Level.SEVERE, "Cannot close : ", e.getMessage());
		}

		return returnValue;
	}

	/**
	 * @return
	 */
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

	/**
	 * @return
	 */
	public static String getServerIp() {
		return properties.getProperty("tartan.server.ip");
	}

	/**
	 * @return
	 */
	public static int getUserPort() {
		int port = DEFAULT_PORT;

		if (properties.containsKey("tartan.server.user.port"))
			port = Integer.parseInt(properties.getProperty("tartan.server.user.port"));

		return port;
	}

	/**
	 * @return
	 */
	public static int getDesignerPort() {
		int port = DEFAULT_DESIGNER_PORT;

		if (properties.containsKey("tartan.server.designer.port")) {
			port = Integer.parseInt(properties.getProperty("tartan.server.designer.port"));
		}

		return port;
	}

	/**
	 * @return
	 */
	public static String getDbName() {
		return properties.getProperty("tartan.db.name");
	}

	/**
	 * @return
	 */
	public static Level getLogLeve() {
		Level level = Level.OFF;

		if (properties.containsKey("tartan.log.level")) {
			String levelString = properties.getProperty("tartan.log.level").toUpperCase();

			switch (levelString) {
			default:
			case "OFF":
				level = Level.OFF;
				break;
			case "SEVERE":
				level = Level.SEVERE;
				break;
			case "WARNING":
				level = Level.WARNING;
				break;
			case "INFO":
				level = Level.INFO;
				break;
			case "CONFIG":
				level = Level.CONFIG;
				break;
			case "FINE":
				level = Level.FINE;
				break;
			case "FINER":
				level = Level.FINER;
				break;
			case "FINEST":
				level = Level.FINEST;
				break;
			case "ALL":
				level = Level.ALL;
				break;
			}
		}

		return level;
	}
}
