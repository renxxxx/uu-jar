package renx.archive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Regex {
	private static Logger logger = LoggerFactory.getLogger(Regex.class);

	public static String group(String regex, String str, int group) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (!m.matches())
			return null;
		try {
			return m.group(group);
		} catch (RuntimeException e) {
			logger.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}

	}

	public static void main(String[] args) {
		System.out.println("/123/123.12".replaceAll("(^/)|(/$)", ""));
		// Pattern p = Pattern.compile("^/?(.*?)/.*$");
		// Matcher m = p.matcher("zhongan/oss/aa.txt");
		// m.matches();
		// System.out.println(m.group(1));
	}
}
