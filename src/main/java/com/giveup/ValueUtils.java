package com.giveup;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class ValueUtils {
	public static Logger logger = Logger.getLogger(ValueUtils.class);

	public static Integer toInteger(Object value) {
		if (value == null)
			return null;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Integer(valueStr);
	}

	public static Float toFloat(Object value) {
		if (value == null)
			return null;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Float(valueStr);
	}

	public static Long toLong(Object value) {
		if (value == null)
			return null;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new Long(valueStr);
	}

	public static BigDecimal toDecimal(Object value) {
		if (value == null)
			return null;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new BigDecimal(valueStr);
	}

	public static Date toDate(Object value, String pattern) throws ParseException {
		if (value == null)
			return null;
		String valueStr = value.toString();
		if (valueStr.trim().isEmpty())
			return null;
		else
			return new SimpleDateFormat(pattern).parse(valueStr);
	}

}
