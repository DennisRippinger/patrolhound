package de.drippinger.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static de.drippinger.generated.Tables.*;

/**
 * StopWordRepository
 *
 * @author Dennis Rippinger
 */
@Repository
public class StopWordRepository {

	@Inject
	private DSLContext jooq;

	/**
	 * Find all stop words set.
	 *
	 * @return the set
	 */
	public Set<String> findAllStopWords() {
		return jooq
			.select(SW_STOPWORD.STOPWORD.lower())
			.from(SW_STOPWORD)
			.fetch()
			.intoSet(0, String.class);
	}

	/**
	 * Find all regex list.
	 *
	 * @return the list
	 */
	public List<String> findAllRegex() {
		return jooq
			.select(SW_REGEX.REGEX)
			.from(SW_REGEX)
			.fetchInto(String.class);
	}

	/**
	 * Find all locations set.
	 *
	 * @return the set
	 */
	public Set<String> findAllLocations() {
		return jooq
			.select(SW_LOCATIONS.LOCATION.lower())
			.from(SW_LOCATIONS)
			.fetch()
			.intoSet(0, String.class);
	}
}
