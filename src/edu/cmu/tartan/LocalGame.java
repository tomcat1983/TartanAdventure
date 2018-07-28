package edu.cmu.tartan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.games.CustomizingGame;
import edu.cmu.tartan.manager.IGameControlMessage;
import edu.cmu.tartan.xml.XmlParser;

public class LocalGame extends Game implements IGameControlMessage {
	public static final String SAVE_FILE_NAME = "Tartan_save_file.dat";
	
	public LocalGame(@NonNull String userId) {
		super(userId);
	}
	
	@Override
	public boolean controlGame(String message) {
		return false;
	}
	
	private boolean readGameData() {
		try ( 
			FileInputStream fis = new FileInputStream(SAVE_FILE_NAME);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis)
		) {
			context= ((GameContext)in.readObject());
		} catch (IOException|ClassNotFoundException e) {
			gameLogger.severe("Game loading failure. Exception: \n" + e);
	       	gameLogger.severe(e.getMessage());
	       	return false;
		}
		return true;
	}
	
	public boolean loadAndStart(String userId) {
		if(context.getUserId().equals(userId)) {
			// 0. loading local game XML
			XmlParser parseXml;
			try {
				parseXml = new XmlParser(); 
				CustomizingGame cGame = (CustomizingGame) parseXml.loadGameMapXml(userId);
				
				if(cGame!=null && readGameData()) {
					// 1. loading Player(with Items) and GameContext(without room?)
					start();
				} else {
					gameLogger.severe("Game loading failure.");
					return false;
				}
			} catch (ParserConfigurationException e) {
				gameLogger.severe("Game loading failure. Exception: \n" + e);
		       	gameLogger.severe(e.getMessage());
		       	return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean writeGameData(GameContext context, Player player) {
		try (
			FileOutputStream fos = new FileOutputStream(SAVE_FILE_NAME);
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
	
	public boolean save(String userId) {
		if(context.getUserId().equals(userId)) {
			// 1. saving GameContext(Room information isn't save.)
			// 2. saving Player(with Items)
			return writeGameData(context, context.getPlayer());
		}
		return false;
	}
}
