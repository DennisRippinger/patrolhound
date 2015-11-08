package de.drippinger.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by dennisrippinger on 10.06.15.
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

	private List<JobOffer> jobOffers;

}
