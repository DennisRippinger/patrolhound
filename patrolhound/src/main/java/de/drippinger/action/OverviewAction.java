package de.drippinger.action;

import de.drippinger.dto.JobOffer;
import de.drippinger.repository.JobOfferRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * OverviewAction
 *
 * @author Dennis Rippinger
 */
@Slf4j
@Named
@Scope("session")
public class OverviewAction implements Serializable {

	private static final long serialVersionUID = 2829845398395153428L;

	@Inject
	private transient JobOfferRepository jobOfferRepository;

	@Getter
	@Setter
	private Long companyID;

	public List<JobOffer> loadJobOffers() {
		return jobOfferRepository.findAllNonObsolete(companyID);
	}
}

