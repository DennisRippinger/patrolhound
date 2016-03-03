package de.drippinger.util;

import de.drippinger.dto.JobOffer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Similarity Tester.
 */
public class SimilarityTest {

	private List<JobOffer> jobOffers = new ArrayList<>();
	private List<JobOffer> knownJobs = new ArrayList<>();
	private JobOffer currentJob;

	@Before
	public void before() throws Exception {
		jobOffers.addAll(createSyntheticJobOffer(0, 10));
		knownJobs.addAll(createSyntheticJobOffer(0, 10));
		currentJob = createSingleSyntheticJobOffer(11);
	}

	/**
	 * Method: isKnown(List<JobOffer> jobOffers, List<JobOffer> knownJobs, JobOffer currentJob)
	 */
	@Test
	public void testIsKnown() throws Exception {
		Boolean known = Similarity.isKnown(jobOffers, knownJobs, currentJob);

		System.out.println(known);
	}

	private List<JobOffer> createSyntheticJobOffer(Integer idFrom, Integer idTo) {
		List<JobOffer> result = new ArrayList<>();

		for (Integer i = idFrom; i <= idTo; i++) {
			JobOffer syntheticJobOffer = createSingleSyntheticJobOffer(i);
			result.add(syntheticJobOffer);
		}

		return result;
	}

	private JobOffer createSingleSyntheticJobOffer(Integer i) {
		JobOffer offer = new JobOffer();
		offer.setJobId(String.format("Systemadministrator-in-Teilzeit-m-w--1000000%s.aspx", i));

		return offer;
	}


}
