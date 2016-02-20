package de.drippinger.repository;

import de.drippinger.dto.Company;
import de.drippinger.dto.JobOffer;
import de.drippinger.generated.tables.records.CompanyRecord;
import de.drippinger.generated.tables.records.TagRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static de.drippinger.generated.Tables.*;


@Repository
public class CompanyRepository {

	@Inject
	private DSLContext jooq;


	public List<Company> findAll() {

		List<Company> companies = jooq
			.select()
			.from(COMPANY)
			.fetchInto(Company.class);

		return companies;
	}

	public Company find(Long companyID) {
		Company company = jooq
			.select()
			.from(COMPANY)
			.where(COMPANY.ID.eq(companyID))
			.fetchOneInto(Company.class);

		return company;
	}

	public Integer countOpenJobOffersForCompany(Long companyID) {
		Integer result = jooq
			.selectCount()
			.from(JOB_OFFER)
			.where(JOB_OFFER.COMPANY_ID.eq(companyID))
			.and(JOB_OFFER.OBSOLETE.eq(false))
			.fetchOne(0, int.class);

		return result;
	}

	public List<String> fetchTagsForCompany(Company company) {

		List<String> result = jooq.select(TAG.TAG_FIELD)
			.from(TAG)
			.join(TAG_FK)
			.on(TAG.ID.eq(TAG_FK.TAG_ID))
			.where(TAG_FK.COMPANY_ID.eq(company.getId()))
			.fetch()
			.getValues(TAG.TAG_FIELD);

		return result;
	}

	public void insertTags(List<String> tags, Company company, JobOffer jobOffer) {

		for (String stringTag : tags) {
			List<Long> values = jooq.select()
				.from(TAG)
				.where(TAG.TAG_FIELD.eq(stringTag))
				.fetch()
				.getValues(TAG.ID);
			if (values.isEmpty()) {
				insertNewTag(stringTag, company, jobOffer);
			} else {
				insertReferenceOfTag(values.get(0), company, jobOffer);
			}
		}
	}

	public void delete(Company company) {
		jooq
			.deleteFrom(COMPANY)
			.where(COMPANY.ID.eq(company.getId()))
			.execute();
	}

	public void updateLAstUpdateTimeStamp(Company company) {
		jooq
			.update(COMPANY)
			.set(COMPANY.LAST_UPDATE, Instant.now())
			.where(COMPANY.ID.eq(company.getId()))
			.execute();
	}

	public void update(Company company) {
		CompanyRecord companyRecord = jooq.newRecord(COMPANY, company);

		jooq.executeUpdate(companyRecord);
	}

	public void save(Company company) {
		CompanyRecord companyRecord = jooq.newRecord(COMPANY, company);

		jooq.executeInsert(companyRecord);
	}


	private void insertReferenceOfTag(Long id, Company company, JobOffer jobOffer) {
		jooq.insertInto(TAG_FK)
			.columns(TAG_FK.COMPANY_ID, TAG_FK.JOB_OFER_ID, TAG_FK.TAG_ID)
			.values(company.getId(), jobOffer.getId(), id)
			.execute();
	}

	private void insertNewTag(String tag, Company company, JobOffer jobOffer) {
		TagRecord tagID = jooq
			.insertInto(TAG)
			.columns(TAG.TAG_FIELD)
			.values(tag)
			.returning(TAG.ID)
			.fetchOne();
		Long id = tagID.getId();

		insertReferenceOfTag(id, company, jobOffer);
	}
}
