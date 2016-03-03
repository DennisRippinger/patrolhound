package de.drippinger.repository;

import de.drippinger.generated.Sequences;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

import static de.drippinger.generated.Tables.TAG;
import static de.drippinger.generated.Tables.TAG_FK;

/**
 * TagRepository
 *
 * @author Dennis Rippinger
 */
@Slf4j
@Repository
public class TagRepository {

	@Inject
	private DSLContext jooq;

	public void truncateTags() {
		jooq.truncate(TAG);
		jooq.truncate(TAG_FK);

		jooq.alterSequence(Sequences.SEQ_TAG).restart();
		jooq.alterSequence(Sequences.SEQ_TAG_FK).restart();
	}

	public List<String> findTagsForJobOffer(Long jobOfferID, Integer limit) {
		List<String> result = jooq
			.select(TAG.TAG_FIELD)
			.from(TAG_FK)
			.innerJoin(TAG)
			.on(TAG_FK.TAG_ID.eq(TAG.ID))
			.where(TAG_FK.JOB_OFFER_ID.eq(jobOfferID))
			.orderBy(TAG_FK.TFIDF.desc())
			.limit(limit)
			.fetchInto(String.class);

		return result;
	}

}
