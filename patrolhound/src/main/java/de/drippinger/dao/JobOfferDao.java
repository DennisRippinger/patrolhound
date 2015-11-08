package de.drippinger.dao;

import org.jooq.DSLContext;
import ro.isdc.wro.model.group.Inject;

/**
 * Created by dennisrippinger on 01.06.15.
 */
//@Stateless
public class JobOfferDao {
//
//	@Inject
//	private DSLContext jooq;
//
//	public List<JobOffer> findAllNonObsolete() {
//		List<JobOffer> result = jooq.select()
//				.from(JOB_OFFER)
//				.where(JOB_OFFER.OBSOLETE.eq(false))
//				.fetchInto(JobOffer.class);
//
//		return result;
//	}
//
//	public void makeObsolete(JobOffer jobOffer) {
//		jooq.update(JOB_OFFER)
//				.set(JOB_OFFER.OBSOLETE, true)
//				.where(JOB_OFFER.ID.eq(jobOffer.getId()));
//	}
//
//	public List<JobOffer> findAll() {
//		List<JobOffer> jobOffers = jooq.select().from(JOB_OFFER).fetchInto(JobOffer.class);
//
//		return jobOffers;
//	}
//
//	public void save(JobOffer jobOffer) {
//		JobOfferRecord jobOfferRecord = jooq.newRecord(JOB_OFFER, jobOffer);
//		jooq.executeInsert(jobOfferRecord);
//	}
//
//	public void batchSave(List<JobOffer> jobOffers) {
//
//	}
}