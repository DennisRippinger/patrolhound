package de.drippinger.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class JobOffer {

	private Long id;

	private Long companyId;

	private String jobID;

	private int jobIDHash;

	private String jobURL;

	private String jobTitle;

	private String companyName;

	private LocalDateTime jobAnnouncementTime;

	private String description;

	private boolean obsolete;


	public void setJobID(String jobID) {
		this.jobID = jobID;
		jobIDHash = jobID.hashCode();
	}

}