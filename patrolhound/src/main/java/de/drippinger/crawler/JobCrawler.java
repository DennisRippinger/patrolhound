package de.drippinger.crawler;

import de.drippinger.dto.Company;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

@Slf4j
public abstract class JobCrawler {

	public abstract void crawlJobs(List<Company> companies, String keyword);
	
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
