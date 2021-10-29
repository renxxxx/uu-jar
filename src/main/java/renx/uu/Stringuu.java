package renx.uu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stringuu {
	private static Logger logger = LoggerFactory.getLogger(Stringuu.class);

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
		System.out.println(camel("wrwer-asfd_e234_ewr"));
	}

	public static Integer toByte(String str) {
		if (Stringuu.isEmpty(str))
			return null;
		String strTo = str;
		Integer size = Paramm.toInteger(str);
		String unit = null;
		if (size == null) {
			unit = str.substring(str.length() - 1);
			strTo = str.substring(0, str.length() - 1);
			size = Paramm.toInteger(strTo);
			if (size == null) {
				unit = str.substring(str.length() - 2);
				strTo = str.substring(0, str.length() - 2);
				size = Paramm.toInteger(strTo);
			}
		}
		if (Stringuu.isEmpty(unit)) {
			return size;
		} else {
			if (Stringuu.equalsIgnoreCaseAny(unit, "g", "gb")) {
				return size * 1024 * 1024 * 1024;
			} else if (Stringuu.equalsIgnoreCaseAny(unit, "m", "mb")) {
				return size * 1024 * 1024;
			} else if (Stringuu.equalsIgnoreCaseAny(unit, "k", "kb")) {
				return size * 1024;
			} else if (Stringuu.equalsIgnoreCaseAny(unit, "b")) {
				return size;
			}
		}

		return null;
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

	public static boolean notEqualsAny(String str, Object... params) {
		return !equalsAny(str, params);
	}

	public static boolean equalsAny(String str, Object... params) {
		if (params == null)
			return false;
		for (Object c : params) {
			if (str == c)
				return true;
			if (c != null && c.toString().equals(str))
				return true;
		}
		return false;
	}

	public static boolean notEqualsIgnoreCaseAny(String str, Object... params) {
		return !equalsIgnoreCaseAny(str, params);
	}

	public static boolean equalsIgnoreCaseAny(String str, Object... params) {
		if (params == null)
			return false;
		for (Object c : params) {
			if (str == c)
				return true;
			if (c != null && c.toString().equalsIgnoreCase(str))
				return true;
		}
		return false;
	}

	public static boolean notEqualsAll(String str, String... items) {
		return !equalsAll(str, items);
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

	public static boolean notEqualsIgnoreCaseAll(String str, String... items) {
		return !equalsIgnoreCaseAll(str, items);
	}

	public static boolean equalsIgnoreCaseAll(String str, String... items) {
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

	public static boolean exist(CharSequence cs) {
		return org.apache.commons.lang3.StringUtils.isNotEmpty(cs);
	}

	public static boolean notEmpty(CharSequence cs) {
		return org.apache.commons.lang3.StringUtils.isNotEmpty(cs);
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

	public static boolean notEquals(CharSequence cs1, CharSequence cs2) {
		return !org.apache.commons.lang3.StringUtils.equals(cs1, cs2);
	}

	public static boolean equals(CharSequence cs1, CharSequence cs2) {
		return org.apache.commons.lang3.StringUtils.equals(cs1, cs2);
	}

	public static boolean equalsIgnoreCase(CharSequence cs1, CharSequence cs2) {
		return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cs1, cs2);
	}

	public static String left(String str, int len) {
		return org.apache.commons.lang3.StringUtils.left(str, len);
	}

	public static String right(String str, int len) {
		return org.apache.commons.lang3.StringUtils.right(str, len);
	}

	public static String camel(String str) {
		String[] sss = str.split("_|-");
		for (int j = 0; j < sss.length; j++) {
			if (j > 0)
				sss[j] = (sss[j].charAt(0) + "").toUpperCase() + sss[j].substring(1);
		}
		String str1 = StringUtils.join(sss);
		return str1;
	}

	public static String[] array(String... item) {
		return item;
	}

}
