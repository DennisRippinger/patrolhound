package de.drippinger.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@FacesConverter(forClass = Timestamp.class)
public class TimestampConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm").format((Timestamp) value);
	}

}
