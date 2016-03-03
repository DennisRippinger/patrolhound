package de.drippinger.util;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SortUtil Tester.
 */
public class SortUtilTest {

	private static final Integer NUMBER_OF_CASES = 10;

	private Map<String, Integer> testData;

	@Before
	public void before() throws Exception {
		testData = new LinkedHashMap<>();
		for (int i = 0; i < NUMBER_OF_CASES; i++) {
			testData.put("Test_" + i, i);
		}

	}

	@Test
	public void testSortByValue() throws Exception {
		Integer lastNumber = null;
		Map<String, Integer> testMap = SortUtil.sortByValue(testData);

		for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
			if (lastNumber != null) {
				assertThat(entry.getValue()).isLessThan(lastNumber);
			}
			lastNumber = entry.getValue();
		}
	}


}
