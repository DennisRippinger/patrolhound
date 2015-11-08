package de.drippinger.util;

import de.drippinger.dto.Company;
import de.drippinger.dto.JobOffer;
import de.drippinger.repository.CompanyRepository;
import de.drippinger.repository.JobOfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TfIdfCalculator {

	@Resource
	private transient JobOfferRepository jobOfferRepository;

	@Resource
	private transient CompanyRepository companyRepository;

	public void calculateTfIDForCompany(Company company) {
		List<JobOffer> jobOfferList = jobOfferRepository.findAll();

		Map<String, Integer> allWordList = new HashMap<>();
		Map<String, Integer> companyWordList = new HashMap<>();
		Map<String, Double> tfidfMap = new HashMap<>();

		List<JobOffer> companyJobOffers = jobOfferList.stream().filter(x -> x.getCompanyId().equals(company.getId())).collect(
				Collectors.toList());

		getAllJobOffersWordList(jobOfferList, allWordList);

		for (JobOffer jobOffer : companyJobOffers) {
			getJobOfferWordList(jobOffer, companyWordList);

			for (Map.Entry<String, Integer> entry : allWordList.entrySet()) {
				Integer companyWordCount = companyWordList.get(entry.getKey());

				if (companyWordCount != null) {
					Double result = Double.valueOf(companyWordCount) / Double.valueOf(entry.getValue());

					tfidfMap.put(entry.getKey(), result);
				}

			}

			Map<String, Double> result = sortByValue(tfidfMap);

			Integer counter = 0;
			List<String> resultList = new ArrayList<>();
			for (Map.Entry<String, Double> entry : result.entrySet()) {
				counter++;
				log.info(entry.toString());
				resultList.add(entry.getKey());
				if (counter > 20) {
					break;
				}
			}

			companyRepository.insertTags(resultList, company, jobOffer);
		}

	}

	private void getAllJobOffersWordList(List<JobOffer> jobOfferList, Map<String, Integer> wordList) {
		for (JobOffer jobOffer : jobOfferList) {
			getJobOfferWordList(jobOffer, wordList);
		}
	}

	private void getJobOfferWordList(JobOffer jobOffer, Map<String, Integer> wordList) {

		StringTokenizer st = new StringTokenizer(jobOffer.getDescription());

		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.endsWith(".")) {
				token = token.substring(0, token.length() - 1);
			}

			wordList.merge(token, 1, (x, y) -> x + y);
		}
	}

	// From http://stackoverflow.com/a/2581754/769114
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list =
				new LinkedList<>(map.entrySet());
		Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

}
