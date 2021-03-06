package de.drippinger.converter.jooq;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * The type Instant converter.
 */
public class InstantConverter implements Converter<Timestamp, Instant> {


	@Override
	public Instant from(Timestamp timestamp) {
		if (timestamp != null) {
			return timestamp.toInstant();
		}

		return null;
	}

	@Override
	public Timestamp to(Instant instant) {
		if (instant != null) {
			return Timestamp.from(instant);
		}
		return null;
	}

	@Override
	public Class<Timestamp> fromType() {
		return Timestamp.class;
	}

	@Override
	public Class<Instant> toType() {
		return Instant.class;
	}

}
