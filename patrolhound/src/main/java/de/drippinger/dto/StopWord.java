package de.drippinger.dto;

import de.drippinger.generated.tables.interfaces.IStopWord;
import lombok.Data;

/**
 * Created by dennisrippinger on 06.07.15.
 */
@Data
public class StopWord implements IStopWord{

	private Long id;

	private String stopWord;
}