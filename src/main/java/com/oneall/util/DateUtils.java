package com.oneall.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtils {
	public static Logger logger = Logger.getLogger(DateUtils.class);

	public static Date parseLong(String longtime) throws ParseException {
		if (longtime == null)
			return null;
		return new Date(new Long(longtime));
	}

	public static Date parseLong(Long time) throws ParseException {
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
