package de.drippinger.converter;

import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TimestampConverter Tester.
 */
public class TimestampConverterTest {

	private TimestampConverter timestampConverter = new TimestampConverter();

	@Test
	public void testGetAsString() throws Exception {
		Instant now = Instant.now();
		String result = timestampConverter.getAsString(null, null, now);

		assertThat(result).contains(now.toString());
	}

}
