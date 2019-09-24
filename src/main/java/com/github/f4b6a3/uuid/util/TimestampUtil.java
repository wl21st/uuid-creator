/*
 * MIT License
 * 
 * Copyright (c) 2018-2019 Fabio Lima
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.f4b6a3.uuid.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Class that provides methods related to timestamps.
 * 
 * All its public methods have milliseconds precision.
 *
 */
public class TimestampUtil {

	public static final long GREGORIAN_MILLISECONDS = getGregorianEpochMilliseconds();

	public static final long MILLISECONDS_PER_SECOND = 1_000L;
	public static final long TIMESTAMP_RESOLUTION = 10_000L;

	private TimestampUtil() {
	}

	/**
	 * Get the current timestamp with milliseconds precision.
	 * 
	 * The UUID timestamp is a number of 100-nanos since gregorian epoch.
	 * 
	 * ### RFC-4122 - 4.2.1.2. System Clock Resolution
	 * 
	 * The timestamp is generated from the system time, whose resolution may be
	 * less than the resolution of the UUID timestamp.
	 * 
	 * If UUIDs do not need to be frequently generated, the timestamp can simply
	 * be the system time multiplied by the number of 100-nanosecond intervals
	 * per system time interval.
	 * 
	 * (4) A high resolution timestamp can be simulated by keeping a count of
	 * the number of UUIDs that have been generated with the same value of the
	 * system time, and using it to construct the low order bits of the
	 * timestamp. The count will range between zero and the number of
	 * 100-nanosecond intervals per system time interval.
	 * 
	 * @return the current timestamp
	 */
	public static long getCurrentTimestamp() {
		return toTimestamp(System.currentTimeMillis());
	}

	/**
	 * Get the timestamp of a given Unix Epoch milliseconds.
	 * 
	 * @param unixEpochMilliseconds
	 *            the Unix Epoch milliseconds
	 * @return a timestamp
	 */
	public static long toTimestamp(final long unixEpochMilliseconds) {
		return (unixEpochMilliseconds - GREGORIAN_MILLISECONDS) * TIMESTAMP_RESOLUTION;
	}

	/**
	 * Get the Unix Epoch milliseconds of a given timestmap
	 * 
	 * @param timestamp
	 *            a timestamp
	 * @return the Unix Epoch milliseconds
	 */
	public static long toUnixEpochMilliseconds(final long timestamp) {
		return (timestamp / TIMESTAMP_RESOLUTION) + GREGORIAN_MILLISECONDS;
	}

	/**
	 * Get the timestamp of a given instant with milliseconds precision.
	 *
	 * @param instant
	 *            an instant
	 * @return a timestamp
	 */
	public static long toTimestamp(final Instant instant) {
		return toTimestamp(instant.toEpochMilli());
	}

	/**
	 * Get the instant of the given timestamp with milliseconds precision.
	 *
	 * @param timestamp
	 *            a timestamp
	 * @return an instant
	 */
	public static Instant toInstant(final long timestamp) {
		return Instant.ofEpochMilli(toUnixEpochMilliseconds(timestamp));
	}

	/**
	 * Get the beginning of the Gregorian Calendar in milliseconds: 1582-10-15
	 * 00:00:00Z.
	 * 
	 * The expression "Gregorian Epoch" means the date and time the Gregorian
	 * Calendar started. This expression is similar to "Unix Epoch", started in
	 * 1970-01-01 00:00:00Z.
	 *
	 * @return the milliseconds since gregorian epoch
	 */
	private static long getGregorianEpochMilliseconds() {
		return LocalDate.parse("1582-10-15").atStartOfDay(ZoneId.of("UTC")).toInstant().getEpochSecond()
				* MILLISECONDS_PER_SECOND;
	}
}
