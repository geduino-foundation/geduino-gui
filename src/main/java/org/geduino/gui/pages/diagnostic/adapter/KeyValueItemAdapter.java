package org.geduino.gui.pages.diagnostic.adapter;

import org.geduino.ros.common.msgs.jade.diagnostic_msgs.KeyValue;

import com.genius.framework.core.view.list.item.impl.TypeAItemAdapter;

public class KeyValueItemAdapter extends TypeAItemAdapter {

	public KeyValueItemAdapter() {
		super(KeyValue.class);
	}

	@Override
	public String adaptSubText() {
		return "";
	}

	@Override
	public String adaptSubText(Object model) {

		// Cast to key value
		KeyValue keyValue = (KeyValue) model;

		return keyValue.value;

	}

	@Override
	public String adaptText() {
		return "";
	}

	@Override
	public String adaptText(Object model) {

		// Cast to key value
		KeyValue keyValue = (KeyValue) model;

		return keyValue.key;

	}

}
