package de.drippinger.util;

import de.drippinger.dto.JobOffer;
import org.apache.commons.lang3.StringUtils;

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
	 * @param knownJobs  the known jobs
	 * @param currentJob the current job
	 * @return return true if already known
	 */
	public static Boolean isKnown(List<JobOffer> jobOffers, List<JobOffer> knownJobs, JobOffer currentJob) {
		for (JobOffer jobOffer : jobOffers) {
			if (jobOffer.getJobIdHash() == currentJob.getJobIdHash()) {
				knownJobs.add(jobOffer);
				return true;
				// TODO Evaluate usefulness later
			} else if (StringUtils.getLevenshteinDistance(jobOffer.getJobId(), currentJob.getJobId()) < 2) {
				return true;
			}
		}
		return false;
	}
}
