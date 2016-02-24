package de.drippinger.action;

import de.drippinger.dto.JobOffer;
import de.drippinger.repository.JobOfferRepository;
import de.drippinger.repository.TagRepository;
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
@Scope("view")
public class OverviewAction implements Serializable {

	private static final long serialVersionUID = 2829845398395153428L;

	@Inject
	private transient JobOfferRepository jobOfferRepository;

	@Inject
	private transient TagRepository tagRepository;

	private List<JobOffer> jobOfferList;

	@Getter
	@Setter
	private Long companyID;

	public List<JobOffer> loadJobOffers() {
		if (jobOfferList == null) {
			jobOfferList = jobOfferRepository.findAllNonObsolete(companyID);

			jobOfferList.forEach(x -> x.setTags(tagRepository.findTagsForJobOffer(x.getId(), 5)));
		}
		return jobOfferList;
	}
}

