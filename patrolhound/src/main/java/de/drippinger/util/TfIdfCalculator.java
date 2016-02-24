package de.drippinger.util;

import de.drippinger.dto.Company;
import de.drippinger.dto.JobOffer;
import de.drippinger.repository.CompanyRepository;
import de.drippinger.repository.JobOfferRepository;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.StringReader;
import java.util.*;


@Slf4j
@Service
public class TfIdfCalculator {

	@Inject
	private JobOfferRepository jobOfferRepository;

	@Inject
	private CompanyRepository companyRepository;

	@Inject
	private StopWords stopWords;

	public void calculateTfIDForCompany(Company company) {
		List<JobOffer> jobOfferList = jobOfferRepository.findAll(company.getId());

		Map<String, Integer> wordInDocumentMap = new HashMap<>();

		countWordDocumentOccurrences(jobOfferList, wordInDocumentMap);

		for (JobOffer jobOffer : jobOfferList) {

			Map<String, Double> tfidfMap = new HashMap<>();
			Map<String, Integer> offerWordMap = new HashMap<>();

			getJobOfferWordList(jobOffer, offerWordMap);

			for (Map.Entry<String, Integer> currentWord : offerWordMap.entrySet()) {
				Double tfidf = calculateTFIDF(jobOfferList, wordInDocumentMap, currentWord);

				tfidfMap.put(currentWord.getKey(), tfidf);
			}

			Map<String, Double> resultMap = fetchFirstEntries(tfidfMap);

			companyRepository.insertTags(resultMap, company, jobOffer);
		}

	}

	private Double calculateTFIDF(List<JobOffer> jobOfferList, Map<String, Integer> wordInDocumentMap, Map.Entry<String, Integer> entry) {
		Integer termFrequency = entry.getValue();
		Double inverseTermFrequency = Math.log(jobOfferList.size() / wordInDocumentMap.get(entry.getKey()));

		return termFrequency * inverseTermFrequency;
	}

	private Map<String, Double> fetchFirstEntries(Map<String, Double> tfidfMap) {
		Map<String, Double> result = sortByValue(tfidfMap);

		Integer counter = 0;
		Map<String, Double> resultList = new LinkedHashMap<>();
		for (Map.Entry<String, Double> resultEntry : result.entrySet()) {
			counter++;
			resultList.put(resultEntry.getKey(), resultEntry.getValue());
			if (counter > 20) {
				break;
			}

		}
		return resultList;
	}

	private void countWordDocumentOccurrences(List<JobOffer> jobOfferList, Map<String, Integer> wordDocumentOccurrence) {
		for (JobOffer jobOffer : jobOfferList) {
			Set<String> wordSet = documentToSet(jobOffer.getDescription());
			for (String word : wordSet) {
				wordDocumentOccurrence.merge(word, 1, (x, y) -> x + y);
			}

		}
	}

	private Set<String> documentToSet(String document) {
		PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>(new StringReader(document), new CoreLabelTokenFactory(), "");
		Set<String> result = new LinkedHashSet<>();

		while (tokenizer.hasNext()) {
			String token = tokenizer.next().toString();
			if (isTokenApplicable(token)) {
				result.add(token);
			}
		}

		return result;
	}

	private boolean isTokenApplicable(String token) {
		return token.length() > 1
			&& !stopWords.getGermanStopWords().contains(token.toLowerCase())
			// No mixing of Words with numbers
			&& !StringUtils.isAlphanumeric(token);
	}

	private void getJobOfferWordList(JobOffer jobOffer, Map<String, Integer> wordList) {

		PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>(new StringReader(jobOffer.getDescription()),
			new CoreLabelTokenFactory(), "");
		while (tokenizer.hasNext()) {
			CoreLabel label = tokenizer.next();
			String token = label.toString();
			if (isTokenApplicable(token)) {
				wordList.merge(token, 1, (x, y) -> x + y);
			}
		}
	}

	// From http://stackoverflow.com/a/2581754/769114
	private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
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
