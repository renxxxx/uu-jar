package inininininin.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.CharSequenceUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class StringUtils {
	public static Logger logger = Logger.getLogger(StringUtils.class);

//	public static List<String> extractOffStrs(String[] oldStrs, String[] newStrs) {
//		oldStrs = oldStrs == null ? new String[] {} : oldStrs;
//		newStrs = newStrs == null ? new String[] {} : newStrs;
//
//		List<String> oldStrList = Arrays.asList(oldStrs);
//		List<String> newStrList = Arrays.asList(newStrs);
//
//		List<String> offUrls = new ArrayList<String>();
//		for (String oldUrl : oldStrList) {
//			if (!newStrList.contains(oldUrl))
//				offUrls.add(oldUrl);
//		}
//
//		return offUrls;
//	}

	public static void main(String[] args) {
		System.out.println(replaceRange("sdfsadf", 1, 3, "$"));
	}

	public static String replaceRange(String str, int startIndex, int length, String replacement) {
		if (str == null)
			return str;
		int end = str.length() - startIndex - length;
		if (end < 0)
			end = 0;
		if (end == 0 && startIndex == 0)
			startIndex = 1;
		str = str.replaceAll("(?<=.{" + startIndex + "}).(?=.{" + end + "})", replacement);
		return str;
	}

	public static boolean equalsAny(CharSequence cs, CharSequence... css) {
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

	public static boolean equalsAnyIgnoreCase(String str, String... strs) {
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

	public static boolean equalsAll(String str, String... items) {
		for (String c : items) {
			if (str == c)
				continue;
			if (str != c || !str.equals(c))
				return false;
		}
		return true;
	}

	public static boolean equalsAllIgnoreCase(String str, String... items) {
		for (String c : items) {
			if (str == c)
				continue;
			if (str != c || !str.equalsIgnoreCase(c))
				return false;
		}
		return true;
	}

	public static String newId() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "1" + RandomStringUtils.randomNumeric(14);
	}

	public static String newIdOnSuffix(String suffix) {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "1"
				+ RandomStringUtils.randomNumeric(14 - suffix.length()) + suffix;
	}

	public static String newIdOnPrefix(String prefix) {
		return prefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "1"
				+ RandomStringUtils.randomNumeric(14 - prefix.length());
	}

	public static boolean isEmpty(CharSequence cs) {
		return org.apache.commons.lang3.StringUtils.isEmpty(cs);
	}

	public static String trimToNull(String str) {
		return org.apache.commons.lang3.StringUtils.trimToNull(str);

	}

	public static String trimToEmpty(String str) {
		return org.apache.commons.lang3.StringUtils.trimToEmpty(str);
	}

	public static boolean equals(CharSequence cs1, CharSequence cs2) {
		return org.apache.commons.lang3.StringUtils.equals(cs1, cs2);
	}

	public static String left(String str, int len) {
		return org.apache.commons.lang3.StringUtils.left(str, len);
	}

	public static String right(String str, int len) {
		return org.apache.commons.lang3.StringUtils.right(str, len);
	}
}
