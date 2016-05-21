package org.geduino.gui.pages.diagnostic.view;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.geduino.gui.pages.diagnostic.adapter.DiagnosticStatusItemAdapter;
import org.geduino.gui.service.OSService;
import org.geduino.gui.service.ShutdownException;
import org.geduino.gui.settings.SettingsSingleton;
import org.geduino.ros.common.msgs.jade.diagnostic_msgs.DiagnosticStatus;

import com.genius.framework.common.locale.LocalizedMessage;
import com.genius.framework.common.logger.Logger;
import com.genius.framework.core.mvc.callback.Callback;
import com.genius.framework.core.mvc.callback.CallbackHandler;
import com.genius.framework.core.mvc.callback.impl.ConfirmCallback;
import com.genius.framework.core.mvc.view.TargetViewScope;
import com.genius.framework.core.util.UserInteractionLock;
import com.genius.framework.core.view.button.ButtonView;
import com.genius.framework.core.view.dialog.impl.ConfirmDialogView;
import com.genius.framework.core.view.label.LabelView;
import com.genius.framework.core.view.list.header.SimpleListHeaderView;
import com.genius.framework.core.view.list.impl.SimpleListView;
import com.genius.framework.core.view.list.item.impl.TypeBItemAwtComponentFactory;
import com.genius.framework.core.view.list.model.ListModel;
import com.genius.framework.core.view.list.paginator.PaginatorView;
import com.genius.framework.core.view.list.provider.ActionDataProvider;
import com.genius.framework.core.view.window.WindowView;

public class DiagnosticView extends WindowView {

	private static final Logger LOGGER = Logger.getLogger(DiagnosticView.class);

	private LabelView connectionLabelView;
	private LabelView lastUpdateLabelView;

	private ActionDataProvider diagnosticDataProvider;
	private ListModel diagnosticModel;

	private Timer updateTimer;

	public DiagnosticView() {
		super("diagnosticView");
	}

	@Override
	public String[] getEventIds() {
		return new String[] {};
	}

	@Override
	protected void onCreate() {

		super.onCreate();

		// The connection label
		connectionLabelView = new LabelView("connection");
		addComponent(connectionLabelView);

		// The last update label
		lastUpdateLabelView = new LabelView("lastUpdate");
		addComponent(lastUpdateLabelView);

		// Create data provider
		diagnosticDataProvider = new ActionDataProvider("diagnosticController");

		// Create diagnostic model
		diagnosticModel = new ListModel(diagnosticDataProvider);

		// List header
		SimpleListHeaderView diagnosticHeaderView = new SimpleListHeaderView(
				"header", diagnosticModel);
		addComponent(diagnosticHeaderView);

		// Create item adapter
		DiagnosticStatusItemAdapter diagnosticStatusItemAdapter = new DiagnosticStatusItemAdapter();

		// Create simple list view
		SimpleListView simpleListView = new SimpleListView("list",
				diagnosticModel, new TypeBItemAwtComponentFactory(),
				diagnosticStatusItemAdapter, TargetViewScope.PARENT);
		addComponent(simpleListView);

		// Create paginator
		PaginatorView paginatorView = new PaginatorView("paginator",
				diagnosticModel);
		addComponent(paginatorView);

		// Create shutdown button
		ButtonView shutdownButtonView = new ButtonView("shutdown",
				TargetViewScope.PARENT);
		addComponent(shutdownButtonView);

	}

	@Override
	protected boolean onShow() {

		super.onShow();

		// Get ros master uri
		URI rosMasterUri = SettingsSingleton.getInstance().getGuiSettings()
				.getROSMasterUri();

		// Set text on connection label
		connectionLabelView.setText(new LocalizedMessage(
				"diagnostic.connection").getMessage(rosMasterUri));

		// Start update timer
		startUpdateTimer();

		return true;

	}

	@Override
	protected void onHide() {

		super.onHide();

		// Stop update timer
		stopUpdateTimer();

	}

	public void onClickList(DiagnosticStatus diagnosticStatus) {

		// Stop update timer
		stopUpdateTimer();

		// Create diagnostic status dialog
		DiagnosticStatusDialog diagnosticStatusDialog = new DiagnosticStatusDialog(
				diagnosticStatus);
		diagnosticStatusDialog
				.addCallbackHandler(new StartUpdateTimerCallbackHandler());
		hotAddComponent(diagnosticStatusDialog);

	}

	public void onClickShutdown() {
		
		// Create confirm dialog view
		ConfirmDialogView confirmDialogView = new ConfirmDialogView("confirmShutdown");
		confirmDialogView.addCallbackHandler(new ShutdownCallbackHandler());
		hotAddComponent(confirmDialogView);
		
	}

	private synchronized void startUpdateTimer() {

		if (updateTimer == null) {

			// Create and start update timer
			updateTimer = new Timer("update-timer");
			updateTimer.schedule(new UpdateTimerTask(), 500, SettingsSingleton
					.getInstance().getGuiSettings()
					.getDiagnosticUpdateInterval());

		}

	}

	private synchronized void stopUpdateTimer() {

		if (updateTimer != null) {

			// Stop update timer
			updateTimer.cancel();
			updateTimer = null;

		}

	}

	private class StartUpdateTimerCallbackHandler implements CallbackHandler {

		@Override
		public void hanlde(Callback[] callbacks) {

			// Start update timer
			startUpdateTimer();

		}

	}

	private class UpdateTimerTask extends TimerTask {

		@Override
		public void run() {

			// Invalidate diagnostic data provider
			diagnosticDataProvider.invalidate();

			// Update diagnostic model
			diagnosticModel.updateDatas();
			diagnosticModel.notifyObservers();

			// Get now
			Date now = new Date();

			// Format now
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm.ss");
			String lastUpdateString = simpleDateFormat.format(now);

			// Set text on last update label
			lastUpdateLabelView.setText(new LocalizedMessage(
					"diagnostic.last.update").getMessage(lastUpdateString));
			lastUpdateLabelView.refresh();

		}

	}

	private class ShutdownCallbackHandler implements CallbackHandler {

		@Override
		public void hanlde(Callback[] callbacks) {

			// Get confirm callback
			ConfirmCallback confirmCallback = (ConfirmCallback) callbacks[0];

			if (confirmCallback.isConfirm()) {

				try {

					// Shutdown
					OSService.getInstance().shutdown();

				} catch (ShutdownException ex) {

					// Log
					LOGGER.error(ex);

				}

			} else {
				
				// Set user interaction ready
				UserInteractionLock.getInstance().ready();
				
			}

		}

	}

}
