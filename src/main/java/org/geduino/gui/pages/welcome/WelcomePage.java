package org.geduino.gui.pages.welcome;

import org.geduino.gui.pages.welcome.controller.WelcomeController;
import org.geduino.gui.pages.welcome.view.WelcomeView;

import com.genius.framework.core.mvc.controller.Controller;
import com.genius.framework.core.mvc.page.Page;
import com.genius.framework.core.mvc.view.View;

public class WelcomePage extends Page {

	@Override
	public View getViewClass() {
		return new WelcomeView();
	}

	@Override
	public Controller[] getControllersClass() {
		return new Controller[] { new WelcomeController() };
	}

}
