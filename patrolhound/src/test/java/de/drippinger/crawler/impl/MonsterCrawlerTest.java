package de.drippinger.crawler.impl;

import de.drippinger.PatrolHound;
import de.drippinger.dto.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * MonsterCrawlerTest
 *
 * @author Dennis Rippinger
 * @since 08.11.15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PatrolHound.class)
public class MonsterCrawlerTest {

	@Resource
	private MonsterCrawler crawler;

	@Test
	public void testCrawlJobs() throws Exception {
		List<Company> companies = new ArrayList<Company>() {
			{
				Company company = new Company();
				company.setName("ALDI");
				add(company);
			}
		};

		crawler.crawlJobs(companies, "");
	}
}