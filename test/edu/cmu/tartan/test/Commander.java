package edu.cmu.tartan.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Commander {
	final String newLine = System.getProperty("line.separator").toString();
	List<String> commandList = new ArrayList<>();
	
	public Commander() {
	}

	public void add(String command) {
		commandList.add(command);
	}
	
	public void apply() {
		String commandString = "";
		
		for(String command : commandList){
			commandString += command + newLine;
		}
		
		InputStream in = new ByteArrayInputStream(commandString.getBytes());
		System.setIn(in);
		
		commandList.clear();
	}
}
