package edu.cmu.tartan;

import java.util.logging.*;
import java.io.PrintStream;

public class GameInterface {
	class GameInterfaceFormatter extends Formatter {

		@Override
		public String format(LogRecord record) {
			System.out.println(record.getLevel());
			if (record.getLevel() != Level.FINEST)
				return "";
			System.out.println(record.getMessage());
			return record.getMessage() + "\n";
		}

	}
	
	private final static Logger logger = Logger.getGlobal();
	private final static PrintStream printStream = new PrintStream(System.out);
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
	
	// For log
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
