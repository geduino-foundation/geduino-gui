package org.geduino.gui.ros;

import java.util.Comparator;

import org.geduino.ros.common.msgs.indigo.diagnostic_msgs.DiagnosticStatus;

class DiagnosticStatusComparator implements Comparator<DiagnosticStatus> {

	@Override
	public int compare(DiagnosticStatus diagnosticStatus1,
			DiagnosticStatus diagnosticStatus2) {

		// Get result
		int result = diagnosticStatus2.level - diagnosticStatus1.level;

		return result;

	}

}
