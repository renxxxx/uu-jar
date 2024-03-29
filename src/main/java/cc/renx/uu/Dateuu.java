package cc.renx.uu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dateuu {
	private Logger logger = LoggerFactory.getLogger(Dateuu.class);

	public static Date parseLong(String longtime) throws ParseException {
		if (longtime == null)
			return null;
		return new Date(new Long(longtime));
	}

	public static Date parse(Long time) throws ParseException {
		if (time == null)
			return null;
		return new Date(time);
	}

	public static Date parse(String value, String pattern) throws ParseException {
		if (value == null || value.isEmpty())
			return null;
		return new SimpleDateFormat(pattern).parse(value);
	}

	public static Date parse(String value, SimpleDateFormat sdf) throws ParseException {
		if (value == null || value.isEmpty())
			return null;
		return sdf.parse(value);
	}

	public static String format(Date date, String pattern) throws ParseException {
		if (date == null)
			return null;
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String format(Date date, SimpleDateFormat sdf) throws ParseException {
		if (date == null)
			return null;
		return sdf.format(date);
	}
}
