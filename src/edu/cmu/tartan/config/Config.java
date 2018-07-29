package edu.cmu.tartan.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import edu.cmu.tartan.GameInterface;

public class Config {
	
	private static final int defaultPort = 10015;
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	public static Properties properties;
	private FileInputStream file = null;
	
	private String fileName;
	
	/**
	 * Application running mod
	 */
	public enum RunningMode {
		UNKNOWN, SERVER, CLIENT
	}
	
	public Config (String fileName) {
		this.fileName = fileName;
		
		properties = new Properties();
		readPropertyFile();
	}
	
	public boolean readPropertyFile() {
		
		boolean returnValue = true;
		
		try {
			file = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			gameInterface.println("FileNotFound : " + e.getMessage());
			return false;
		}
		
		try {
			properties.load(file);
		} catch (IOException e) {
			e.printStackTrace();
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
		int port = defaultPort;
		
		if (properties.containsKey("tartan.server.port"))
			port = Integer.parseInt(properties.getProperty("tartan.server.port"));
		
		return port;
	}
}
