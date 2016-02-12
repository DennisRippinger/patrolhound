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
	private transient JobOfferRepository jobOfferRepository;

	@Inject
	private transient CompanyRepository companyRepository;

	@Getter
	private List<JobOffer> allJobOffers;

	@Getter
	private List<JobOffer> newJobOffers;

	@Inject
	private TfIdfCalculator tfIdfCalculator;

	public void crawlJobs() {
		log.info("Crawl info pressed");

		List<Company> companies = companyRepository.findAll();

		crawlerMonster.crawlJobs(companies, "");

		tfIdfCalculator.calculateTfIDForCompany(companies.get(0));
	}

	@PostConstruct
	public void initPage() {
		log.info("Home Action inited");

		// TODO Add Cache
		allJobOffers = jobOfferRepository.findAllNonObsolete();
		if (!allJobOffers.isEmpty()) {


			//allJobOffers.sort(Comparator.comparing(JobOffer::getJobAnnouncementTime).reversed());

//			newJobOffers = allJobOffers
//					.stream()
//					.filter(
//							x -> x.getJobAnnouncementTime()
//									.isAfter(
//											LocalDateTime.now().minus(Duration.ofDays(2))
//									)
//					)
//					.collect(Collectors.toList());
		}
	}

}
