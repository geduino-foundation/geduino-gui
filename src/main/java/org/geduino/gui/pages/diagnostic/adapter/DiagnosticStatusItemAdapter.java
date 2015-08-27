package org.geduino.gui.pages.diagnostic.adapter;

import java.util.Date;

import org.geduino.gui.ros.DiagnosticSingleton;
import org.geduino.ros.common.msgs.hydro.diagnostic_msgs.DiagnosticStatus;

import com.genius.framework.common.locale.LocalizedMessage;
import com.genius.framework.core.view.list.item.impl.TypeBItemAdapter;

public class DiagnosticStatusItemAdapter extends TypeBItemAdapter {

	public DiagnosticStatusItemAdapter() {
		super(DiagnosticStatus.class);
	}

	@Override
	public String adaptLeftSubText() {
		return "";
	}

	@Override
	public String adaptLeftSubText(Object model) {

		// Cast to diagnostic status
		DiagnosticStatus diagnosticStatus = (DiagnosticStatus) model;

		return diagnosticStatus.message;

	}

	@Override
	public String adaptRigthSubText() {
		return "";
	}

	@Override
	public String adaptRigthSubText(Object model) {

		// Cast to diagnostic status
		DiagnosticStatus diagnosticStatus = (DiagnosticStatus) model;

		// Get last received
		Date lastReceived = DiagnosticSingleton.getInstance().getLastReceived(
				diagnosticStatus);

		// Calculate ellapsed time in millis
		long ellapsedMillis = System.currentTimeMillis()
				- lastReceived.getTime();

		// Get human readable string for ellapsed time
		String ellapsedTimeString = HumanReadableMillis
				.humanize(ellapsedMillis);

		// Create localized message
		LocalizedMessage localizedMessage = new LocalizedMessage(
				"diagnostic.status.received");

		return localizedMessage.getMessage(ellapsedTimeString);

	}

	@Override
	public String adaptText() {
		return "";
	}

	@Override
	public String adaptText(Object model) {

		// Cast to diagnostic status
		DiagnosticStatus diagnosticStatus = (DiagnosticStatus) model;

		return diagnosticStatus.name;

	}

}
