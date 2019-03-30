package com.giveup;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

	public static String toString(Object value) {
		if (value == null)
			return null;
		String valueStr = value.toString();
		if (valueStr.isEmpty())
			return null;
		else
			return valueStr.toString();
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

	public static Date toDate(Object value) throws ParseException {
		if (value == null)
			return null;
		return (Date) value;
	}

	public static void main(String[] args) {
		Map a = new HashMap();
		System.out.println((Date) a.get("a"));
	}
}
