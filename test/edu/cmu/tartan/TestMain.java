package edu.cmu.tartan;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.test.Commander;

public class TestMain {

	/**
	 * Commander for user input
	 */
	private Commander commander;

	/**
	 * Arguments for main processor
	 */
	String[] args;

	@BeforeEach
	void testMakeCommander() {
		commander = new Commander();
	}

	@AfterEach
	void testRunClient() {
		Main.main(args);
	}
	@Test
	public void testServerMode() {
		System.out.println("testServerMode");

		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "server";

		args = new String[1];
		args[0] = propertiesUri;
	}

	@Test
	public void testServerModeWithInvalidCommand() {
		System.out.println("testServerModeWithInvalidCommand");

		commander.add("invalidCmd");
		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "server";

		args = new String[1];
		args[0] = propertiesUri;
	}

	@Test
	public void testClientMode() {
		System.out.println("testClientMode");

		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "client";

		args = new String[1];
		args[0] = propertiesUri;
	}

	@Test
	public void testClientModeWithInvalidCommand() {
		System.out.println("testClientModeWithInvalidCommand");

		commander.add("invalidCmd");
		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "client";

		args = new String[1];
		args[0] = propertiesUri;
	}

	@Test
	public void testNoArgs() {
		System.out.println("testNoArgs");

		commander.add("exit");
		commander.apply();

		args = new String[0];
	}

	@Test
	public void testNoArgsWithNoArgs() {
		System.out.println("testNoArgsWithNoArgs");

		commander.add("invalidCmd");
		commander.add("exit");
		commander.apply();

		args = new String[0];
	}

	@Test
	public void testInvalidArgs() {
		System.out.println("testInvalidArgs");

		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources";

		args = new String[1];
		args[0] = propertiesUri;
	}

	@Test
	public void testInvalidArgsWithNoArgs() {
		System.out.println("testInvalidArgsWithNoArgs");

		commander.add("invalidCmd");
		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources";

		args = new String[1];
		args[0] = propertiesUri;
	}
}
