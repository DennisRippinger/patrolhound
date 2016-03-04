package de.drippinger.util;

import de.drippinger.dto.JobOffer;

import java.util.List;

/**
 * Similarity checks for Crawler related things.
 *
 * @author Dennis Rippinger
 */
public class Similarity {

	/**
	 * Check if a Job Offer is already known.
	 *
	 * @param jobOffers  the job offers
	 * @param currentJob the current job
	 * @return return true if already known
	 */
	public static Boolean isKnown(List<JobOffer> jobOffers, JobOffer currentJob) {
		for (JobOffer jobOffer : jobOffers) {
			if (jobOffer.getJobUrl().equals(currentJob.getJobUrl())) {
				return true;
			}
		}
		return false;
	}

}
