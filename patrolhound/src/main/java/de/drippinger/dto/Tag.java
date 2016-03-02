package de.drippinger.dto;

import lombok.Data;

/**
 * @author Dennis Rippinger
 */
@Data
public class Tag extends de.drippinger.generated.tables.Tag {

	private Long id;

	private String tagField;

}
