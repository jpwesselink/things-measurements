package serializers;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import flexjson.BasicType;
import flexjson.JSONContext;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;


public class MultipleDateTransformer extends AbstractTransformer {

	String prefix = "";

	public MultipleDateTransformer() {
		
	}

	public void transform(Object o) {
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		DateTimeFormatter customFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
		boolean setContext = false;

		TypeContext typeContext = getContext().peekTypeContext();
		String propertyName = typeContext != null ? typeContext.getPropertyName() : "";
		if(prefix.trim().equals("")) prefix = propertyName;

		if (typeContext == null || typeContext.getBasicType() != BasicType.OBJECT) {
			typeContext = getContext().writeOpenObject();
			setContext = true;
		}

		Date date = (Date) o;
		DateTime dateTime = new DateTime(date);
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		if (!typeContext.isFirst()) getContext().writeComma();
		typeContext.setFirst(false);
		getContext().writeName(propertyName);
		getContext().writeOpenObject();
		
		getContext().writeName("iso8601");
		getContext().writeQuoted(dateTime.toDateTimeISO().toString());
		getContext().writeComma();
		getContext().writeName("custom");
		getContext().writeQuoted(dateTime.toString(customFormatter));
		getContext().writeComma();
		getContext().writeName("epoch");
		Long l = (Long)dateTime.toDate().getTime() / 1000;
		getContext().write(l.toString());
		
		getContext().writeCloseObject();
		
		if (setContext) {
			getContext().writeCloseObject();
		}

	}

	private String fieldName(String suffix) {
		if( prefix.trim().equals("")) {
			return suffix.toLowerCase();
		} else {
			return prefix + suffix;
		}
	}

	@Override
	public Boolean isInline() {
		return Boolean.TRUE;
	}
}