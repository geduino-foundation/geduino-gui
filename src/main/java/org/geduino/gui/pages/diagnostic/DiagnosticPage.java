package org.geduino.gui.pages.diagnostic;

import org.geduino.gui.pages.diagnostic.controller.DiagnosticController;
import org.geduino.gui.pages.diagnostic.view.DiagnosticView;

import com.genius.framework.core.mvc.controller.Controller;
import com.genius.framework.core.mvc.page.Page;
import com.genius.framework.core.mvc.view.View;

public class DiagnosticPage extends Page {

	@Override
	public View getViewClass() {
		return new DiagnosticView();
	}

	@Override
	public Controller[] getControllersClass() {
		return new Controller[] { new DiagnosticController() };
	}

}
