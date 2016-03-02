package de.drippinger.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;


/**
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


	public void setJobId(String jobId) {
		this.jobId = jobId;
		this.jobIdHash = jobId.hashCode();
	}

}
