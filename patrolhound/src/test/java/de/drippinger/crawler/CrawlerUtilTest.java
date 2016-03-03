package de.drippinger.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.w3c.dom.Element;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * CrawlerUtil Tester.
 */
public class CrawlerUtilTest {

	@Test
	public void testGetRandomDesktopWebClient() throws Exception {
		WebClient randomDesktopWebClient = CrawlerUtil.getRandomDesktopWebClient(true, true);

		assertThat(randomDesktopWebClient).isNotNull();
		assertThat(randomDesktopWebClient.getOptions().isJavaScriptEnabled()).isTrue();
		assertThat(randomDesktopWebClient.getOptions().isCssEnabled()).isTrue();
	}

	@Test
	public void testGetWebPage() throws Exception {
		HtmlPage webPage = CrawlerUtil.getWebPage(
			CrawlerUtil.getRandomDesktopWebClient(true, true),
			this.getClass().getResource("/test.html").toString(),
			0);

		assertThat(webPage).isNotNull();
		Element title = webPage.getHead().getFirstByXPath("//title");
		assertThat(title.getTextContent()).isEqualTo("Simple HTML file for test purpose");
	}

}
