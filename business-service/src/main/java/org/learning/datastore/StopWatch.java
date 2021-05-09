package org.learning.datastore;

import java.time.Duration;
import java.time.LocalTime;

public class StopWatch {
	LocalTime startTime;
	LocalTime endTime;

	public static StopWatch of() {
		return new StopWatch();
	}

	public StopWatch start() {
		startTime = LocalTime.now();
		return this;
	}

	public Duration stop() {
		if (endTime == null) {
			endTime = LocalTime.now();
		}
		return Duration.between(startTime, endTime);
	}

}
