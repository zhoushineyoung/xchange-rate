package com.ebj.conf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConfigManagerTest {
	private static String filePath = "src/main/resources/config.properties";
	ConfigManager configManager = null;
	ConfigurationSchema config = null;
	@Before
	public void setUp() throws Exception {
		configManager = ConfigManager.getInstance();
		configManager.setFilePath(filePath);
		config = configManager.getConfig();
	}

	@Test
	public void test() {
		System.out.println(config.getYahooExcgRtAPI());
		System.out.println(config.getH2ServPort());
	}

}
