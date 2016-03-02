package de.drippinger.crawler.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import de.drippinger.crawler.CrawlerUtil;
import de.drippinger.dto.JobOffer;
import de.drippinger.exception.CrawlerException;
import org.slf4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;


public class MonsterJobPageCrawler {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(MonsterJobPageCrawler.class);

	public void crawlJobOffer(JobOffer jobOffer, WebClient webClient) {

		try {
			HtmlPage webPage = CrawlerUtil.getWebPage(webClient, jobOffer.getJobUrl(), 0);

			DomElement domJobOffer = webPage.getFirstByXPath("//div[@id='monsterAppliesContentHolder']|//*[@id='jobview']");

			String jobDescription = "";

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

			} else {
				// Take hole page if layout is unknown
				jobDescription = webPage.getTextContent();
			}

			jobOffer.setDescription(jobDescription);

		} catch (CrawlerException e) {
			log.error("Could not load Monster Page", e);
		}
	}

}
