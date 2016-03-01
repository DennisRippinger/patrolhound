package de.drippinger.util;

import java.util.*;

/**
 * SortUtil
 *
 * @author Dennis Rippinger
 */
public class SortUtil {

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
