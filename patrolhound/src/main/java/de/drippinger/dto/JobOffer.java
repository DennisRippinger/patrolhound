package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.IJobOffer;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class JobOffer implements IJobOffer {

	private Long id;

	private Long companyId;

	private String jobId;

	private Integer jobIdHash;

	private String jobUrl;

	private String jobTitle;

	private String companyName;

	private Timestamp jobAnnouncementTime;

	private String description;

	private Boolean obsolete;

	public void setJobID(String jobID) {
		this.jobId = jobID;
		jobIdHash = jobID.hashCode();
	}
}