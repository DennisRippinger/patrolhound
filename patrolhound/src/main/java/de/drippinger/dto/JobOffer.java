package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.IJobOffer;

import java.sql.Timestamp;


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

	public JobOffer() {
	}

	public void setJobID(String jobID) {
		this.jobId = jobID;
		jobIdHash = jobID.hashCode();
	}

	public Long getId() {
		return this.id;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public String getJobId() {
		return this.jobId;
	}

	public Integer getJobIdHash() {
		return this.jobIdHash;
	}

	public String getJobUrl() {
		return this.jobUrl;
	}

	public String getJobTitle() {
		return this.jobTitle;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public Timestamp getJobAnnouncementTime() {
		return this.jobAnnouncementTime;
	}

	public String getDescription() {
		return this.description;
	}

	public Boolean getObsolete() {
		return this.obsolete;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setJobIdHash(Integer jobIdHash) {
		this.jobIdHash = jobIdHash;
	}

	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setJobAnnouncementTime(Timestamp jobAnnouncementTime) {
		this.jobAnnouncementTime = jobAnnouncementTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setObsolete(Boolean obsolete) {
		this.obsolete = obsolete;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof JobOffer)) return false;
		final JobOffer other = (JobOffer) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$id = this.id;
		final Object other$id = other.id;
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		final Object this$companyId = this.companyId;
		final Object other$companyId = other.companyId;
		if (this$companyId == null ? other$companyId != null : !this$companyId.equals(other$companyId)) return false;
		final Object this$jobId = this.jobId;
		final Object other$jobId = other.jobId;
		if (this$jobId == null ? other$jobId != null : !this$jobId.equals(other$jobId)) return false;
		final Object this$jobIdHash = this.jobIdHash;
		final Object other$jobIdHash = other.jobIdHash;
		if (this$jobIdHash == null ? other$jobIdHash != null : !this$jobIdHash.equals(other$jobIdHash)) return false;
		final Object this$jobUrl = this.jobUrl;
		final Object other$jobUrl = other.jobUrl;
		if (this$jobUrl == null ? other$jobUrl != null : !this$jobUrl.equals(other$jobUrl)) return false;
		final Object this$jobTitle = this.jobTitle;
		final Object other$jobTitle = other.jobTitle;
		if (this$jobTitle == null ? other$jobTitle != null : !this$jobTitle.equals(other$jobTitle)) return false;
		final Object this$companyName = this.companyName;
		final Object other$companyName = other.companyName;
		if (this$companyName == null ? other$companyName != null : !this$companyName.equals(other$companyName))
			return false;
		final Object this$jobAnnouncementTime = this.jobAnnouncementTime;
		final Object other$jobAnnouncementTime = other.jobAnnouncementTime;
		if (this$jobAnnouncementTime == null ? other$jobAnnouncementTime != null : !this$jobAnnouncementTime.equals(other$jobAnnouncementTime))
			return false;
		final Object this$description = this.description;
		final Object other$description = other.description;
		if (this$description == null ? other$description != null : !this$description.equals(other$description))
			return false;
		final Object this$obsolete = this.obsolete;
		final Object other$obsolete = other.obsolete;
		if (this$obsolete == null ? other$obsolete != null : !this$obsolete.equals(other$obsolete)) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $id = this.id;
		result = result * PRIME + ($id == null ? 0 : $id.hashCode());
		final Object $companyId = this.companyId;
		result = result * PRIME + ($companyId == null ? 0 : $companyId.hashCode());
		final Object $jobId = this.jobId;
		result = result * PRIME + ($jobId == null ? 0 : $jobId.hashCode());
		final Object $jobIdHash = this.jobIdHash;
		result = result * PRIME + ($jobIdHash == null ? 0 : $jobIdHash.hashCode());
		final Object $jobUrl = this.jobUrl;
		result = result * PRIME + ($jobUrl == null ? 0 : $jobUrl.hashCode());
		final Object $jobTitle = this.jobTitle;
		result = result * PRIME + ($jobTitle == null ? 0 : $jobTitle.hashCode());
		final Object $companyName = this.companyName;
		result = result * PRIME + ($companyName == null ? 0 : $companyName.hashCode());
		final Object $jobAnnouncementTime = this.jobAnnouncementTime;
		result = result * PRIME + ($jobAnnouncementTime == null ? 0 : $jobAnnouncementTime.hashCode());
		final Object $description = this.description;
		result = result * PRIME + ($description == null ? 0 : $description.hashCode());
		final Object $obsolete = this.obsolete;
		result = result * PRIME + ($obsolete == null ? 0 : $obsolete.hashCode());
		return result;
	}

	public String toString() {
		return "de.drippinger.dto.JobOffer(id=" + this.id + ", companyId=" + this.companyId + ", jobId=" + this.jobId + ", jobIdHash=" + this.jobIdHash + ", jobUrl=" + this.jobUrl + ", jobTitle=" + this.jobTitle + ", companyName=" + this.companyName + ", jobAnnouncementTime=" + this.jobAnnouncementTime + ", description=" + this.description + ", obsolete=" + this.obsolete + ")";
	}

	protected boolean canEqual(Object other) {
		return other instanceof JobOffer;
	}
}