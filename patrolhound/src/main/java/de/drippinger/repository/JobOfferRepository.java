package de.drippinger.repository;

import de.drippinger.dto.JobOffer;
import de.drippinger.generated.tables.records.JobOfferRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

import static de.drippinger.generated.Tables.JOB_OFFER;


/**
 * The type Job offer repository.
 */
@Slf4j
@Repository
public class JobOfferRepository {

	@Inject
	private DSLContext jooq;

	/**
	 * Find all non obsolete list.
	 *
	 * @return the list
	 */
	public List<JobOffer> findAllNonObsolete() {
		return jooq.select()
			.from(JOB_OFFER)
			.where(JOB_OFFER.OBSOLETE.eq(false))
			.fetchInto(JobOffer.class);
	}

	/**
	 * Find all non obsolete list.
	 *
	 * @param companyID the company id
	 * @return the list
	 */
	public List<JobOffer> findAllNonObsolete(Long companyID) {
		log.debug("Loading all non obsolete job offers from company {}", companyID);
		return jooq.select()
			.from(JOB_OFFER)
			.where(JOB_OFFER.COMPANY_ID.eq(companyID))
			.and(JOB_OFFER.OBSOLETE.eq(false))
			.orderBy(JOB_OFFER.JOB_ANNOUNCEMENT_TIME.desc())
			.fetchInto(JobOffer.class);
	}


	/**
	 * Make obsolete.
	 *
	 * @param jobOffers the job offers
	 */
	public void makeObsolete(List<JobOffer> jobOffers) {

		for (JobOffer jobOffer : jobOffers) {
			jooq
				.update(JOB_OFFER)
				.set(JOB_OFFER.OBSOLETE, true)
				.where(JOB_OFFER.ID.eq(jobOffer.getId()))
				.execute();
		}


	}

	/**
	 * Find all list.
	 *
	 * @param companyID the company id
	 * @return the list
	 */
	public List<JobOffer> findAll(Long companyID) {
		return jooq
			.select()
			.from(JOB_OFFER)
			.where(JOB_OFFER.COMPANY_ID.eq(companyID))
			.fetchInto(JobOffer.class);
	}

	/**
	 * Save a job offer.
	 *
	 * @param jobOffer the job offer
	 */
	public void save(JobOffer jobOffer) {
		JobOfferRecord jobOfferRecord = jooq.newRecord(JOB_OFFER, jobOffer);
		jooq.executeInsert(jobOfferRecord);
	}

}
