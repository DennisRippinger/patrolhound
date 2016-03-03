package de.drippinger.action;

import de.drippinger.dto.Company;
import de.drippinger.repository.CompanyRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Class for Company related actions.
 *
 * @author Dennis Rippinger
 */
@Slf4j
@Named
@Scope("view")
public class CompanyAction implements Serializable {

	private static final long serialVersionUID = 503243196512521686L;

	@Inject
	private transient CompanyRepository companyRepository;

	@Getter
	@Setter
	private Long customerID;

	@Getter
	private Boolean hasCustomer = false;

	@Getter
	@Setter
	private Company company;

	/**
	 * Load companies list.
	 *
	 * @return the list of all companies.
	 */
	public List<Company> loadCompanies() {
		return companyRepository.findAll();
	}

	/**
	 * Loads the current company.
	 */
	public void loadCurrentCompany() {
		if (customerID != null) {
			company = companyRepository.find(customerID);
			hasCustomer = true;

			return;
		}
		company = new Company();
	}

	/**
	 * Counts the number of offers for a given company.
	 *
	 * @param companyID the company id
	 * @return the number of open job offers.
	 */
	public int countOffers(Long companyID) {
		return companyRepository.countOpenJobOffersForCompany(companyID);
	}

	/**
	 * Delete a company.
	 */
	public void deleteCompany() {
		log.info("Delete {}", company.getName());

		companyRepository.delete(company);
	}

	/**
	 * Save a company.
	 */
	public void saveCompany() {
		companyRepository.save(company);
	}

}
