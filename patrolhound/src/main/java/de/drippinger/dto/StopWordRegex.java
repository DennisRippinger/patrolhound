package de.drippinger.dto;

import de.drippinger.generated.tables.SwRegex;
import lombok.Data;

/**
 * StopWordRegex
 *
 * @author Dennis Rippinger
 * @since 29.02.16
 */
@Data
public class StopWordRegex extends SwRegex {

	private Long ID;

	private String regex;

}
