package de.drippinger.crawler.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import de.drippinger.crawler.CrawlerUtil;
import de.drippinger.dto.JobOffer;
import de.drippinger.exception.CrawlerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;


/**
 * The type Monster job page crawler.
 */
@Slf4j
public class MonsterJobPageCrawler {

	/**
	 * Crawl individual job offer text.
	 *
	 * @param jobOffer  the job offer
	 * @param webClient the web client
	 */
	public void crawlJobOffer(JobOffer jobOffer, WebClient webClient) {

		try {
			HtmlPage webPage = CrawlerUtil.getWebPage(webClient, jobOffer.getJobUrl(), 0);

			DomElement domJobOffer = webPage.getFirstByXPath("//div[@id='monsterAppliesContentHolder']");
			DomElement domJobView = webPage.getFirstByXPath("//*[@id='jobview']");

			String jobDescription = StringUtils.EMPTY;

			if (domJobOffer != null) {
				// Remove JS Bar at the left
				Node iaactionFixed = webPage.getFirstByXPath("//div[@id='iaactionfixed']");
				if (iaactionFixed != null) {
					try {
						domJobOffer.removeChild(iaactionFixed);

						jobDescription = domJobOffer.getTextContent();
					} catch (DOMException e) {
						jobDescription = webPage.getTextContent();
					}
				}

			} else if (domJobView != null) {
				jobDescription = domJobView.getTextContent();
			} else {
				// Take hole page if layout is unknown
				jobDescription = webPage.getTextContent();
			}

			jobOffer.setDescription(StringUtils.trim(jobDescription));

			if (jobOffer.getDescription().equals(StringUtils.EMPTY)) {
				log.warn("Empty: {}", jobOffer.getJobUrl());
			}

		} catch (CrawlerException e) {
			log.error("Could not load Monster Page", e);
		}


	}

}
