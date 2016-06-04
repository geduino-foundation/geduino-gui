package org.geduino.gui.pages.diagnostic.view;

import java.util.Arrays;

import org.geduino.gui.pages.diagnostic.adapter.KeyValueItemAdapter;
import org.geduino.ros.common.msgs.indigo.diagnostic_msgs.DiagnosticStatus;
import org.geduino.gui.ros.DiagnosticListener;
import org.geduino.gui.ros.DiagnosticSingleton;
import com.genius.framework.core.mvc.callback.Callback;
import com.genius.framework.core.mvc.view.TargetViewScope;
import com.genius.framework.core.view.dialog.impl.HeaderDialogView;
import com.genius.framework.core.view.list.impl.SimpleListView;
import com.genius.framework.core.view.list.item.impl.TypeAItemAwtComponentFactory;
import com.genius.framework.core.view.list.model.ListModel;
import com.genius.framework.core.view.list.paginator.PaginatorView;
import com.genius.framework.core.view.list.provider.SimpleDataProvider;

public class DiagnosticStatusDialog extends HeaderDialogView implements
		DiagnosticListener {

	private String name;

	private SimpleDataProvider keyValueDataProvider;
	private ListModel keyValueModel;

	public DiagnosticStatusDialog(String name) {

		super("diagnosticStatusDialog");

		this.name = name;

	}

	@Override
	public String[] getEventIds() {
		return new String[] {};
	}

	@Override
	protected void onCreate() {

		super.onCreate();

		// Get diagnostic status
		DiagnosticStatus diagnosticStatus = DiagnosticSingleton.getInstance()
				.getDiagnosticStatus(name);

		// Create data provider
		keyValueDataProvider = new SimpleDataProvider(diagnosticStatus.values);

		// Create key value model
		keyValueModel = new ListModel(keyValueDataProvider);

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
		getTitleLabelView().setText(name);

		// Add as diagnostic listener
		DiagnosticSingleton.getInstance().addListener(name, this);

		return true;

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		// Remove as diagnostic listener
		DiagnosticSingleton.getInstance().removeListener(name);

	}

	@Override
	public boolean onClickClose() {

		boolean result = super.onClickClose();

		// Execute callback handlers
		executeCallbackHandlers(new Callback[] {});

		return result;

	}

	@Override
	public void diagnosticUpdated() {

		// Get diagnostic status
		DiagnosticStatus diagnosticStatus = DiagnosticSingleton.getInstance()
				.getDiagnosticStatus(name);

		// Set values
		keyValueDataProvider.setDatas(Arrays.asList(diagnosticStatus.values));

		// Update datas and notify observers
		keyValueModel.updateDatas();
		keyValueModel.notifyObservers();

	}

}
