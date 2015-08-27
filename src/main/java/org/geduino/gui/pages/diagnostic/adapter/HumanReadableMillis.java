package org.geduino.gui.pages.diagnostic.adapter;

import com.genius.framework.common.locale.LocalizedMessage;

class HumanReadableMillis {

	private static final LocalizedMessage MILLIS = new LocalizedMessage(
			"time.millis");
	private static final LocalizedMessage SECONDS = new LocalizedMessage(
			"time.seconds");
	private static final LocalizedMessage MINUTES = new LocalizedMessage(
			"time.minutes");
	private static final LocalizedMessage HOURS = new LocalizedMessage(
			"time.hours");

	private static final long MILLIS_IN_SECOND = 1000;
	private static final long MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
	private static final long MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60;

	static String humanize(long millis) {

		StringBuffer stringBuffer = new StringBuffer();

		if (millis > MILLIS_IN_HOUR) {

			// Get hours
			long hours = millis / MILLIS_IN_HOUR;

			// Add hours to string
			stringBuffer.append(HOURS.getMessage(hours));

			// Decrease hours from millis
			millis -= hours * MILLIS_IN_HOUR;

		}

		if (millis > MILLIS_IN_MINUTE) {

			// Get minutes
			long minutes = millis / MILLIS_IN_MINUTE;

			// Add minutes to string
			stringBuffer.append(stringBuffer.length() > 0 ? ", " : "").append(
					MINUTES.getMessage(minutes));

			// Decrease minutes from millis
			millis -= minutes * MILLIS_IN_MINUTE;

		}

		if (millis > MILLIS_IN_SECOND) {

			// Get seconds
			long seconds = millis / MILLIS_IN_SECOND;

			// Add seconds to string
			stringBuffer.append(stringBuffer.length() > 0 ? ", " : "").append(
					SECONDS.getMessage(seconds));

			// Decrease seconds from millis
			millis -= seconds * MILLIS_IN_SECOND;

		}

		// Add millis to string
		stringBuffer.append(stringBuffer.length() > 0 ? ", " : "").append(
				MILLIS.getMessage(millis));

		return stringBuffer.toString();

	}

}
