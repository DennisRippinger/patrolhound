package de.drippinger.crawler;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import de.drippinger.exception.CrawlerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class CrawlerUtil provides different Browser Version and wait times to avoid a CAPTCHA.
 *
 * @author Dennis Rippinger
 */
@Slf4j
public final class CrawlerUtil {

	private static List<BrowserVersion> browserVersionDesktop = new LinkedList<>();

	static {
		browserVersionDesktop.add(BrowserVersion.CHROME);
		browserVersionDesktop.add(BrowserVersion.FIREFOX_31);
		browserVersionDesktop.add(BrowserVersion.INTERNET_EXPLORER_11);

		// Maybe add mobile browsers
	}

	/**
	 * Private Constructor
	 */
	private CrawlerUtil() {

	}

	public static WebClient getRandomDesktopWebClient(Boolean javascript, Boolean css) {
		BrowserVersion browserVersion = browserVersionDesktop.get(ThreadLocalRandom.current().nextInt(0, 2));
		log.info("Creating new '{}' Browser.", browserVersion.getApplicationVersion());
		WebClient webClient = new WebClient(browserVersion);
		webClient.getOptions().setJavaScriptEnabled(javascript);
		webClient.getOptions().setCssEnabled(css);

		return webClient;
	}

	public static HtmlPage getWebPage(WebClient client, String url, Integer wait) throws CrawlerException {
		HtmlPage page;
		try {
			waitAroundTimeInSeconds(wait);
			page = client.getPage(url);

		} catch (FailingHttpStatusCodeException | IOException e) {
			// Retry
			waitAroundTimeInSeconds(5);
			try {
				page = client.getPage(url);
			} catch (FailingHttpStatusCodeException | IOException e1) {
				throw new CrawlerException("HTTP Error", e1);
			}

		}
		log.info("Page Title: '{}' for '{}'", page.getTitleText(), url);

		return page;
	}

	public static List<Integer> extractPositiveNumbersFromString(String input) {
		List<Integer> result = new LinkedList<>();
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(input);
		while (m.find()) {
			String found = m.group();
			result.add(Integer.parseInt(found));
		}

		return result;
	}

	public static void waitAroundTimeInSeconds(Integer second) {
		Double dSecond = second * 1.0;
		NormalDistribution distribution = new NormalDistribution(dSecond, 0.3);
		Double sample = distribution.sample();

		waitInMillisecondsSeconds((int) (sample * 1000));
	}

	private static void waitInMillisecondsSeconds(Integer milliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.error("Error interrupting sleep time", e);
		}
	}

}
