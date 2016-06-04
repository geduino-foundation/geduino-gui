package org.geduino.gui.pages.diagnostic.controller;

import java.util.List;

import org.geduino.gui.ros.DiagnosticSingleton;
import org.geduino.ros.common.msgs.indigo.diagnostic_msgs.DiagnosticStatus;

import com.genius.framework.core.mvc.controller.Controller;

public class DiagnosticController extends Controller {

	public List<?> getList() {

		// Get diagnostic status list
		List<DiagnosticStatus> diagnosticStatusList = DiagnosticSingleton
				.getInstance().getDiagnosticStatusList();

		return diagnosticStatusList;

	}

	@Override
	public void destroy() {
	}

	@Override
	protected void onDispose() {
	}

	@Override
	protected void onResume() {
	}

}
