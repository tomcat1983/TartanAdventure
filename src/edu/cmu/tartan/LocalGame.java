package edu.cmu.tartan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.xml.GameMode;

public class LocalGame extends Game {
	public static final String SAVE_FILE_NAME = "Tartan_save_file.dat";

	/**
	 * @param userId
	 */
	public LocalGame(@NonNull String userId) {
		super(userId);
	}

	private boolean readGameData(String fileName) {
		try (
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis)
		) {
			context = ((GameContext)in.readObject());
			playerExecutionEngine = new PlayerExecutionEngine(context.getPlayer());
		} catch (IOException|ClassNotFoundException e) {
			gameLogger.severe("Game loading failure. Exception: \n" + e);
	       	gameLogger.severe(e.getMessage());
	       	return false;
		}
		return true;
	}

	/**
	 * @param userId
	 * @return
	 */
	public boolean loadAndStart(String userId) {
		if(context.getUserId().equals(userId)) {
			configureGame(GameMode.LOCAL);
			context.setPlayerGameGoal();
	    	context = new GameContext(userId);
	        interpreter = new PlayerInterpreter();
	        if(readGameData(SAVE_FILE_NAME)) {
	        	start();
	        }
		}
		return false;
	}

	private boolean writeGameData(GameContext context, String fileName) {
		try (
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(bos)
		) {
			out.writeObject(context);
		} catch (IOException e) {
			gameLogger.severe("Game saving failure. Exception: \n" + e);
	       	gameLogger.severe(e.getMessage());
	       	return false;
		}
		return true;
	}

	/**
	 * @param userId
	 * @param fileName
	 * @return
	 */
	public boolean save(String userId, String fileName) {
		if(context.getUserId().equals(userId)) {
			// 1. saving GameContext(Room information isn't save.)
			// 2. saving Player(with Items)
			return writeGameData(context, fileName);
		}
		return false;
	}
}