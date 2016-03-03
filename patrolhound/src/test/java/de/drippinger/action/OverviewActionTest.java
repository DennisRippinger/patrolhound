package de.drippinger.action;

import de.drippinger.dto.JobOffer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * OverviewActionTest
 *
 * @author Dennis Rippinger
 */
public class OverviewActionTest {

	private OverviewAction overviewAction;

	@Before
	public void before() throws Exception {
		overviewAction = Mockito.spy(new OverviewAction());

		Mockito.doReturn(prepareTestDate()).when(overviewAction).loadJobOffers();
	}

	/**
	 * Test loadSummary().
	 */
	@Test
	public void testLoadSummary() {
		Map<String, Double> summary = overviewAction.loadSummary();

		assertThat(summary).containsKeys("Tag 9", "Tag 8");
		assertThat(summary).containsValues(100.0, 90.0);
	}


	private List<JobOffer> prepareTestDate() {
		List<JobOffer> result = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			JobOffer offer = new JobOffer();
			offer.setJobTitle(String.format("Job %s", i));
			for (int t = i; t < 10; t++) {
				offer.addTag(String.format("Tag %s", t));
			}
			result.add(offer);
		}

		return result;
	}
}
