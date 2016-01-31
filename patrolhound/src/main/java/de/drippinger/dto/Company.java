package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.ICompany;

import java.util.List;

/**
 * Created by dennisrippinger on 10.06.15.
 */
public class Company implements ICompany {

	private Long id;

	private String name;

	private String crawlerName;

	private String street;

	private String locationCode;

	private String city;

	private String contactPerson;

	private String phone;

	private List<JobOffer> jobOffers;

	public Company() {
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getCrawlerName() {
		return this.crawlerName;
	}

	public String getStreet() {
		return this.street;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public String getCity() {
		return this.city;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public String getPhone() {
		return this.phone;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setJobOffers(List<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Company)) return false;
		final Company other = (Company) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$id = this.id;
		final Object other$id = other.id;
		if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
		final Object this$name = this.name;
		final Object other$name = other.name;
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		final Object this$crawlerName = this.crawlerName;
		final Object other$crawlerName = other.crawlerName;
		if (this$crawlerName == null ? other$crawlerName != null : !this$crawlerName.equals(other$crawlerName))
			return false;
		final Object this$street = this.street;
		final Object other$street = other.street;
		if (this$street == null ? other$street != null : !this$street.equals(other$street)) return false;
		final Object this$locationCode = this.locationCode;
		final Object other$locationCode = other.locationCode;
		if (this$locationCode == null ? other$locationCode != null : !this$locationCode.equals(other$locationCode))
			return false;
		final Object this$city = this.city;
		final Object other$city = other.city;
		if (this$city == null ? other$city != null : !this$city.equals(other$city)) return false;
		final Object this$contactPerson = this.contactPerson;
		final Object other$contactPerson = other.contactPerson;
		if (this$contactPerson == null ? other$contactPerson != null : !this$contactPerson.equals(other$contactPerson))
			return false;
		final Object this$phone = this.phone;
		final Object other$phone = other.phone;
		if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
		final Object this$jobOffers = this.jobOffers;
		final Object other$jobOffers = other.jobOffers;
		if (this$jobOffers == null ? other$jobOffers != null : !this$jobOffers.equals(other$jobOffers)) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $id = this.id;
		result = result * PRIME + ($id == null ? 0 : $id.hashCode());
		final Object $name = this.name;
		result = result * PRIME + ($name == null ? 0 : $name.hashCode());
		final Object $crawlerName = this.crawlerName;
		result = result * PRIME + ($crawlerName == null ? 0 : $crawlerName.hashCode());
		final Object $street = this.street;
		result = result * PRIME + ($street == null ? 0 : $street.hashCode());
		final Object $locationCode = this.locationCode;
		result = result * PRIME + ($locationCode == null ? 0 : $locationCode.hashCode());
		final Object $city = this.city;
		result = result * PRIME + ($city == null ? 0 : $city.hashCode());
		final Object $contactPerson = this.contactPerson;
		result = result * PRIME + ($contactPerson == null ? 0 : $contactPerson.hashCode());
		final Object $phone = this.phone;
		result = result * PRIME + ($phone == null ? 0 : $phone.hashCode());
		final Object $jobOffers = this.jobOffers;
		result = result * PRIME + ($jobOffers == null ? 0 : $jobOffers.hashCode());
		return result;
	}

	public String toString() {
		return "de.drippinger.dto.Company(id=" + this.id + ", name=" + this.name + ", crawlerName=" + this.crawlerName + ", street=" + this.street + ", locationCode=" + this.locationCode + ", city=" + this.city + ", contactPerson=" + this.contactPerson + ", phone=" + this.phone + ", jobOffers=" + this.jobOffers + ")";
	}

	protected boolean canEqual(Object other) {
		return other instanceof Company;
	}
}
