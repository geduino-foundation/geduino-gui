package org.geduino.gui.settings;

import java.io.IOException;

import com.genius.framework.common.bean.Bean;
import com.genius.framework.common.bean.BeanContext;
import com.genius.framework.common.logger.Logger;

public class SettingsSingleton extends Bean {

	private final Logger logger = Logger.getLogger(this.getClass());

	private GuiSettings guiSettings;

	public static SettingsSingleton getInstance() {
		return (SettingsSingleton) BeanContext.getInstance(
				SettingsSingleton.class, true);
	}

	@Override
	public void postConstructor() {

		// Creare gui settings
		guiSettings = new GuiSettings();

		try {

			// Load settings from store
			guiSettings.loadFromStore();

		} catch (IOException ex) {

			// Log
			logger.warning("settings file not loaded: " + ex.getMessage());

		}
	}

	@Override
	public void preDestroy() throws InterruptedException {
	}

	public GuiSettings getGuiSettings() {
		return guiSettings;
	}

}
