package org.geduino.gui.ros;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geduino.gui.settings.SettingsSingleton;
import org.geduino.ros.common.msgs.hydro.diagnostic_msgs.DiagnosticStatus;
import org.geduino.ros.core.naming.model.GlobalName;
import org.geduino.ros.node.NodeThread;

import com.genius.framework.common.bean.Bean;
import com.genius.framework.common.bean.BeanContext;

public class DiagnosticSingleton extends Bean {

	private static final Comparator<DiagnosticStatus> DIAGNOSTIC_STATUS_COMPARATOR = new DiagnosticStatusComparator();

	private Map<String, DiagnosticStatus> diagnosticStatusMap;
	private Map<String, Date> lastReceivedMap;
	
	private Map<String, DiagnosticListener> diagnosticListeners;

	public static DiagnosticSingleton getInstance() {
		return (DiagnosticSingleton) BeanContext.getInstance(
				DiagnosticSingleton.class, true);
	}

	@Override
	public void postConstructor() {

		// Create diagnostic status map and lat received map
		diagnosticStatusMap = new HashMap<String, DiagnosticStatus>();
		lastReceivedMap = new HashMap<String, Date>();
		
		// Create diagnostic listeners
		diagnosticListeners = new HashMap<String, DiagnosticListener>();

		// Get diagnostic node name
		GlobalName diagnosticNodeName = new GlobalName(SettingsSingleton
				.getInstance().getGuiSettings().getROSDiagnosticNodeName());

		// Create diagnostic node
		DiagnosticNode diagnosticNode = new DiagnosticNode(diagnosticNodeName,
				SettingsSingleton.getInstance().getGuiSettings()
						.getROSDiagnosticNodeUri());

		// Create and start diagnostic node thread
		NodeThread diagnosticNodeThread = new NodeThread(diagnosticNode);
		diagnosticNodeThread.start();

	}

	@Override
	public void preDestroy() throws InterruptedException {

		// Clear diagnostic status map
		diagnosticStatusMap.clear();

	}
	
	public void addListener(String name, DiagnosticListener diagnosticListener) {
		diagnosticListeners.put(name, diagnosticListener);
	}
	
	public void removeListener(String name) {
		diagnosticListeners.remove(name);
	}

	public synchronized void update(DiagnosticStatus[] diagnosticStatusArray) {

		// Get now
		Date now = new Date();

		for (int index = 0; index < diagnosticStatusArray.length; index++) {

			// Get next diagnostic status
			DiagnosticStatus diagnosticStatus = diagnosticStatusArray[index];

			// Get name
			String name = diagnosticStatus.name;

			// Put on diagnostic status map
			diagnosticStatusMap.put(name, diagnosticStatus);

			// Put last received date
			lastReceivedMap.put(name, now);

			// Notify listener
			notifyListener(name);
			
		}

	}

	public synchronized List<DiagnosticStatus> getDiagnosticStatusList() {

		// Get diagnostic status collection
		Collection<DiagnosticStatus> diagnosticStatusCollection = diagnosticStatusMap
				.values();

		// Put on list
		List<DiagnosticStatus> diagnosticStatusList = new ArrayList<DiagnosticStatus>(
				diagnosticStatusCollection);

		// Sort diagnostic status
		Collections.sort(diagnosticStatusList, DIAGNOSTIC_STATUS_COMPARATOR);

		return diagnosticStatusList;

	}
	
	public synchronized DiagnosticStatus getDiagnosticStatus(String name) {
		return diagnosticStatusMap.get(name);
	}

	public synchronized Date getLastReceived(DiagnosticStatus diagnosticStatus) {

		// Get name
		String name = diagnosticStatus.name;

		// Get last received
		Date lastReceived = lastReceivedMap.get(name);

		return lastReceived;

	}
	
	private void notifyListener(String name) {
		
		// Get diagnostic listener for given name
		DiagnosticListener diagnosticListener = diagnosticListeners.get(name);
		
		if (diagnosticListener != null) {
		
			// Notify listener
			diagnosticListener.diagnosticUpdated();
			
		}
		
	}

}
