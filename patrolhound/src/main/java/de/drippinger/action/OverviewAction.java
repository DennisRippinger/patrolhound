package de.drippinger.action;

import de.drippinger.dto.Company;
import de.drippinger.dto.JobOffer;
import de.drippinger.repository.CompanyRepository;
import de.drippinger.repository.JobOfferRepository;
import de.drippinger.repository.TagRepository;
import de.drippinger.util.SortUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	private transient CompanyRepository companyRepository;

	@Inject
	private transient TagRepository tagRepository;

	private List<JobOffer> jobOfferList;

	private Map<String, Double> summary;

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

	public Company getCompany() {
		return companyRepository.find(companyID);
	}

	public Map<String, Double> loadSummary() {
		if (summary == null) {
			Map<String, Integer> tmpResult = new LinkedHashMap();

			// Count Tags
			for (JobOffer jobOffer : loadJobOffers()) {
				for (String tag : jobOffer.getTags()) {
					tmpResult.merge(tag, 1, (x, y) -> x + y);
				}
			}

			// Filter one timers and calculate percentage
			Map<String, Double> result = new LinkedHashMap<>();
			tmpResult
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue() > 2)
				.forEach(entry -> {
						Double percentage = (entry.getValue() * 1.0 / loadJobOffers().size()) * 100;
						result.put(entry.getKey(), Math.floor(percentage));
					}
				);

			summary = SortUtil.sortByValue(result);
		}

		return summary;
	}
}

