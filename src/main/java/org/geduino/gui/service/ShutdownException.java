package org.geduino.gui.service;

public class ShutdownException extends OSException {

	private static final long serialVersionUID = 1L;

	public ShutdownException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShutdownException(String message) {
		super(message);
	}

	public ShutdownException(Throwable cause) {
		super(cause);
	}

}
