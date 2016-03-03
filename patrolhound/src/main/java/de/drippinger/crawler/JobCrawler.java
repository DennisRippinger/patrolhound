package de.drippinger.crawler;

import de.drippinger.dto.Company;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

/**
 * The type Job crawler.
 */
@Slf4j
public abstract class JobCrawler {

	/**
	 * Crawl jobs.
	 *
	 * @param companies the companies list of companies as target
	 * @param keyword   the keyword keyword is optional, some job pages do not allow to search in categories, therefore it is required to a a general IT related term to focus the search.
	 */
	public abstract void crawlJobs(List<Company> companies, String keyword);


	/**
	 * Creates the URL.
	 *
	 * @param url        the url
	 * @param company    the company
	 * @param pageNumber the page number
	 * @return the url
	 */
	protected String createURL(String url, String company, Integer pageNumber) {
		return MessageFormat.format(url, replaceBlanks(company), pageNumber);
	}

	private String replaceBlanks(String input) {
		try {
			return URLEncoder.encode(input, "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
