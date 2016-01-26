package de.drippinger.crawler;

import de.drippinger.dto.Company;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

public abstract class JobCrawler {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(JobCrawler.class);

	public abstract void crawlJobs(List<Company> companies, String keyword);

	public String getURL(String url, String company, String keyword) {
		return MessageFormat.format(url, replaceBlanks(company), replaceBlanks(keyword));
	}

	public String getURL(String url, String company, Integer pageNumber) {
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
