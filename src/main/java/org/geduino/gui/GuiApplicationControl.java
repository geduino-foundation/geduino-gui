package org.geduino.gui;

import org.apache.pivot.wtk.DesktopApplicationContext;
import org.geduino.gui.settings.GuiSettingsApplication;

import com.genius.framework.common.gre.ApplicationControl;
import com.genius.framework.common.gre.exception.ApplicationControlException;

public class GuiApplicationControl implements ApplicationControl {

	@Override
	public void afterRun() throws ApplicationControlException {
		// NOTHING TO DO
	}

	@Override
	public void afterStop() throws ApplicationControlException {
		// NOTHING TO DO
	}

	@Override
	public void beforeRun() throws ApplicationControlException {
		// NOTHING TO DO
	}

	@Override
	public void beforeStop() throws ApplicationControlException {
		// NOTHING TO DO
	}

	@Override
	public void launchApplicationConfigurationTool(String[] args) {

		// Launch configuration tool
		DesktopApplicationContext.main(GuiSettingsApplication.class,
				args);

	}

}
