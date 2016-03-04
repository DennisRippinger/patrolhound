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
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

/**
 * The Monster.de crawler.
 */
@Slf4j
@Service
@Scope("prototype")
public class MonsterCrawler extends JobCrawler {

	private static final String MONSTER_URL = "http://jobs.monster.de/c-{0}-q-it-jobs.aspx?page={1}";

	@Inject
	private JobOfferRepository jobOfferRepository;

	@Inject
	private CompanyRepository companyRepository;

	private MonsterJobPageCrawler monsterJobPageCrawler;

	private List<JobOffer> currentJobOffers = new ArrayList<>();

	private List<JobOffer> knownJobOffers;

	@Override
	public void crawlJobs(List<Company> companies, String keyword) {

		log.info("Starting Monster Crawling");

		monsterJobPageCrawler = new MonsterJobPageCrawler();

		knownJobOffers = jobOfferRepository.findAllNonObsolete();

		WebClient webClient = CrawlerUtil.getRandomDesktopWebClient(false, false);

		for (Company company : companies) {
			Integer counter = 1;

			String monsterUrl = createURL(MONSTER_URL, company.getName(), counter++);
			HtmlPage monsterPage;

			Boolean hasChanges = false;

			try {
				monsterPage = CrawlerUtil.getWebPage(webClient, monsterUrl, 0);
				hasChanges |= extractJobOffers(monsterPage, company, webClient);

				while (hasNext(monsterPage)) {
					monsterUrl = createURL(MONSTER_URL, company.getName(), counter++);
					monsterPage = CrawlerUtil.getWebPage(webClient, monsterUrl, 0);

					hasChanges |= extractJobOffers(monsterPage, company, webClient);
				}

			} catch (CrawlerException e) {
				log.error("Could not load Monster Page", e);
			}

			if (hasChanges) {
				companyRepository.updateLastUpdateTimeStamp(company);
			}

		}

		markKnownJobsAsObsolete();
	}

	private void markKnownJobsAsObsolete() {
		this.knownJobOffers.removeAll(currentJobOffers);
		jobOfferRepository.makeObsolete(this.knownJobOffers);

		log.info("Unchecked {} no longer listed jobs", knownJobOffers.size());
	}

	private boolean hasNext(HtmlPage monsterPage) {
		DomElement domNextPage = monsterPage.getFirstByXPath("//*[@class='page-link next']");
		DomElement pageNavigation = monsterPage.getFirstByXPath("//div[@class='pagingWrapper']/div");

		return pageNavigation != null && domNextPage == null;
	}

	private Boolean extractJobOffers(HtmlPage monsterPage, Company company, WebClient webClient) {
		List<DomElement> divJobOffers = (List<DomElement>) monsterPage.getByXPath("//section[@id='resultsWrapper']//article[@itemtype='http://schema.org/JobPosting']");

		Boolean hasChanges = false;

		for (DomElement divJobOffer : divJobOffers) {

			String companyName = getCompanyName(divJobOffer);
			if (!companyName.toLowerCase().contains(company.getName().toLowerCase())) {
				continue;
			}

			String jobTitle = getJobTitle(divJobOffer);
			String jobURL = getJobURL(divJobOffer);
			String jobLocation = getJobLocation(divJobOffer);
			Instant jobAnnouncementTime = getJobAnnouncementTime(divJobOffer);

			JobOffer jobOffer = new JobOffer();
			jobOffer.setCompanyId(company.getId());
			jobOffer.setJobTitle(jobTitle);
			jobOffer.setJobUrl(jobURL);
			jobOffer.setJobLocation(jobLocation);
			jobOffer.setCompanyName(companyName);
			jobOffer.setJobAnnouncementTime(jobAnnouncementTime);
			jobOffer.setObsolete(false);

			currentJobOffers.add(jobOffer);

			Boolean isKnown = Similarity.isKnown(knownJobOffers, jobOffer);

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

	private String getJobLocation(DomElement divJobOffer) {
		DomElement domLocation = divJobOffer.getFirstByXPath(".//span[@itemprop='address']");
		if (domLocation != null) {
			return StringUtils.trim(domLocation.getTextContent());
		}
		return StringUtils.EMPTY;
	}

	private void getJobDescription(JobOffer jobOffer, WebClient webClient) {
		monsterJobPageCrawler.crawlJobOffer(jobOffer, webClient);
	}


	private Instant getJobAnnouncementTime(DomElement divJobOffer) {
		DomElement domAnnouncementTime = divJobOffer.getFirstByXPath(".//*[@itemprop='datePosted']");
		String announcementTime = domAnnouncementTime.getAttribute("datetime");
		TemporalAccessor time = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault()).parse(announcementTime);

		return Instant.from(time);
	}

	private String getCompanyName(DomElement divJobOffer) {
		DomElement domCompanyName = divJobOffer.getFirstByXPath(".//div[@itemscope='http://schema.org/Organization']/a");
		if (domCompanyName != null) {
			return domCompanyName.getAttribute("title");
		} else {
			domCompanyName = divJobOffer.getFirstByXPath(".//*[@itemprop='name']");
			String companyName = domCompanyName.getTextContent();
			return companyName.replace("Gefunden bei: ", "");
		}
	}

	private String getJobTitle(DomElement divJobOffer) {
		DomElement domJobTitle = divJobOffer.getFirstByXPath(".//*[@itemprop='title']");

		return domJobTitle.asText();
	}

	private String getJobURL(DomElement divJobOffer) {
		DomElement domJobTitle = divJobOffer.getFirstByXPath(".//h2/a");

		// The URL comes with some type of referral, we remove that by splitting the URL into path and attributes.
		String href = domJobTitle.getAttribute("href");
		String[] url = href.split("\\?");

		return url[0];
	}

}
