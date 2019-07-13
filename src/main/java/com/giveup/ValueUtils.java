package com.giveup;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class ValueUtils {
	public static Logger logger = Logger.getLogger(ValueUtils.class);

	public static Integer toInteger(Object value) {
		if (value == null)
			return null;
		if (value instanceof Integer)
			return (Integer) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Integer(valueStr);
	}

	public static Float toFloat(Object value) {
		if (value == null)
			return null;
		if (value instanceof Float)
			return (Float) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Float(valueStr);
	}

	public static String toString(Object value) {
		if (value == null)
			return null;
		if (value instanceof String)
			return (String) value;
		String valueStr = value.toString();
		if (valueStr.isEmpty())
			return null;
		else
			return valueStr.toString();
	}

	public static Date toDate(Object value) {
		if (value == null)
			return null;
		if (value instanceof Date)
			return (Date) value;
		if (value instanceof String) {
			if (value.toString().trim().isEmpty())
				return null;
			if (value.toString().trim().length() == 13 && StringUtils.isNumeric(value.toString()))
				return new Date(Long.parseLong(value.toString().trim()));
			if (value.toString().trim().length() == 10 && StringUtils.isNumeric(value.toString()))
				return new Date(Long.parseLong(value.toString().trim()) * 1000);
			try {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
			} catch (Exception e) {
			}
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
			} catch (Exception e) {
			}
			try {
				return new SimpleDateFormat("HH:mm:ss").parse(value.toString());
			} catch (Exception e) {
			}
		}
		if (value instanceof Long)
			return new Date((Long) value);
		if (value instanceof Integer)
			return new Date((Long) value * 1000);
		return null;
	}

	public static Date toDate(Object value, String pattern) {
		Date date = toDate(value);
		if (date == null)
			try {
				date = new SimpleDateFormat(pattern).parse(value.toString());
			} catch (Exception e) {
			}
		return date;
	}

	public static Long toLong(Object value) {
		if (value == null)
			return null;
		if (value instanceof Long)
			return (Long) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Long(valueStr);
	}

	public static BigDecimal toDecimal(Object value) {
		if (value == null)
			return null;
		if (value instanceof BigDecimal)
			return (BigDecimal) value;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new BigDecimal(valueStr);
	}

	public static void main(String[] args) {
		Map a = new HashMap();
		System.out.println(Boolean.getBoolean("1"));
	}
}
