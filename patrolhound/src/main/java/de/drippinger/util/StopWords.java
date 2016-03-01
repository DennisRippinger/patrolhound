package de.drippinger.util;

import de.drippinger.repository.StopWordRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;

/**
 * Stop Word logic.
 *
 * @author Dennis Rippinger
 */
@Slf4j
@Named
public class StopWords {

	@Inject
	private StopWordRepository stopWordRepository;

	private Set<String> stopWords;

	private Set<String> locations;

	private List<String> regexs;

	private EmailValidator emailValidator = EmailValidator.getInstance();

	private UrlValidator urlValidator = UrlValidator.getInstance();


	@PostConstruct
	public void init() {
		stopWords = stopWordRepository.findAllStopWords();
		locations = stopWordRepository.findAllLocations();
		regexs = stopWordRepository.findAllRegex();

	}

	public boolean isTokenApplicable(String token) {

		return token.length() > 1
			&& !stopWords.contains(token.toLowerCase())
			&& !locations.contains(token.toLowerCase())
			&& !containsAlphabetic(token)
			&& !StringUtils.isAlphanumericSpace(token)
			&& !emailValidator.isValid(token)
			&& !urlValidator.isValid(token)
			&& !matchesRegex(token);

	}

	private boolean containsAlphabetic(String token) {
		if (token == null) {
			return false;
		} else {
			int sz = token.length();

			for (int i = 0; i < sz; ++i) {
				if (!Character.isAlphabetic(token.codePointAt(i))) {
					return false;
				}
			}

			return true;
		}
	}

	private boolean matchesRegex(String token) {
		for (String regex : regexs) {
			if (token.matches(regex)) {
				return true;
			}
		}
		return false;
	}


}
