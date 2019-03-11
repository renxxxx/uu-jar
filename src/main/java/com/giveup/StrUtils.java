package com.giveup;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class StrUtils {
	public static Logger logger = Logger.getLogger(StrUtils.class);

	public static JSONObject isJSON(String str) {
		try {
			return JSON.parseObject(str);
		} catch (RuntimeException e) {
			return null;
		}
	}

	public static boolean isNumber(String str) {
		try {
			new Double(str);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static boolean isInteger(String str) {
		try {
			new Integer(str);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static boolean allEqualWith(CharSequence target, CharSequence... items) {
		for (CharSequence c : items) {
			if (target == c)
				continue;
			if (target != c || !target.equals(c))
				return false;
		}
		return true;
	}

	public static boolean equalsWithAny(CharSequence cs, CharSequence... css) {
		if (css == null)
			return false;
		for (CharSequence c : css) {
			if (cs == c)
				return true;
			if (cs != null && cs.equals(c))
				return true;
			if (c != null && c.equals(cs))
				return true;
		}
		return false;
	}

	public static boolean equalsIgnoreCaseWithAny(String str, String... strs) {
		if (strs == null)
			return false;
		for (String s : strs) {
			if (str == s)
				return true;
			if (str != null && str.equalsIgnoreCase(s))
				return true;
			if (s != null && s.equalsIgnoreCase(str))
				return true;
		}
		return false;
	}

	public static boolean allEqualsIgnoreCaseWith(String target, String... items) {
		for (String c : items) {
			if (target == c)
				continue;
			if (target != c || !target.equalsIgnoreCase(c))
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out
				.println(Pattern.matches("^[!@#$%^&*()_-`=+~{}\\[\\];:'\\\",.<>?/a-zA-Z0-9]{6,20}$", "12{1``[\"`~``3"));
	}
}
