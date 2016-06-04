package org.geduino.gui.service;

import java.io.IOException;

import com.genius.framework.common.bean.Bean;
import com.genius.framework.common.bean.BeanContext;
import com.genius.framework.common.cli.Cli;
import com.genius.framework.common.logger.Logger;

public class OSService extends Bean {

	private static final Logger LOGGER = Logger.getLogger(OSService.class);

	public static OSService getInstance() {
		return (OSService) BeanContext.getInstance(OSService.class, true);
	}

	@Override
	public void postConstructor() {
		// NOTHING TO DO
	}

	@Override
	public void preDestroy() throws InterruptedException {
		// NOTHING TO DO
	}

	public void shutdown() throws ShutdownException {

		// Log
		LOGGER.info("executing shutdown...");

		try {

			// Execute shutdown script
			int exitValue = new Cli().command("sudo").command("shutdown")
					.command("-P").command("now").executeAndWait();

			// Log
			LOGGER.info("process exit value is: " + exitValue);

			if (exitValue != 0) {

				throw new ShutdownException(
						"an error occurred waiting for shutdown process..."
								+ exitValue);

			}

		} catch (IOException ex) {

			throw new ShutdownException(
					"an error occurred in shutdown process...", ex);

		} catch (InterruptedException ex) {

			throw new ShutdownException(
					"an error occurred in shutdown process...", ex);

		}

	}

}
