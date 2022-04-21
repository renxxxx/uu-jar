package renx.uu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Objectuu {
	private static Logger logger = LoggerFactory.getLogger(Objectuu.class);

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

	public static Object overlengthSummarize(Object obj) {
		return overlengthSummarize(300, obj);
	}

	public static Object overlengthSummarize(int length, Object obj) {
		if (obj == null)
			return obj;
		if (obj instanceof String || obj instanceof StringBuffer || obj instanceof StringBuilder
				|| obj instanceof CharSequence) {
			String str = obj.toString();
			str = str.length() > length ? str.substring(0, length) + " ..." + str.length() : str;
			obj = str;
		}
		return obj;
	}

	public static Object[] overlengthSummarize(Object[] objs) {
		return overlengthSummarize(300, objs);
	}

	public static Object[] overlengthSummarize(int length, Object[] objs) {
		Object[] objs2 = new Object[objs.length];
		for (int i = 0; i < objs.length; i++) {
			Object obj = objs[i];
			obj = overlengthSummarize(length, obj);
			objs2[i] = obj;
		}
		return objs2;
	}
}
