package org.geduino.gui.settings;

import java.io.File;
import java.net.URI;

import com.genius.framework.common.settings.impl.FileSystemSettingsStore;
import com.genius.framework.common.settings.model.Setting;
import com.genius.framework.common.settings.model.Settings;
import com.genius.framework.common.settings.model.validator.GreaterThan;
import com.genius.framework.common.settings.model.validator.NotEmpty;

public class GuiSettings extends Settings {

	private static final long serialVersionUID = 1L;

	public GuiSettings() {
		super(new FileSystemSettingsStore(new File("gui-settings.properties")));
	}

	public static final Setting WELCOME_REDIRECT_TIMEOUT = new Setting(
			"welcome.redirect.timeout", "Welcome redirect timeout ",
			"The time the welcome screen will be shown", "millis", 10000,
			"Application", true, new GreaterThan(0));
	
	public static final Setting ROS_MASTER_HOST = new Setting(
			"ros.master.host", "ROS Master host",
			"ROS Master hostname or ip address", "localhost", "ROS", false,
			new NotEmpty());

	public static final Setting ROS_MASTER_PORT = new Setting(
			"ros.master.port", "ROS Master port", "ROS Master port number",
			11311, "ROS", false, new GreaterThan(0));

	public static final Setting ROS_DIAGNOSTIC_NODE_HOST = new Setting(
			"ros.diagnostic.node.host", "ROS diagnostic node host",
			"The ROS diagnostic node hostname or ip address", "localhost",
			"ROS", false, new NotEmpty());

	public static final Setting ROS_DIAGNOSTIC_NODE_PORT = new Setting(
			"ros.diagnostic.node.port", "ROS diagnostic node port",
			"The ROS diagnostic node port number", 11312, "ROS", false,
			new GreaterThan(0));

	public static final Setting ROS_DIAGNOSTIC_NODE_NAME = new Setting(
			"ros.diagnostic.node.name", "ROS diagnostic node name",
			"The ROS diagnostic node name", "/diagnostics_monitor", "ROS",
			true, new NotEmpty());

	public static final Setting DIAGNOSTIC_TOPIC_NAME = new Setting(
			"diagnostic.topic.name", "Diagnostic topic name",
			"The diagnostic topic name", "/diagnostics", "Diagnostic", true,
			new NotEmpty());

	public static final Setting DIAGNOSTIC_UPDATE_INTERVAL = new Setting(
			"diagnostic.update.interval", "Diagnostic update interval",
			"The diagnostic page update interval", "millis", 10000,
			"Diagnostic", true, new GreaterThan(0));

	public int getWelcomeRedirectTimeout() {
		return getAsInteger(WELCOME_REDIRECT_TIMEOUT);
	}
	
	public String getROSMasterHost() {
		return getAsString(ROS_MASTER_HOST);
	}

	public int getROSMasterPort() {
		return getAsInteger(ROS_MASTER_PORT);
	}

	public URI getROSMasterUri() {

		// Get ros master uri
		URI rosMasterUri = URI.create("http://".concat(getROSMasterHost())
				.concat(":").concat(Integer.toString(getROSMasterPort())));

		return rosMasterUri;

	}

	public String getROSDiagnosticNodeHost() {
		return getAsString(ROS_DIAGNOSTIC_NODE_HOST);
	}

	public int getROSDiagnosticNodePort() {
		return getAsInteger(ROS_DIAGNOSTIC_NODE_PORT);
	}

	public URI getROSDiagnosticNodeUri() {

		// Get ros diagnostic node uri
		URI rosDiagnosticNodeUri = URI.create("http://"
				.concat(getROSDiagnosticNodeHost()).concat(":")
				.concat(Integer.toString(getROSDiagnosticNodePort())));

		return rosDiagnosticNodeUri;

	}

	public String getROSDiagnosticNodeName() {
		return getAsString(ROS_DIAGNOSTIC_NODE_NAME);
	}

	public String getDiagnosticTopicName() {
		return getAsString(DIAGNOSTIC_TOPIC_NAME);
	}

	public int getDiagnosticUpdateInterval() {
		return getAsInteger(DIAGNOSTIC_UPDATE_INTERVAL);
	}
}
