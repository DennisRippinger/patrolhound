package de.drippinger.repository;

import de.drippinger.dto.JobOffer;
import de.drippinger.generated.tables.records.JobOfferRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

import static de.drippinger.generated.Tables.JOB_OFFER;


@Repository
public class JobOfferRepository {

	@Inject
	private DSLContext jooq;

	public List<JobOffer> findAllNonObsolete() {
		List<JobOffer> result = jooq.select()
			.from(JOB_OFFER)
			.where(JOB_OFFER.OBSOLETE.eq(false))
			.fetchInto(JobOffer.class);

		return result;
	}

	public void makeObsolete(JobOffer jobOffer) {
		jooq.update(JOB_OFFER)
			.set(JOB_OFFER.OBSOLETE, true)
			.where(JOB_OFFER.ID.eq(jobOffer.getId()));
	}

	public List<JobOffer> findAll() {
		List<JobOffer> jobOffers = jooq.select().from(JOB_OFFER).fetchInto(JobOffer.class);

		return jobOffers;
	}

	public void save(JobOffer jobOffer) {
		JobOfferRecord jobOfferRecord = jooq.newRecord(JOB_OFFER, jobOffer);
		jooq.executeInsert(jobOfferRecord);
	}

	public void batchSave(List<JobOffer> jobOffers) {

	}
}
