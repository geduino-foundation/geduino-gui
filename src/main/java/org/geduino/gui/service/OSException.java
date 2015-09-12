package org.geduino.gui.service;

public class OSException extends Exception {

	private static final long serialVersionUID = 1L;

	public OSException(String message, Throwable cause) {
		super(message, cause);
	}

	public OSException(String message) {
		super(message);
	}

	public OSException(Throwable cause) {
		super(cause);
	}

}
