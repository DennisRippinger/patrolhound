package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.ITag;
import lombok.Data;

/**
 * Created by dennisrippinger on 12.07.15.
 */
@Data
public class Tag implements ITag{

	private Long id;

	private String tagField;

}