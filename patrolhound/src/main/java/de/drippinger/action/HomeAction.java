package de.drippinger.action;

import de.drippinger.crawler.impl.MonsterCrawler;
import de.drippinger.dto.Company;
import de.drippinger.dto.JobOffer;
import de.drippinger.repository.CompanyRepository;
import de.drippinger.repository.JobOfferRepository;
import de.drippinger.util.TfIdfCalculator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Class for Home view based actions.
 *
 * @author Dennis Rippinger
 */
@Slf4j
@Named
@Scope("session")
public class HomeAction implements Serializable {

	private static final long serialVersionUID = 9086854678284589128L;

	@Inject
	private transient MonsterCrawler crawlerMonster;

	@Inject
	private transient CompanyRepository companyRepository;

	@Inject
	private TfIdfCalculator tfIdfCalculator;

	public void crawlJobs() {

		List<Company> companies = companyRepository.findAll();

		crawlerMonster.crawlJobs(companies, "");

		tfIdfCalculator.calculateTfIDForCompany(companies.get(0));
	}


}
