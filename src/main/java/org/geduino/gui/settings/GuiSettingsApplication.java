package org.geduino.gui.settings;

import com.genius.framework.common.settings.model.Settings;
import com.genius.framework.configuration.gui.AbstractSettingsApplication;

public class GuiSettingsApplication extends AbstractSettingsApplication {

	private static final String TITLE = "Geduino GUI";

	protected Settings getSettings() {

		// Create settings
		Settings settings = new GuiSettings();

		return settings;

	}

	protected String getTitle() {
		return TITLE;
	}

}
