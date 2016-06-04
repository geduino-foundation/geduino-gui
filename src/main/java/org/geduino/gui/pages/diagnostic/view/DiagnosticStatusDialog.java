package org.geduino.gui.pages.diagnostic.view;

import org.geduino.gui.pages.diagnostic.adapter.KeyValueItemAdapter;
import org.geduino.ros.common.msgs.indigo.diagnostic_msgs.DiagnosticStatus;

import com.genius.framework.core.mvc.callback.Callback;
import com.genius.framework.core.mvc.view.TargetViewScope;
import com.genius.framework.core.view.dialog.impl.HeaderDialogView;
import com.genius.framework.core.view.list.impl.SimpleListView;
import com.genius.framework.core.view.list.item.impl.TypeAItemAwtComponentFactory;
import com.genius.framework.core.view.list.model.ListModel;
import com.genius.framework.core.view.list.paginator.PaginatorView;
import com.genius.framework.core.view.list.provider.SimpleDataProvider;

public class DiagnosticStatusDialog extends HeaderDialogView {

	private DiagnosticStatus diagnosticStatus;

	public DiagnosticStatusDialog(DiagnosticStatus diagnosticStatus) {

		super("diagnosticStatusDialog");

		this.diagnosticStatus = diagnosticStatus;

	}

	@Override
	public String[] getEventIds() {
		return new String[] {};
	}

	@Override
	protected void onCreate() {

		super.onCreate();

		// Create data provider
		SimpleDataProvider keyValueDataProvider = new SimpleDataProvider(
				diagnosticStatus.values);

		// Create key value model
		ListModel keyValueModel = new ListModel(keyValueDataProvider);

		// Create key value item adapter
		KeyValueItemAdapter keyValueItemAdapter = new KeyValueItemAdapter();

		// Create simple list view
		SimpleListView simpleListView = new SimpleListView("list",
				keyValueModel, new TypeAItemAwtComponentFactory(),
				keyValueItemAdapter, TargetViewScope.PARENT);
		addComponent(simpleListView);

		// Create paginator
		PaginatorView paginatorView = new PaginatorView("paginator",
				keyValueModel);
		addComponent(paginatorView);

	}

	@Override
	protected boolean onShow() {

		super.onShow();

		// Set diagnostic status name on title
		getTitleLabelView().setText(diagnosticStatus.name);

		return true;

	}

	@Override
	public boolean onClickClose() {

		boolean result = super.onClickClose();

		// Execute callback handlers
		executeCallbackHandlers(new Callback[] {});

		return result;

	}

}
