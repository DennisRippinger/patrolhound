package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.ICompany;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * @author Dennis Rippinger
 */
@Data
public class Company implements ICompany {

	private Long id;

	private String name;

	private String crawlerName;

	private String street;

	private String locationCode;

	private String city;

	private String contactPerson;

	private String phone;

	private Instant lastUpdate;

	private List<JobOffer> jobOffers;


	public void from(ICompany from) {

	}

	public <E extends ICompany> E into(E into) {
		return null;
	}
}
