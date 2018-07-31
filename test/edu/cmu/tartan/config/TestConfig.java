package edu.cmu.tartan.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestConfig {
	Config config;
	String fileName = "config.properties";

	@BeforeEach
	public void beforeTest() {
		config = new Config(fileName);
		config.readPropertyFile();
	}

	@Test
	public void testGetRunningMode() {
		Config.RunningMode mode = Config.getMode();

		assertEquals(Config.RunningMode.CLIENT, mode);
	}

	@Test
	public void testGetServerIp() {
		String serverIp = Config.getServerIp();

		assertTrue(serverIp.equals("127.0.0.1"));
	}

	@Test
	public void testGetServerPort() {
		int port = Config.getUserPort();

		assertEquals(10015, port);
	}
}
