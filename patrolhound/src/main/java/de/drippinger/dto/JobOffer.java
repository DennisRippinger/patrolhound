package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.IJobOffer;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;


/**
 * @author Dennis Rippinger
 */
@Data
public class JobOffer implements IJobOffer {

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


	public void from(IJobOffer from) {

	}

	public <E extends IJobOffer> E into(E into) {
		return null;
	}

	public void setJobId(String jobId){
		this.jobId = jobId;
		this.jobIdHash = jobId.hashCode();
	}
}
