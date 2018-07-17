package edu.cmu.tartan;

import java.util.logging.*;
import java.io.PrintStream;

public class GameInterface {

	/**
	 * Logger for log message
	 */
	private final static Logger logger = Logger.getGlobal();
	
	/**
	 * PrinterStream for Game message
	 */
	private final static PrintStream printStream = new PrintStream(System.err);
	
	/**
	 * Static variable for singleton
	 */
	private static GameInterface instance = null;
	
	public GameInterface() {
		super();
        logger.setUseParentHandlers(false);

        ConsoleHandler logHhandler = new ConsoleHandler();
        logger.addHandler(logHhandler);
    }
	
	public static GameInterface getInterface() {
		if (instance == null) {
			instance = new GameInterface();
		}
		
		return instance;
	}
	
	// For log message
	public void severe(String msg) {
		logger.severe(msg);
	}

	public void warning(String msg) {
		logger.warning(msg);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	// For game message
	public void print(String msg) {
		printStream.print(msg);
	}
	
	public void println(String msg) {
		printStream.println(msg);
	}
}
