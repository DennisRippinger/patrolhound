package de.drippinger.converter.jooq;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * InstantConverter Tester.
 */
public class InstantConverterTest {

	private InstantConverter instantConverter = new InstantConverter();

	private Instant now = Instant.now();

	@Test
	public void testFrom() throws Exception {

		Instant from = instantConverter.from(Timestamp.from(now));
		assertThat(from).isEqualTo(now);

		from = instantConverter.from(null);
		assertThat(from).isNull();
	}

	@Test
	public void testTo() throws Exception {
		Timestamp to = instantConverter.to(now);
		assertThat(to).isEqualTo(Timestamp.from(now));

		to = instantConverter.to(null);
		assertThat(to).isNull();
	}

}
