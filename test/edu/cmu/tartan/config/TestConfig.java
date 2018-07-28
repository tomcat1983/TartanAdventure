package edu.cmu.tartan.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestConfig {
	Config config;
	String fileName = "config.properties";
	
	@BeforeEach
	public void beforeTest() {
		config = new Config(fileName);
	}
	
	@Test
	public void testTrueWhenGetPropertyValue() {
		config.readPropertyFile();
		String serverIp = Config.properties.getProperty("tartan.operation.mode");
		
		assertEquals("server", serverIp);
	
	}
}
