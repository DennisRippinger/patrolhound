package de.drippinger.converter;

import org.ocpsoft.prettytime.PrettyTime;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.Instant;
import java.util.Date;


/**
 * The type Timestamp converter.
 */
@FacesConverter(forClass = Instant.class)
public class TimestampConverter implements Converter {

	private PrettyTime prettyTime = new PrettyTime();

	private static final String TIME_STRING = "<time datetime=\"%s\">%s</time>";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Instant instant = (Instant) value;

		return String.format(TIME_STRING, instant.toString(), prettyTime.format(Date.from(instant)));
	}
}
