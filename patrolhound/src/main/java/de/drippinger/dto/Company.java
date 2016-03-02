package de.drippinger.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author Dennis Rippinger
 */
@Data
public class Company {

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

}
