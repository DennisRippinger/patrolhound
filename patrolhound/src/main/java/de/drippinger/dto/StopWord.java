package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.IStopWord;
import lombok.Data;

/**
 * @author Dennis Rippinger
 */
@Data
public class StopWord implements IStopWord {

	private Long id;

	private String stopWord;

	public void from(IStopWord from) {

	}

	public <E extends IStopWord> E into(E into) {
		return null;
	}
}
