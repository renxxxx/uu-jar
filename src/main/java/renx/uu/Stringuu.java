package renx.uu;

import java.text.SimpleDateFormat;
import java.util.Date;

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

	public static void main(String[] args) throws InterruptedException {
		System.out.println("11".substring(0, "11".length() - 1));
	}

	public static String[] toArray(String item, int size) {
		String[] items = new String[size];
		for (int i = 0; i < items.length; i++) {
			items[i] = item;
		}
		return items;
	}

	public static Integer toByte(String str) {
		if (Stringuu.isEmpty(str))
			return null;
		String strTo = str;
		Integer size = Var.toInteger(str);
		String unit = null;
		if (size == null) {
			unit = str.substring(str.length() - 1);
			strTo = str.substring(0, str.length() - 1);
			size = Var.toInteger(strTo);
			if (size == null) {
				unit = str.substring(str.length() - 2);
				strTo = str.substring(0, str.length() - 2);
				size = Var.toInteger(strTo);
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

	public static String timeId() {
		return new Date().getTime() + RandomStringUtils.randomNumeric(6);
	}

	public static String timeId(int randomLength) {
		return new Date().getTime() + RandomStringUtils.randomNumeric(randomLength);
	}

	public static boolean isExisting(CharSequence cs) {
		return notEmpty(cs);
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

	public static String trimToBlank(String str) {
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

	public static String left(String str, int len, int start) {
		if (start <= str.length())
			str = str.substring(start - 1);
		else
			return "";
		return org.apache.commons.lang3.StringUtils.left(str, len);
	}

	public static String right(String str, int len) {
		return org.apache.commons.lang3.StringUtils.right(str, len);
	}

	public static String right(String str, int len, int start) {
		if (start <= str.length())
			str = str.substring(0, str.length() - start + 1);
		else
			return "";
		return org.apache.commons.lang3.StringUtils.right(str, len);
	}

	public static LList split(final String str, final String separator) {
		if (str == null)
			return LList.build();
		String[] ss = StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator);
		ss = ss == null ? new String[] {} : ss;
		LList llist = LList.build().addAll(ss);
		return llist;
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

	public static String buildFixedArrayString(String s, int count) {
		String ss = "";
		for (int i = 0; i < count; i++) {
			ss += (s + ",");
		}
		if (ss.length() > 0)
			ss = ss.substring(0, ss.length() - 1);
		return ss;
	}

	public static String excelIndexToAlphas(int value) throws Exception {
		if (value < 1)
			return "";
		// 转26进制 0-25
		int calculateValue = value - 1;
		// 取高位
		int high = calculateValue / 26;
		// 取低位
		int low = calculateValue % 26;
		// 低位可直接取出对应的字母
		String transStr = String.valueOf((char) (low + 65));
		if (high > 0) {
			// 高位递归取出字母
			transStr = excelIndexToAlphas(high) + transStr;
		}
		return transStr;
	}

	public static String[] array(String... item) {
		return item;
	}

}
