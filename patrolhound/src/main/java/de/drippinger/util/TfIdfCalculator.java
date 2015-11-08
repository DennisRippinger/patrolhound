//package de.drippinger.util;
//
//import com.msg.gbl.laboro.repository.CompanyDao;
//import com.msg.gbl.laboro.repository.JobOfferDao;
//import com.msg.gbl.laboro.dto.Company;
//import com.msg.gbl.laboro.dto.JobOffer;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * Created by dennisrippinger on 07.07.15.
// */
//@Slf4j
//@Stateless
//public class TfIdfCalculator {
//
//	@Inject
//	private transient JobOfferDao jobOfferDao;
//
//	@Inject
//	private transient CompanyDao companyDao;
//
//	public void calculateTfIDForCompany(Company company) {
//		List<JobOffer> jobOfferList = jobOfferDao.findAll();
//
//		Map<String, Integer> allWordList = new HashMap<>();
//		Map<String, Integer> companyWordList = new HashMap<>();
//		Map<String, Double> tfidfMap = new HashMap<>();
//
//		List<JobOffer> companyJobOffers = jobOfferList.stream().filter(x -> x.getCompanyId().equals(company.getId())).collect(
//				Collectors.toList());
//
//		getAllJobOffersWordList(jobOfferList, allWordList);
//
//		for (JobOffer jobOffer : companyJobOffers) {
//			getJobOfferWordList(jobOffer, companyWordList);
//
//			for (Map.Entry<String, Integer> entry : allWordList.entrySet()) {
//				Integer companyWordCount = companyWordList.get(entry.getKey());
//
//				if (companyWordCount != null) {
//					Double result = Double.valueOf(companyWordCount) / Double.valueOf(entry.getValue());
//
//					tfidfMap.put(entry.getKey(), result);
//				}
//
//			}
//
//			Map<String, Double> result = sortByValue(tfidfMap);
//
//			Integer counter = 0;
//			List<String> resultList = new ArrayList<>();
//			for (Map.Entry<String, Double> entry : result.entrySet()) {
//				counter++;
//				log.info(entry.toString());
//				resultList.add(entry.getKey());
//				if (counter > 20) {
//					break;
//				}
//			}
//
//			companyDao.insertTags(resultList, company, jobOffer);
//		}
//
//	}
//
//	private void getAllJobOffersWordList(List<JobOffer> jobOfferList, Map<String, Integer> wordList) {
//		for (JobOffer jobOffer : jobOfferList) {
//			getJobOfferWordList(jobOffer, wordList);
//		}
//	}
//
//	private void getJobOfferWordList(JobOffer jobOffer, Map<String, Integer> wordList) {
//
//		StringTokenizer st = new StringTokenizer(jobOffer.getDescription());
//
//		while (st.hasMoreTokens()) {
//			String token = st.nextToken();
//			if (token.endsWith(".")) {
//				token = token.substring(0, token.length() - 1);
//			}
//
//			wordList.merge(token, 1, (x, y) -> x + y);
//		}
//	}
//
//	// From http://stackoverflow.com/a/2581754/769114
//	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
//		List<Map.Entry<K, V>> list =
//				new LinkedList<>(map.entrySet());
//		Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
//
//		Map<K, V> result = new LinkedHashMap<>();
//		for (Map.Entry<K, V> entry : list) {
//			result.put(entry.getKey(), entry.getValue());
//		}
//		return result;
//	}
//
//}
