package de.drippinger.job;

import de.drippinger.crawler.impl.MonsterCrawler;
import de.drippinger.dto.Company;
import de.drippinger.repository.CompanyRepository;
import de.drippinger.repository.TagRepository;
import de.drippinger.util.TfIdfCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Slf4j
@Named
public class CrawlerJob {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private transient TfIdfCalculator tfIdfCalculator;

	@Inject
	private transient TagRepository tagRepository;

	@Inject
	private transient CompanyRepository companyRepository;

	@Scheduled(cron = "0 0 4 ? * MON-FRI")
	public void execute() {

		// It's a prototype
		MonsterCrawler monsterCrawler = applicationContext.getBean(MonsterCrawler.class);

		log.info("Started Crawling Job");

		List<Company> companies = companyRepository.findAll();

		monsterCrawler.crawlJobs(companies, "");

		log.info("Truncating former Tags");

		tagRepository.truncateTags();

		log.info("Calculating TF/IDF for companies");

		tfIdfCalculator.calculateTfIdfForCompany(companies);
	}
}
