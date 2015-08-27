package org.geduino.gui.ros;

import java.net.URI;

import org.geduino.gui.settings.SettingsSingleton;
import org.geduino.ros.common.msgs.hydro.diagnostic_msgs.DiagnosticArray;
import org.geduino.ros.common.msgs.hydro.diagnostic_msgs.DiagnosticStatus;
import org.geduino.ros.core.exception.RosException;
import org.geduino.ros.core.naming.model.GlobalName;
import org.geduino.ros.node.Node;
import org.geduino.ros.node.NodeHandle;
import org.geduino.ros.node.Subscriber;
import org.geduino.ros.node.SubscriberListener;

public class DiagnosticNode extends Node implements
		SubscriberListener<DiagnosticArray> {

	public DiagnosticNode(GlobalName nodeName, URI nodeUri) {
		super(nodeName, nodeUri);
	}

	@Override
	protected void paramUpdated(GlobalName parameterName, Object value) {
		// NOTHING TO DO
	}

	@Override
	protected void run() throws RosException {

		// Create node handle
		NodeHandle nodeHandle = new NodeHandle(this, SettingsSingleton
				.getInstance().getGuiSettings().getROSMasterUri());

		// Start node handle
		nodeHandle.start();

		// Create diagnostics global name
		GlobalName diagnosticsGlobalName = this
				.getResolvedName(SettingsSingleton.getInstance()
						.getGuiSettings().getDiagnosticTopicName());

		// Subscribe diagnostics
		Subscriber<DiagnosticArray> diagnosticArraySubscriber = new Subscriber<DiagnosticArray>(
				diagnosticsGlobalName, DiagnosticArray.DETAILS, this);

		// Register subscriber
		nodeHandle.registerSubscriber(diagnosticArraySubscriber);

		while (!Thread.currentThread().isInterrupted()) {

			try {

				// Thread sleep
				Thread.sleep(10000);

			} catch (InterruptedException ex) {

				// Stop node handle
				nodeHandle.stop();

				// Propagate thread interruption
				Thread.currentThread().interrupt();

			}

		}

	}

	@Override
	protected void shutdown() {
		// NOTHING TO DO
	}

	@Override
	public void messageReceived(DiagnosticArray diagnosticArray) {

		// Get diagnostic status array
		DiagnosticStatus[] diagnosticStatusArray = diagnosticArray.status;

		// Update diagnostic status singleton
		DiagnosticSingleton.getInstance().update(diagnosticStatusArray);

	}

}
