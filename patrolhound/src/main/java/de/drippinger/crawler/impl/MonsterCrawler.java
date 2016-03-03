package de.drippinger.crawler.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import de.drippinger.crawler.CrawlerUtil;
import de.drippinger.crawler.JobCrawler;
import de.drippinger.dto.Company;
import de.drippinger.dto.JobOffer;
import de.drippinger.exception.CrawlerException;
import de.drippinger.repository.CompanyRepository;
import de.drippinger.repository.JobOfferRepository;
import de.drippinger.util.Similarity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * The Monster.de crawler.
 */
@Slf4j
@Service
public class MonsterCrawler extends JobCrawler {

	private static final String MONSTER_URL = "http://jobsuche.monster.de/Jobs/IT-Informationstechnologie_4?q=\"{0}\"&pg={1}";

	@Inject
	private JobOfferRepository jobOfferRepository;

	@Inject
	private CompanyRepository companyRepository;

	private MonsterJobPageCrawler monsterJobPageCrawler;

	@Override
	public void crawlJobs(List<Company> companies, String keyword) {

		log.info("Starting Monster Crawling");

		monsterJobPageCrawler = new MonsterJobPageCrawler();

		List<JobOffer> jobOffers = jobOfferRepository.findAllNonObsolete();
		List<JobOffer> knownJobOffers = new ArrayList<>();

		WebClient webClient = CrawlerUtil.getRandomDesktopWebClient(false, false);

		for (Company company : companies) {
			Integer counter = 1;

			String monsterUrl = createURL(MONSTER_URL, company.getName(), counter++);
			HtmlPage monsterPage;

			Boolean hasChanges = false;

			try {
				monsterPage = CrawlerUtil.getWebPage(webClient, monsterUrl, 0);
				hasChanges |= extractJobOffers(jobOffers, knownJobOffers, monsterPage, company, webClient);

				while (hasNext(monsterPage)) {
					monsterUrl = createURL(MONSTER_URL, company.getName(), counter++);
					monsterPage = CrawlerUtil.getWebPage(webClient, monsterUrl, 0);

					hasChanges |= extractJobOffers(jobOffers, knownJobOffers, monsterPage, company, webClient);
				}

			} catch (CrawlerException e) {
				log.error("Could not load Monster Page", e);
			}

			if (hasChanges) {
				companyRepository.updateLastUpdateTimeStamp(company);
			}

		}

		// Uncheck no longer listed job offers
		jobOffers.removeAll(knownJobOffers);
		jobOffers.forEach(jobOfferRepository::makeObsolete);

		log.info("Unchecked {} no longer listed jobs", jobOffers.size());
	}

	private boolean hasNext(HtmlPage monsterPage) {
		DomElement domNextPage = monsterPage.getFirstByXPath("//*[@id='page_navigation']/div/span[@class='nextLink fnt13']");
		DomElement pageNavigation = monsterPage.getFirstByXPath("//*[@id='page_navigation']");

		return pageNavigation != null && domNextPage == null;
	}

	private Boolean extractJobOffers(List<JobOffer> jobOffers, List<JobOffer> knownJobOffers, HtmlPage monsterPage, Company company, WebClient webClient) {
		List<DomElement> divJobOffers = (List<DomElement>) monsterPage.getByXPath("//*[@class='jobTitleCol fnt4']");

		Boolean hasChanges = false;

		for (DomElement divJobOffer : divJobOffers) {

			String companyName = getCompanyName(divJobOffer);
			if (!companyName.toLowerCase().contains(company.getName().toLowerCase())) {
				continue;
			}

			String jobID = getJobID(divJobOffer);
			String jobTitle = getJobTitle(divJobOffer);
			String jobURL = getJobURL(divJobOffer);
			Instant jobAnnouncementTime = getJobAnnouncementTime(divJobOffer);

			JobOffer jobOffer = new JobOffer();
			jobOffer.setJobId(jobID);
			jobOffer.setCompanyId(company.getId());
			jobOffer.setJobTitle(jobTitle);
			jobOffer.setJobUrl(jobURL);
			jobOffer.setCompanyName(companyName);
			jobOffer.setJobAnnouncementTime(jobAnnouncementTime);
			jobOffer.setObsolete(false);

			Boolean isKnown = Similarity.isKnown(jobOffers, knownJobOffers, jobOffer);

			if (!isKnown) {

				getJobDescription(jobOffer, webClient);

				jobOfferRepository.save(jobOffer);
				log.info("Saved new Job Offer {}", jobOffer.getJobTitle());

				hasChanges = true;
			} else {
				log.info("Job {}, was already known", jobOffer.getJobTitle());
			}

		}
		return hasChanges;
	}

	private void getJobDescription(JobOffer jobOffer, WebClient webClient) {
		monsterJobPageCrawler.crawlJobOffer(jobOffer, webClient);
	}


	private Instant getJobAnnouncementTime(DomElement divJobOffer) {
		DomElement domAnnouncementTime = divJobOffer.getFirstByXPath(".//div[@class='fnt20']");
		String announcementTime = domAnnouncementTime.asText();
		if (announcementTime.contains("Heute")) {
			return Instant.now();
		} else if (announcementTime.contains("Tagen")) {
			return extractTimeDifference(announcementTime, ChronoUnit.DAYS);
		} else if (announcementTime.contains("Wochen")) {
			return extractTimeDifference(announcementTime, ChronoUnit.WEEKS);
		} else {
			// Unknown Case, but hey, it's a crawler. Strange things can happen.
			return Instant.now();
		}
	}

	private Instant extractTimeDifference(String announcementTime, ChronoUnit timeUnit) {
		List<Integer> result = CrawlerUtil.extractPositiveNumbersFromString(announcementTime);
		if (!result.isEmpty()) {
			Integer daysMinus = result.get(0);
			return Instant.now().minus(daysMinus, timeUnit);
		}

		return Instant.now();
	}

	private String getCompanyName(DomElement divJobOffer) {
		DomElement domCompanyName = divJobOffer.getFirstByXPath(".//div[@class='companyContainer']/div/a[2]");
		return domCompanyName.getAttribute("title");
	}

	private String getJobTitle(DomElement divJobOffer) {
		DomElement domJobTitle = divJobOffer.getFirstByXPath(".//div[@class='jobTitleContainer']/a");

		return domJobTitle.asText();
	}

	private String getJobURL(DomElement divJobOffer) {
		DomElement domJobTitle = divJobOffer.getFirstByXPath(".//div[@class='jobTitleContainer']/a");

		// The URL comes with some type of referral, we remove that by splitting the URL into path and attributes.
		String href = domJobTitle.getAttribute("href");
		String[] url = href.split("\\?");

		return url[0];
	}

	private String getJobID(DomElement divJobOffer) {
		DomElement domJobID = divJobOffer.getFirstByXPath(".//div[@class='jobTitleContainer']/a");
		String jobURL = domJobID.getAttribute("href");
		jobURL = jobURL.replace("http://stellenanzeige.monster.de:80/", "");
		String[] splitJobID = jobURL.split("\\?");

		return splitJobID[0];
	}

}
