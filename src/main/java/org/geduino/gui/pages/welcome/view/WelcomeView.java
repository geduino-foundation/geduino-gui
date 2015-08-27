package org.geduino.gui.pages.welcome.view;

import java.util.Timer;
import java.util.TimerTask;

import org.geduino.gui.pages.diagnostic.DiagnosticPage;
import org.geduino.gui.settings.SettingsSingleton;

import com.genius.framework.core.mvc.History;
import com.genius.framework.core.view.image.ImageView;
import com.genius.framework.core.view.window.WindowView;

public class WelcomeView extends WindowView {

	public WelcomeView() {
		super("welcomeView");
	}

	@Override
	public String[] getEventIds() {
		return new String[] {};
	}

	@Override
	protected void onCreate() {

		super.onCreate();

		// Create geduino logo view
		ImageView geduinoLogoView = new ImageView("geduinoLogo");
		addComponent(geduinoLogoView);

	}

	@Override
	protected boolean onShow() {

		super.onShow();

		// Create redirect timer task
		RedirectTimerTask redirectTimerTask = new RedirectTimerTask();

		// Create and start update timer
		Timer redirectTimer = new Timer("redirect-timer");
		redirectTimer.schedule(redirectTimerTask, SettingsSingleton
				.getInstance().getGuiSettings().getWelcomeRedirectTimeout());

		return true;

	}

	private class RedirectTimerTask extends TimerTask {

		@Override
		public void run() {

			// Move to diagnostic page
			History.getInstance().moveToPage(DiagnosticPage.class);

		}

	}

}
