package de.drippinger.converter;

import org.ocpsoft.prettytime.PrettyTime;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.Instant;
import java.util.Date;


@FacesConverter(forClass = Instant.class)
public class TimestampConverter implements Converter {

	private PrettyTime prettyTime = new PrettyTime();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Instant instant = (Instant) value;

		return prettyTime.format(Date.from(instant));
	}
}
