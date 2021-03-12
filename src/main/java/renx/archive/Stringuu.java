package renx.archive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

public class Stringuu {
	public static Logger logger = Logger.getLogger(Stringuu.class);

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
		Map map = new HashMap();
		System.out.println((Integer) map.get("a"));
		System.out.println("212".replaceFirst("^0", "1"));
		System.out.println((System.nanoTime() + ""));
		System.out.println((System.nanoTime() + ""));
		System.out.println((System.nanoTime() + ""));
	}

	public static String commaNum(String str) {
		if (str == null)
			return str;
		String[] parts = str.split("\\.");
		return str;
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

	public static String newId(int tailLength) {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "1"
				+ RandomStringUtils.randomNumeric(tailLength);
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
