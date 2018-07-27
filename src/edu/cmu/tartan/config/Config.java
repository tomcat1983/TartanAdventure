package edu.cmu.tartan.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import edu.cmu.tartan.GameInterface;

public class Config {
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	public static Properties properties;
	private FileInputStream file = null;
	
	private String fileName;
	
	public Config (String fileName) {
		this.fileName = fileName;
		
		properties = new Properties();
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
}
