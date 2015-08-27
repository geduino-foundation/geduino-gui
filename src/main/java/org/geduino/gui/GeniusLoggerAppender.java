package org.geduino.gui;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import com.genius.framework.common.logger.Logger;

public class GeniusLoggerAppender extends AppenderSkeleton {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected void append(LoggingEvent loggingEvent) {

		String message = loggingEvent.getRenderedMessage();

		switch (loggingEvent.getLevel().toInt()) {

		case Level.TRACE_INT:

			// Log info
			logger.info(message);
			break;

		case Level.DEBUG_INT:

			// Log debug
			logger.debug(message);
			break;

		case Level.WARN_INT:

			// Log warning
			logger.warning(message);
			break;

		case Level.ERROR_INT:
		case Level.FATAL_INT:

			// Get throwable information
			ThrowableInformation throwableInformation = loggingEvent
					.getThrowableInformation();

			if (throwableInformation != null) {

				// Log error
				logger.error(message, throwableInformation.getThrowable());
				break;

			} else {

				// Log error
				logger.error(message);
				break;

			}

		}

	}

	@Override
	public void close() {
		// NOTHING TO DO
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}
