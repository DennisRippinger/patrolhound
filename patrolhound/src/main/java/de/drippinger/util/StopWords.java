package de.drippinger.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * List of German StopWords
 *
 * @author Dennis Rippinger
 */
@Slf4j
@Component
public class StopWords {

	@Getter
	private Set<String> germanStopWords = new LinkedHashSet<>();

	@PostConstruct
	public void init() {
		try {
			germanStopWords.addAll(IOUtils.readLines(this.getClass().getResourceAsStream("/stopwords.txt")));
		} catch (Exception e) {
			log.error("Could not load Stopwords", e);
		}
	}


}
