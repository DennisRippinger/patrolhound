package de.drippinger.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StopWordTest
 *
 * @author Dennis Rippinger
 */
@RunWith(Parameterized.class)
public class StopWordTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
			{"www.test.de", false}
		});
	}

	@Parameter
	public String token;

	@Parameter(1)
	public boolean result;


	private StopWords stopWords;

	@Before
	public void setUp() {
		stopWords = new StopWords();
		stopWords.init();
	}

	@Test
	public void test() {
		assertThat(stopWords.isTokenApplicable(token)).isEqualTo(result);
	}
}
