package inininininin.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class StringUtil {
	public static Logger logger = Logger.getLogger(StringUtil.class);

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

	public static JSONObject isJSON(String str) {
		try {
			return JSON.parseObject(str);
		} catch (RuntimeException e) {
			return null;
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

	public static boolean equalsAllWith(String target, String... items) {
		for (String c : items) {
			if (target == c)
				continue;
			if (target != c || !target.equals(c))
				return false;
		}
		return true;
	}

	public static boolean equalsAllIgnoreCaseWith(String target, String... items) {
		for (String c : items) {
			if (target == c)
				continue;
			if (target != c || !target.equalsIgnoreCase(c))
				return false;
		}
		return true;
	}

	public static String newId() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "1" + RandomStringUtils.randomNumeric(14);
	}

	public static String newId(String suffix) {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "1"
				+ RandomStringUtils.randomNumeric(14 - suffix.length()) + suffix;
	}

}
