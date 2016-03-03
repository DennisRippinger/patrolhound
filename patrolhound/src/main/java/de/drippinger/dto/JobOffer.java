package de.drippinger.dto;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Job offer.
 *
 * @author Dennis Rippinger
 */
@Data
public class JobOffer {

	private Long id;

	private Long companyId;

	private String jobId;

	private Integer jobIdHash;

	private String jobUrl;

	private String jobTitle;

	private String companyName;

	private Instant jobAnnouncementTime;

	private String description;

	private Boolean obsolete;

	private List<String> tags;


	/**
	 * Sets job id.
	 *
	 * @param jobId the job id
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
		this.jobIdHash = jobId.hashCode();
	}

	/**
	 * Add a new tag.
	 *
	 * @param tag single tag.
	 */
	public void addTag(String tag) {
		if (tags == null) {
			tags = new ArrayList<>();
		}
		tags.add(tag);
	}

}
