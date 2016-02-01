package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.ITag;
import lombok.Data;

/**
 * @author Dennis Rippinger
 */
@Data
public class Tag implements ITag {

	private Long id;

	private String tagField;


	public void from(ITag from) {

	}

	public <E extends ITag> E into(E into) {
		return null;
	}
}
