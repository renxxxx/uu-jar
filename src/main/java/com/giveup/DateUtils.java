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

public class DateUtils {
	public static Logger logger = Logger.getLogger(DateUtils.class);

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
