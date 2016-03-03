package de.drippinger.repository;

import de.drippinger.dto.Company;
import de.drippinger.dto.JobOffer;
import de.drippinger.generated.tables.records.CompanyRecord;
import de.drippinger.generated.tables.records.TagRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static de.drippinger.generated.Tables.*;


/**
 * The type Company repository.
 */
@Repository
public class CompanyRepository {

	@Inject
	private DSLContext jooq;


	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	public List<Company> findAll() {

		return jooq
			.select()
			.from(COMPANY)
			.where(COMPANY.LAST_UPDATE.isNotNull())
			.orderBy(COMPANY.LAST_UPDATE.desc())
			.fetchInto(Company.class);
	}

	/**
	 * Find company.
	 *
	 * @param companyID the company id
	 * @return the company
	 */
	public Company find(Long companyID) {
		return jooq
			.select()
			.from(COMPANY)
			.where(COMPANY.ID.eq(companyID))
			.fetchOneInto(Company.class);
	}

	/**
	 * Count open job offers for company integer.
	 *
	 * @param companyID the company id
	 * @return the integer
	 */
	public Integer countOpenJobOffersForCompany(Long companyID) {
		return jooq
			.selectCount()
			.from(JOB_OFFER)
			.where(JOB_OFFER.COMPANY_ID.eq(companyID))
			.and(JOB_OFFER.OBSOLETE.eq(false))
			.fetchOne(0, int.class);
	}

	/**
	 * Fetch tags for company list.
	 *
	 * @param company the company
	 * @return the list
	 */
	public List<String> fetchTagsForCompany(Company company) {

		return jooq.select(TAG.TAG_FIELD)
			.from(TAG)
			.join(TAG_FK)
			.on(TAG.ID.eq(TAG_FK.TAG_ID))
			.where(TAG_FK.COMPANY_ID.eq(company.getId()))
			.fetch()
			.getValues(TAG.TAG_FIELD);
	}

	/**
	 * Insert tags.
	 *
	 * @param tags     the tags
	 * @param company  the company
	 * @param jobOffer the job offer
	 */
	public void insertTags(Map<String, Double> tags, Company company, JobOffer jobOffer) {

		for (Map.Entry<String, Double> tagEntry : tags.entrySet()) {
			List<Long> values = jooq.select()
				.from(TAG)
				.where(TAG.TAG_FIELD.eq(tagEntry.getKey()))
				.fetch()
				.getValues(TAG.ID);
			if (values.isEmpty()) {
				insertNewTag(tagEntry, company, jobOffer);
			} else {
				insertReferenceOfTag(values.get(0), company, jobOffer, tagEntry.getValue());
			}
		}
	}

	/**
	 * Delete.
	 *
	 * @param company the company
	 */
	public void delete(Company company) {
		jooq
			.deleteFrom(COMPANY)
			.where(COMPANY.ID.eq(company.getId()))
			.execute();
	}

	/**
	 * Update last update time stamp.
	 *
	 * @param company the company
	 */
	public void updateLastUpdateTimeStamp(Company company) {
		jooq
			.update(COMPANY)
			.set(COMPANY.LAST_UPDATE, Instant.now())
			.where(COMPANY.ID.eq(company.getId()))
			.execute();
	}

	/**
	 * Updates a company.
	 *
	 * @param company the company
	 */
	public void update(Company company) {
		CompanyRecord companyRecord = jooq.newRecord(COMPANY, company);

		jooq.executeUpdate(companyRecord);
	}

	/**
	 * Save a company.
	 *
	 * @param company the company
	 */
	public void save(Company company) {
		if (company.getId() != null) {
			update(company);
		} else {
			CompanyRecord companyRecord = jooq.newRecord(COMPANY, company);
			jooq.executeInsert(companyRecord);
		}
	}

	private void insertReferenceOfTag(Long id, Company company, JobOffer jobOffer, Double tfidf) {
		jooq.insertInto(TAG_FK)
			.columns(TAG_FK.COMPANY_ID, TAG_FK.JOB_OFFER_ID, TAG_FK.TAG_ID, TAG_FK.TFIDF)
			.values(company.getId(), jobOffer.getId(), id, tfidf)
			.execute();
	}

	private void insertNewTag(Map.Entry<String, Double> tag, Company company, JobOffer jobOffer) {
		TagRecord tagID = jooq
			.insertInto(TAG)
			.columns(TAG.TAG_FIELD)
			.values(tag.getKey())
			.returning(TAG.ID)
			.fetchOne();
		Long id = tagID.getId();

		insertReferenceOfTag(id, company, jobOffer, tag.getValue());
	}
}
