package edu.cmu.tartan.manager;

import java.util.Scanner;

import edu.cmu.tartan.GameInterface;

public class CommandLineInput {
	
	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

    /**
     * Reads input from the command line.
     */
    private Scanner scanner;
	
	public CommandLineInput() {
		
        scanner = new Scanner(System.in);
		
	}
	
	

}
